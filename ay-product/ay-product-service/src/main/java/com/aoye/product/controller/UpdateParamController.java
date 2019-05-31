package com.aoye.product.controller;

import com.aoye.common.enums.ExceptionEnum;
import com.aoye.common.exception.AyException;
import com.aoye.common.vo.PageResult;
import com.aoye.product.pojo.*;
import com.aoye.product.service.UpdateParamService;
import com.aoye.product.vo.UpdateParamVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("updateparam")
@RestController
public class UpdateParamController {
    @Autowired
    private UpdateParamService updateParamService;

    /**
     * @Author: Alex
     * @CreateDate: 2019/5/31
     * @Description: 新增升级参数
     */
    @PostMapping
    public ResponseEntity<Void> saveInstall(UpdateParamVo vo) {
        if (vo.getCid()==null
                || CollectionUtils.isEmpty(vo.getIids())
                ||CollectionUtils.isEmpty(vo.getProvinces())
        ){
            throw new AyException(ExceptionEnum.INCORRECT_PARAMS);
        }
        updateParamService.save(vo);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    /**
     * @Author: Alex
     * @CreateDate: 2019/5/31
     * @Description: 删除升级参数
     */
    @DeleteMapping
    public ResponseEntity<Void> delById(@RequestParam("id")Long id) {
        updateParamService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    /**
     * @Author: Alex
     * @CreateDate: 2019/5/31
     * @Description: 查升级参数分页
     */
    @GetMapping("page")
    public ResponseEntity<PageResult<UpdateParam>> queryUpdateParamByPage(
            @RequestParam(value = "page",defaultValue = "1")Integer page,
            @RequestParam(value="rows",defaultValue = "5")Integer rows,
            @RequestParam(value = "sortBy", required = false) String sortBy,
            @RequestParam(value = "desc", defaultValue = "false") Boolean desc,
            @RequestParam(value = "cid", required = false) Long cid,
            @RequestParam(value = "iid", required = false) Integer iid,
            @RequestParam(value = "provinceId", required = false) Integer provinceId
    ){
        PageResult<UpdateParam> result = updateParamService.queryUpdateParamByPage(page, rows, sortBy, desc, cid,iid,provinceId);
        return ResponseEntity.ok(result);
    }
    /**
     * @Author: Alex
     * @CreateDate: 2019/5/31
     * @Description: 查升级的省份
     */
    @GetMapping("province")
    public List<Province> getProvinces(){
        List<Province> provinces = updateParamService.getProvinces();
        return provinces;
    }


}
