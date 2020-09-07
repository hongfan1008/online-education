package com.atguigu.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.atguigu.oss.service.FileService;
import com.atguigu.oss.utils.ConstantPropertiesUtils;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {
    @Override
    public String upload(MultipartFile file) {
        String endpoint = ConstantPropertiesUtils.END_POINT;
        // 云账号AccessKey有所有API访问权限，建议遵循阿里云安全最佳实践，创建并使用RAM子账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建。
        String accessKeyId = ConstantPropertiesUtils.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtils.ACCESS_KEY_SECRET;
        String bucketName = ConstantPropertiesUtils.BUCKET_NAME;
        String url="";
            try{
                // 创建OSSClient实例。
                OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

                // 上传文件流。
                InputStream inputStream = file.getInputStream();
                String fileName = file.getOriginalFilename();
                String s = UUID.randomUUID().toString().replaceAll("-","");
                fileName = s + fileName;

                String datePath = new DateTime().toString("yyyy/MM/dd");
                fileName = datePath+"/"+fileName;

                ossClient.putObject(ConstantPropertiesUtils.BUCKET_NAME, fileName, inputStream);
                // 关闭OSSClient。
                ossClient.shutdown();
                //https://edu10.oss-cn-beijing.aliyuncs.com/WechatIMG19.jpeg
                 url = "https://"+bucketName+"."+endpoint+"/"+fileName;
            }catch (Exception e){
                throw new GuliException(201,"文件上传失败");
            }
        return url;
    }
}
