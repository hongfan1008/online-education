package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.service.EduVideoService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-07-28
 */
@CrossOrigin
@RestController
@RequestMapping("/eduservice/video")
public class EduVideoController {
    @Resource
    private EduVideoService eduVideoService;
    @PostMapping("addVideo")
    public R addVideo(@RequestBody EduVideo eduVideo){
        eduVideoService.save(eduVideo);
        return R.ok();
    }
    //TODO 后面需要完善：删除小节的时候同时删除小节下的所有视频
    @DeleteMapping("deleteVideo/{id}")
    public R deleteVideo(@PathVariable String id){
        eduVideoService.removeById(id);
        return R.ok();
    }
    @GetMapping("getVideoById/{id}")
    public R getVideoById(@PathVariable String id){
        EduVideo video = eduVideoService.getById(id);
        return R.ok().data("video",video);
    }
    @PostMapping("updateVideo")
    public R updateVideo(@RequestBody EduVideo eduVideo){
        eduVideoService.updateById(eduVideo);
        return R.ok();
    }

}

