package com.aoye.product.service;

import com.aoye.common.enums.ExceptionEnum;
import com.aoye.common.exception.AyException;
import com.aoye.common.utils.IdWorker;
import com.aoye.common.utils.UUIDUtil;
import com.aoye.common.vo.PageResult;
import com.aoye.product.mapper.CategoryMapper;
import com.aoye.product.pojo.Category;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private IdWorker idWorker;

    /**
    * @Description:    新增分类
    * @Author:         Alex
    * @CreateDate:     2019/5/6
    */
    @Transactional
    public void saveCategory(Category category) {
        category.setId(idWorker.nextId());
        int insert = categoryMapper.insert(category);
        if (insert != 1) {
            throw new AyException(ExceptionEnum.FAIl_TO_ADD);
        }
    }
    /**
    * @Description:    删除分类
    * @Author:         Alex
    * @CreateDate:     2019/5/6
    */
    @Transactional
    public void delCategory(Long id) {
        int delete = categoryMapper.deleteByPrimaryKey(id);
        if (delete != 1) {
            throw new AyException(ExceptionEnum.FAIl_TO_DEL);
        }
    }
    /**
    * @Description:    查单个分类
    * @Author:         Alex
    * @CreateDate:     2019/5/6
    */
    public Category queryCategoryById(Long id){
        Category category = categoryMapper.selectByPrimaryKey(id);
        if (category == null) {
            throw new AyException(ExceptionEnum.INFO_NOT_FOUND);
        }
        return category;
    }

    /**
    * @Description:    修改分类
    * @Author:         Alex
    * @CreateDate:     2019/5/6
    */
    @Transactional
    public void updateCategory(Category category){
        int update = categoryMapper.updateByPrimaryKey(category);
        if (update != 1) {
            throw new AyException(ExceptionEnum.FAIl_TO_UPDATE);
        }
    }
    /**
    * @Description:    作用描述
    * @Author:         Alex
    * @CreateDate:     2019/5/6
    */
    public List<Category> queryAll(){
        List<Category> categories = categoryMapper.selectAll();
        if (CollectionUtils.isEmpty(categories)) {
            throw new AyException(ExceptionEnum.INFO_NOT_FOUND);
        }
        return categories;
    }

    /**
     * @Description:    查分类分页
     * @Author:         Alex
     * @CreateDate:     2019/5/6
     */
    public PageResult<Category> queryCategoryByPage(
            Integer page, Integer rows, String sortBy, Boolean desc, String key){
        // 开始分页
        PageHelper.startPage(page, rows);
        // 过滤
        Example example = new Example(Category.class);
        if (StringUtils.isNotBlank(key)) {
            example.createCriteria().andLike("cname", "%" + key + "%")
                    .orLike("id", "%" + key + "%");
        }
        if (StringUtils.isNotBlank(sortBy)) {
            // 排序
            String orderByClause = sortBy + (desc ? " DESC" : " ASC");
            example.setOrderByClause(orderByClause);
        }
        // 查询
        Page<Category> pageInfo = (Page<Category>) categoryMapper.selectByExample(example);
        if (pageInfo.getTotal()==0) {
            throw new AyException(ExceptionEnum.INFO_NOT_FOUND);
        }
        // 返回结果
        PageResult<Category> result = new PageResult<>(pageInfo.getTotal(), pageInfo);
        return result;
    }
}
