package com.aoye.product.controller;

import com.aoye.common.enums.ExceptionEnum;
import com.aoye.common.exception.AyException;
import com.aoye.common.utils.IdWorker;
import com.aoye.common.vo.PageResult;
import com.aoye.product.pojo.Category;
import com.aoye.product.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * @Description: 新增分类
     * @Author: Alex
     * @CreateDate: 2019/5/6
     */
    @PostMapping
    public ResponseEntity<Void> queryCategorys(Category category) {
        categoryService.saveCategory(category);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * @Description: 删除分类
     * @Author: Alex
     * @CreateDate: 2019/5/6
     */
    @DeleteMapping
    public ResponseEntity<Void> delById(@RequestParam("id")Long id) {
        categoryService.delCategory(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * @Description: 查单个分类
     * @Author: Alex
     * @CreateDate: 2019/5/6
     */
    @GetMapping
    public ResponseEntity<Category> queryById(@RequestParam("id")Long id) {
        Category category = categoryService.queryCategoryById(id);
        return ResponseEntity.ok(category);
    }

    /**
     * @Description: 修改分类
     * @Author: Alex
     * @CreateDate: 2019/5/6
     */
    @PutMapping
    public ResponseEntity<Void> updateById(Category category) {
        if (category.getId()==null) {
            throw new AyException(ExceptionEnum.INCORRECT_PARAMS);
        }
        categoryService.updateCategory(category);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * @Description: 查分类分页
     * @Author: Alex
     * @CreateDate: 2019/5/6
     */
    @GetMapping("/page")
    public ResponseEntity<PageResult<Category>> queryCategoryByPage(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "rows", defaultValue = "5") Integer rows,
            @RequestParam(value = "sortBy", required = false) String sortBy,
            @RequestParam(value = "desc", defaultValue = "false") Boolean desc,
            @RequestParam(value = "key", required = false) String key) {
        PageResult<Category> result = categoryService.queryCategoryByPage(page, rows, sortBy, desc, key);
        return ResponseEntity.ok(result);
    }

    /**
     * @Description: 查全部分类，用于下拉列表
     * @Author: Alex
     * @CreateDate: 2019/5/6
     */
    @GetMapping("/all")
    public ResponseEntity<List<Category>> queryAll(){
        List<Category> categories = categoryService.queryAll();
        return ResponseEntity.ok(categories);
    }
}








