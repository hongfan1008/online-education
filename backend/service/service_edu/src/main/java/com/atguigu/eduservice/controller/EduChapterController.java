package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduChapter;
import com.atguigu.eduservice.entity.chapter.Chapter;
import com.atguigu.eduservice.service.EduChapterService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-07-28
 */
@CrossOrigin
@RestController
@RequestMapping("/eduservice/chapter")
public class EduChapterController {
    @Resource
    private EduChapterService eduChapterService;
    @GetMapping("getChapterVideo/{courseId}")
    public R getChapterVideo(@PathVariable String courseId){
       List<Chapter> list = eduChapterService.getChapterVideo(courseId);
        return R.ok().data("list",list);
    }
        @PostMapping("addChapter")
        public R addChapter(@RequestBody EduChapter eduChapter) {
            eduChapterService.save(eduChapter);
            return R.ok();
        }

    @GetMapping("getChapterById/{chapterId}")
    public R getChapterById(@PathVariable String chapterId){
        EduChapter eduChapter = eduChapterService.getById(chapterId);
        return R.ok().data("chapter",eduChapter);

    }

    @PostMapping("updateChapter")
    public R updateChapter(@RequestBody EduChapter eduChapter){
        eduChapterService.updateById(eduChapter);
        return R.ok();
    }
    @DeleteMapping("deleteChapter/{chapterId}")
    public R deleteChapter(@PathVariable String chapterId){
        boolean flag = eduChapterService.deleteChapter(chapterId);
        if (flag){
            return R.ok();
        }else {
            return R.error();
        }
    }

}

