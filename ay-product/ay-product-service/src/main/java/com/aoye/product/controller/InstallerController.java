package com.aoye.product.controller;

import com.aoye.common.enums.ExceptionEnum;
import com.aoye.common.exception.AyException;
import com.aoye.common.vo.PageResult;
import com.aoye.product.pojo.Installer;
import com.aoye.product.service.InstallerService;
import com.aoye.product.vo.InstallerVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

@RestController
@RequestMapping("installer")
public class InstallerController {

    @Autowired
    private InstallerService installerService;

    @Value("${installersPath}")
    private String filesPath;
    /**
    * @Author:         Alex
    * @CreateDate:     2019/5/10
    * @Description:    新增客户端
    */
    @PostMapping
    public ResponseEntity<Void> saveInstall(Installer installer) {
        if (StringUtils.isBlank(installer.getFileName())) {
            throw new AyException(ExceptionEnum.INCORRECT_PARAMS);
        }
        installerService.saveInstaller(installer);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    /**
    * @Author:         Alex
    * @CreateDate:     2019/5/10
    * @Description:    删除客户端
    */
    @DeleteMapping
    public ResponseEntity<Void> delById(@RequestParam("id")Long id){
        if (id == null) {
            throw new AyException(ExceptionEnum.INCORRECT_PARAMS);
        }
        installerService.delelInstaller(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * @Author:         Alex
     * @CreateDate:     2019/5/10
     * @Description:    删除客户端
     */
    @DeleteMapping("url/{url}")
    public ResponseEntity<Void> delByUrl(@PathVariable("url")String url){
        if (url != null) {
            String[] split = url.split("\\?");
            File file = new File(filesPath + '\\' + split[0]);
            if (file != null) {
                file.delete();
            }
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }



    /**
    * @Author:         Alex
    * @CreateDate:     2019/5/10
    * @Description:    查找单个客户端
    */
    @GetMapping
    public ResponseEntity<Installer> queryById(@RequestParam("id")Long id){
        if (id == null) {
            throw new AyException(ExceptionEnum.INCORRECT_PARAMS);
        }
        Installer installer = installerService.queryById(id);
        return ResponseEntity.ok(installer);
    }
    /**
    * @Author:         Alex
    * @CreateDate:     2019/5/10
    * @Description:    修改客户端
    */
    @PutMapping
    public ResponseEntity<Installer> updateById(Installer installer){
        if (installer == null || installer.getId() == null) {
            throw new AyException(ExceptionEnum.INCORRECT_PARAMS);
        }
        installerService.updateById(installer);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    /**
    * @Author:         Alex
    * @CreateDate:     2019/5/10
    * @Description:    查找客户端分页
    */
    @GetMapping("page")
    public ResponseEntity<PageResult<InstallerVO>> queryInstallersByPage(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
           @RequestParam(value = "rows", defaultValue = "5") Integer rows,
           @RequestParam(value = "sortBy", required = false) String sortBy,
           @RequestParam(value = "desc", defaultValue = "false") Boolean desc,
           @RequestParam(value = "key", required = false) String key){
        PageResult<InstallerVO> pageResult = installerService.queryInstallersByPage(page, rows, sortBy, desc, key);
        return ResponseEntity.ok(pageResult);
    }

    /**
    * @Author:         Alex
    * @CreateDate:     2019/5/10
    * @Description:    上传
    */
    @PostMapping("upload")
    public ResponseEntity<String> uploadInstaller(
            @RequestParam("file") MultipartFile file,HttpServletRequest request){
        if (file == null) {
            throw new AyException(ExceptionEnum.INCORRECT_PARAMS);
        }
        String uploadPath = null;

        uploadPath = installerService.getUploadPath(file);
        return ResponseEntity.ok(uploadPath);
    }

    /**
    * @Author:         Alex
    * @CreateDate:     2019/5/10
    * @Description:    员工下载
    */
    @GetMapping("download")
    public void download(
            @RequestParam("id")Long id, HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException {
        Installer installer = installerService.queryById(id);
        File file = new File(filesPath + "\\" + installer.getFileName());
        if (file == null) {
            throw new AyException(ExceptionEnum.FILE_NOT_FOUND);
        }
        // 配置文件下载
        response.setHeader("content-type", "application/octet-stream");
        response.setContentType("application/octet-stream");
        // 下载文件能正常显示中文
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(file.getName(), "UTF-8"));
        // 实现文件下载
        byte[] buffer = new byte[1024];
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        try {
            fis = new FileInputStream(file);
            bis = new BufferedInputStream(fis);
            OutputStream os = response.getOutputStream();
            int i = bis.read(buffer);
            while (i != -1) {
                os.write(buffer, 0, i);
                i = bis.read(buffer);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * @Author:         Alex
     * @CreateDate:     2019/5/10
     * @Description:    客户下载
     */
    /*@GetMapping("downloadInstaller")
    public ResponseEntity<String> downloadInstaller(
            @RequestParam("id")Long cid,@RequestParam("versionNum")Integer versionNum,
            HttpServletResponse response){
        if (cid == null || versionNum == null) {
            throw new AyException(ExceptionEnum.INCORRECT_PARAMS);
        }
        String downloadPath = installerService.getDownloadPath(cid, versionNum);
        InputStream in=null;
        ServletOutputStream out=null;
        try {
            in = new FileInputStream(new File(downloadPath));
            out = response.getOutputStream();
            int len = 0;
            byte[] bytes = new byte[1024];
            while ((len = in.read()) > 0) {
                out.write(bytes,0,len);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                in.close();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return ResponseEntity.ok("文件下载成功！");
    }*/
}
