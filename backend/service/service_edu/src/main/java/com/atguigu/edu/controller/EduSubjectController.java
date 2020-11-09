package com.atguigu.edu.controller;


import com.atguigu.commonutils.R;
import com.atguigu.edu.entity.subject.OneSubject;
import com.atguigu.edu.service.EduSubjectService;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2020-07-27
 */
@CrossOrigin
@RestController
@RequestMapping("/eduservice/subject")
public class EduSubjectController {
    @Resource
    EduSubjectService eduSubjectService;
    @PostMapping("add")
    public R addSubject(MultipartFile file){
        eduSubjectService.addSubject(file,eduSubjectService);
        return R.ok();
    }
    //课程分类列别
    @GetMapping("getAllSubject")
    public R getAllSubject(){
        List<OneSubject> allSubject = eduSubjectService.getAllSubject();
        return R.ok().data("allSubject",allSubject);
    }
}

