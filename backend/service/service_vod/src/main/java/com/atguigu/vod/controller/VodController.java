package com.atguigu.vod.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.atguigu.commonutils.R;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.atguigu.vod.service.VodService;
import com.atguigu.vod.utils.ConstantVod;
import com.atguigu.vod.utils.InitVod;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@CrossOrigin
@RestController
@RequestMapping("/eduvod/video")
public class VodController {
    @Resource
    private VodService vodService;
    @PostMapping("uploadVideo")
    public R uploadVideo(MultipartFile file){
        String videoId = vodService.uploadVideo(file);
        return R.ok().data("videoId",videoId);
    }
    //根据视频id删除阿里云视频
    @DeleteMapping("removeAlyVideo/{videoId}")
    public R removeAlyVideo(@PathVariable String videoId){
        try{
            //初始化对象
            DefaultAcsClient client = InitVod.initVodClient(ConstantVod.ACCESS_KEY_ID,ConstantVod.ACCESS_KEY_SECRET);
            //创建删除视频request对象
            DeleteVideoRequest request = new DeleteVideoRequest();
            //向request中设置id值
            request.setVideoIds(videoId);
            //调用初始化对象的方法实现删除
            client.getAcsResponse(request);
        }catch (Exception e){
            throw new GuliException(20001,"删除视频失败");
        }
        return R.ok();
    }
}
