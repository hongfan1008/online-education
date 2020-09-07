package com.atguigu.oss.controller;

import com.atguigu.commonutils.R;
import com.atguigu.oss.service.FileService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@RestController
@RequestMapping("/eduoss/file")
@CrossOrigin
public class FileController {
    @Resource
    private FileService fileService;
    @PostMapping("upload")
    public R uploadOssFile(MultipartFile file){
        String url = fileService.upload(file);
        return R.ok().message("文件上传成功").data("url",url);
    }
}
