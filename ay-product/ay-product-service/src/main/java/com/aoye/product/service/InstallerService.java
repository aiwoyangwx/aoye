package com.aoye.product.service;

import com.aoye.common.enums.ExceptionEnum;
import com.aoye.common.exception.AyException;
import com.aoye.common.utils.IdWorker;
import com.aoye.common.vo.PageResult;
import com.aoye.product.mapper.CategoryMapper;
import com.aoye.product.mapper.InstallerMapper;
import com.aoye.product.pojo.Category;
import com.aoye.product.pojo.Installer;
import com.aoye.product.vo.InstallerVO;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.entity.Example;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
@PropertySource("classpath:application.yml")
public class InstallerService {

    @Autowired
    private InstallerMapper installerMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private IdWorker idWorker;

    @Value("${installersPath}")
    private String filesPath;

    @Value("${successImgUrl}")
    private String successImgUrl;

    /**
     * @Author: Alex
     * @CreateDate: 2019/5/10
     * @Description: 新增版本
     */
    public void saveInstaller(Installer installer) {
        //校验是否有重复版本
        Example example = new Example(Installer.class);
        example.createCriteria().andEqualTo("cid", installer.getCid())
                .andEqualTo("versionNum", installer.getVersionNum());
        List<Installer> installers = installerMapper.selectByExample(example);
        if (!CollectionUtils.isEmpty(installers)){
            throw new AyException(ExceptionEnum.INSTALLER_REPETITION);
        }
        //保存
        installer.setId(idWorker.nextId());
        installer.setCreateTime(new Date());
        String fileName = installer.getFileName();
        String[] strings = fileName.substring(successImgUrl.length()+1).trim().split("\\?");
        installer.setFileName(strings[0]);
        installer.setFileSize(Long.parseLong(strings[1]));
        int insert = installerMapper.insert(installer);
        if (insert != 1) {
            throw new AyException(ExceptionEnum.FAIl_TO_ADD);
        }
    }

    /**
     * @Author: Alex
     * @CreateDate: 2019/5/10
     * @Description: 删除版本
     */
    public void delelInstaller(Long id) {
        Installer installer = installerMapper.selectByPrimaryKey(id);
        File file = new File(filesPath + "\\" + installer.getFileName());
        if (file != null) {
            file.delete();
        }
        int delete = installerMapper.deleteByPrimaryKey(id);
        if (delete!=1) {
            throw new AyException(ExceptionEnum.FAIl_TO_DEL);
        }
    }

    /**
     * @Author: Alex
     * @CreateDate: 2019/5/10
     * @Description: 查单个版本
     */
    public Installer queryById(Long id) {
        Installer installer = installerMapper.selectByPrimaryKey(id);
        if (installer == null) {
            throw new AyException(ExceptionEnum.INFO_NOT_FOUND);
        }
        return installer;
    }

    /**
     * @Author: Alex
     * @CreateDate: 2019/5/10
     * @Description: 修改版本
     */
    public void updateById(Installer installer) {
        int update = installerMapper.updateByPrimaryKey(installer);
        if (update != 1) {
            throw new AyException(ExceptionEnum.FAIl_TO_UPDATE);
        }
    }

    /**
     * @Author: Alex
     * @CreateDate: 2019/5/10
     * @Description: 查询版本分页
     */
    public PageResult<InstallerVO> queryInstallersByPage(
            Integer page, Integer rows, String sortBy, Boolean desc, String key) {
        // 开始分页
        PageHelper.startPage(page, rows);
        // 过滤
        Example example = new Example(Installer.class);
        if (StringUtils.isNotBlank(key)) {
            example.createCriteria().andLike("vresionCode", "%" + key + "%");
        }
        if (StringUtils.isNotBlank(sortBy)) {
            // 排序
            String orderByClause = sortBy + (desc ? " DESC" : " ASC");
            example.setOrderByClause(orderByClause);
        }
        // 查询
        Page<Installer> pageInfo = (Page<Installer>) installerMapper.selectByExample(example);

        List<Installer> installers = pageInfo.getResult();
        if (pageInfo.getTotal()==0) {
            throw new AyException(ExceptionEnum.INFO_NOT_FOUND);
        }
        List<InstallerVO> listVO = new ArrayList<>();
        for (Installer installer : installers) {
            Category category = categoryMapper.selectByPrimaryKey(installer.getCid());
            if (category == null) {
                throw new AyException(ExceptionEnum.INFO_NOT_FOUND);
            }
            InstallerVO vo = new InstallerVO();
            BeanUtils.copyProperties(installer,vo);
            vo.setCname(category.getCname());
            listVO.add(vo);
        }
        // 返回结果
        PageResult<InstallerVO> result = new PageResult<>(pageInfo.getTotal(),listVO);
        return result;
    }

    /**
    * @Author:         Alex
    * @CreateDate:     2019/5/10
    * @Description:    作用描述
    */
    public String getUploadPath(MultipartFile file) {

            // 1、生成保存目录
            File dir = new File(filesPath);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            if (Arrays.asList(dir.list()).contains(file.getOriginalFilename())){
                throw new AyException(ExceptionEnum.FILE_NAME_REPETITION);
            }
            // 2、保存文件
            try {
                file.transferTo(new File(dir, file.getOriginalFilename()));
            }catch (Exception e){
                e.printStackTrace();
            }
            // 3、返回成功路径
            String path = successImgUrl+"?"+file.getOriginalFilename()+"?"+file.getSize();
            return path;
    }
    /**
     * @Author: Alex
     * @CreateDate: 2019/5/10
     * @Description: 根据分类和版本号查客户端
     */
    public String getDownloadPath(Long cid, Integer versionNum) {
        String path = "";
        Example example = new Example(Installer.class);
        example.createCriteria().andEqualTo("cid", cid)
                .andGreaterThan("version_num", versionNum);
        example.setOrderByClause("version_num DESC");
        List<Installer> installers = installerMapper.selectByExample(example);
        if (!CollectionUtils.isEmpty(installers)) {
            path = filesPath + "/" + installers.get(0).getFileName();
        }
        return path;
    }
}
