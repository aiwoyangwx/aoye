package com.aoye.product.controller;

import com.aoye.common.enums.ExceptionEnum;
import com.aoye.common.exception.AyException;
import com.aoye.common.utils.UUIDUtil;
import com.aoye.common.vo.PageResult;
import com.aoye.product.pojo.RunningMachine;
import com.aoye.product.service.RunningMachineService;
import com.aoye.product.vo.RunningMachineVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/runningmachine")
public class RunningMachineController {

    @Autowired
    private RunningMachineService runningMachineService;

    /**
     * @Description: 新增跑步机
     * @Author: Alex
     * @CreateDate: 2019/5/6
     */
    @PostMapping
    public ResponseEntity<Void> save(RunningMachine runningMachine) {
        /*if (StringUtils.isBlank(runningMachine.getId())) {
            throw new AyException(ExceptionEnum.INCORRECT_PARAMS);
        }*/
        if (runningMachine.getCid()==null) {
            throw new AyException(ExceptionEnum.INCORRECT_PARAMS);
        }
        runningMachineService.saveRunningMachine(runningMachine);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * @Description: 新增跑步机
     * @Author: Alex
     * @CreateDate: 2019/5/6
     */
    @GetMapping("add")
    public ResponseEntity<String> saveByUrl(
            @RequestParam("id")Long id,@RequestParam("cid")Long cid) {
        RunningMachine runningMachine = new RunningMachine();
        runningMachine.setId(id);
        runningMachine.setCid(cid);
        runningMachineService.saveRunningMachine(runningMachine);
        return ResponseEntity.ok("跑步机添加成功！");
    }

    /**
     * @Description: 删除跑步机
     * @Author: Alex
     * @CreateDate: 2019/5/6
     */
    @DeleteMapping
    public ResponseEntity<Void> delById(@RequestParam("id") String id) {
        runningMachineService.delById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * @Description: 查单个
     * @Author: Alex
     * @CreateDate: 2019/5/6
     */
    @GetMapping
    public ResponseEntity<RunningMachineVO> queryById(@RequestParam("id") Long id) {
        RunningMachineVO vo = runningMachineService.queryById(id);
        return ResponseEntity.ok(vo);
    }

    /**
     * @Description: 修改跑步机
     * @Author: Alex
     * @CreateDate: 2019/5/6
     */
    @PutMapping
    public ResponseEntity<Void> updateById(RunningMachine runningMachine) {
        if (runningMachine.getId()==null) {
            throw new AyException(ExceptionEnum.INCORRECT_PARAMS);
        }
        runningMachineService.updateById(runningMachine);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    /**
    * @Description:    查分页
    * @Author:         Alex
    * @CreateDate:     2019/5/6
    */
    @GetMapping("page")
    public ResponseEntity<PageResult<RunningMachineVO>> queryByPage(
          @RequestParam(value = "page", defaultValue = "1") Integer page,
          @RequestParam(value = "rows", defaultValue = "5") Integer rows,
          @RequestParam(value = "sortBy", required = false) String sortBy,
          @RequestParam(value = "desc", defaultValue = "false") Boolean desc,
          @RequestParam(value = "key", required = false) String key){
        PageResult<RunningMachineVO> result = runningMachineService.queryByPage(page, rows, sortBy, desc, key);
        return ResponseEntity.ok(result);
    }
}
