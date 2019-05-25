package com.aoye.product.service;

import com.aoye.common.enums.ExceptionEnum;
import com.aoye.common.exception.AyException;
import com.aoye.common.utils.IdWorker;
import com.aoye.common.vo.PageResult;
import com.aoye.product.mapper.RunningMachineMapper;
import com.aoye.product.pojo.Category;
import com.aoye.product.pojo.RunningMachine;
import com.aoye.product.vo.RunningMachineVO;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class RunningMachineService {

    @Autowired
    private RunningMachineMapper runningMachineMapper;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private IdWorker idWorker;

    /**
    * @Description:    新增跑步机
    * @Author:         Alex
    * @CreateDate:     2019/5/6
    */
    @Transactional
    public void saveRunningMachine(RunningMachine runningMachine) {
        //手动新增跑步机录入主键
        runningMachine.setId(idWorker.nextId());
        runningMachine.setCreateTime(new Date());
        int insert = runningMachineMapper.insert(runningMachine);
        if (insert != 1) {
            throw new AyException(ExceptionEnum.FAIl_TO_ADD);
        }
    }

    /**
     * @Description: 删除跑步机
     * @Author: Alex
     * @CreateDate: 2019/5/6
     */
    @Transactional
    public void delById(String id) {
        int delete = runningMachineMapper.deleteByPrimaryKey(id);
        if (delete != 1) {
            throw new AyException(ExceptionEnum.FAIl_TO_DEL);
        }
    }

    /**
     * @Description: 查单个跑步机
     * @Author: Alex
     * @CreateDate: 2019/5/6
     */
    public RunningMachineVO queryById(Long id) {
        RunningMachine runningMachine = runningMachineMapper.selectByPrimaryKey(id);
        Category category = categoryService.queryCategoryById(runningMachine.getCid());
        if (runningMachine == null||category==null) {
            throw new AyException(ExceptionEnum.INFO_NOT_FOUND);
        }
        RunningMachineVO runningMachineVO = new RunningMachineVO();
        BeanUtils.copyProperties(runningMachine,runningMachineVO);
        runningMachineVO.setCname(category.getCname());
        return runningMachineVO;
    }

    /**
     * @Description: 修改跑步机
     * @Author: Alex
     * @CreateDate: 2019/5/6
     */
    public void updateById(RunningMachine runningMachine) {
        runningMachine.setUpdateTime(new Date());
        int update = runningMachineMapper.updateByPrimaryKey(runningMachine);
        if (update != 1) {
            throw new AyException(ExceptionEnum.FAIl_TO_UPDATE);
        }
    }

    /**
     * @Description: 查分页
     * @Author: Alex
     * @CreateDate: 2019/5/6
     */
    public PageResult<RunningMachineVO> queryByPage(Integer page, Integer rows, String sortBy, Boolean desc, String key) {
        //分页
        PageHelper.startPage(page, rows);
        //过滤
        Example example = new Example(RunningMachine.class);
        if (StringUtils.isNotBlank(key)) {
            example.createCriteria().andLike("id", "%" + key + "%");
        }
        //排序
        if (StringUtils.isNotBlank(sortBy)) {
            String orderByClause = sortBy + (desc ? " desc" : " asc");
            example.setOrderByClause(orderByClause);
        }
        //查询
        Page<RunningMachine> pageInfo = (Page<RunningMachine>) runningMachineMapper.selectByExample(example);
        if (pageInfo.getTotal()==0) {
            throw new AyException(ExceptionEnum.INFO_NOT_FOUND);
        }
        List<RunningMachine> list = pageInfo.getResult();

        List<RunningMachineVO> listVO =new ArrayList<>();

        for (RunningMachine runningMachine : list) {
            RunningMachineVO vo = new RunningMachineVO();
            BeanUtils.copyProperties(runningMachine, vo);
            Category category = categoryService.queryCategoryById(runningMachine.getCid());
            vo.setCname(category.getCname());
            listVO.add(vo);
        }
        //返回结果
        PageResult<RunningMachineVO> result = new PageResult<>(pageInfo.getTotal(),listVO);
        return result;
    }

}
