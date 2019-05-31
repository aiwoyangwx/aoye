package com.aoye.product.service;

import com.aoye.common.enums.ExceptionEnum;
import com.aoye.common.exception.AyException;
import com.aoye.common.utils.IdWorker;
import com.aoye.common.vo.PageResult;
import com.aoye.product.mapper.ProvinceMapper;
import com.aoye.product.mapper.UpdateParamMapper;
import com.aoye.product.pojo.Category;
import com.aoye.product.pojo.Province;
import com.aoye.product.pojo.UpdateParam;
import com.aoye.product.vo.UpdateParamVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UpdateParamService {

    @Autowired
    private UpdateParamMapper updateParamMapper;

    @Autowired
    private ProvinceMapper provinceMapper;

    @Autowired
    private IdWorker idWorker;

    /**
     * @Author: Alex
     * @CreateDate: 2019/5/31
     * @Description: 新增升级参数
     */
    @Transactional
    public void save(UpdateParamVo vo) {
        List<Long> iids = vo.getIids();
        List<Integer> provinces = vo.getProvinces();
        List<UpdateParam> updateParams = new ArrayList<>();
        for (Long iid : iids) {
            for (Integer province : provinces) {
                UpdateParam u = new UpdateParam();
                u.setId(idWorker.nextId());
                u.setCid(vo.getCid());
                u.setIid(iid);
                u.setProvinceId(province);
                u.setCreateTime(new Date());
                updateParams.add(u);
            }
        }
        int i = updateParamMapper.insertList(updateParams);
        if (i == 0) {
            throw new AyException(ExceptionEnum.FAIl_TO_ADD);
        }
    }

    /**
     * @Author: Alex
     * @CreateDate: 2019/5/31
     * @Description: 删除升级参数
     */
    public void delete(Long id){
        int i = updateParamMapper.deleteByPrimaryKey(id);
        if (i != 1) {
            throw new AyException(ExceptionEnum.FAIl_TO_DEL);
        }
    }
    /**
     * @Author: Alex
     * @CreateDate: 2019/5/31
     * @Description: 查询升级参数分页
     */
    public PageResult<UpdateParam> queryUpdateParamByPage(
            Integer page, Integer rows, String sortBy, Boolean desc,Long cid, Integer iid,Integer provinceId){
        // 开始分页
        PageHelper.startPage(page, rows);
        // 过滤
        Example example = new Example(Category.class);
        Example.Criteria criteria = example.createCriteria();
        if (cid!=null) {
            criteria.andEqualTo("cid", cid);
        }
        if (iid != null) {
            criteria.andEqualTo("iid", iid);
        }
        if (provinceId != null) {
            criteria.andEqualTo("provinceId", provinceId);
        }
        if (StringUtils.isNotBlank(sortBy)) {
            // 排序
            String orderByClause = sortBy + (desc ? " DESC" : " ASC");
            example.setOrderByClause(orderByClause);
        }
        // 查询
        Page<UpdateParam> pageInfo = (Page<UpdateParam>) updateParamMapper.selectByExample(example);
        if (pageInfo.getTotal()==0) {
            throw new AyException(ExceptionEnum.INFO_NOT_FOUND);
        }
        // 返回结果
        PageResult<UpdateParam> result = new PageResult<>(pageInfo.getTotal(), pageInfo);
        return result;
    }

    /**
     * @Author: Alex
     * @CreateDate: 2019/5/31
     * @Description: 查询省份
     */
    public List<Province> getProvinces(){
        List<Province> provinces = provinceMapper.selectAll();
        return provinces;
    }
}
