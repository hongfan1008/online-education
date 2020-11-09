package com.atguigu.edu.controller;


import com.atguigu.commonutils.R;
import com.atguigu.edu.entity.EduCourse;
import com.atguigu.edu.entity.vo.Course;
import com.atguigu.edu.entity.vo.CoursePublish;
import com.atguigu.edu.entity.vo.CourseQuery;
import com.atguigu.edu.service.EduCourseService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.util.StringUtils;
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
@RequestMapping("/eduservice/course")
public class EduCourseController {
    @Resource
    private EduCourseService eduCourseService;
    @GetMapping("getCourseList")
    public R getCourseList(){
        List<EduCourse> list = eduCourseService.list(null);
        return R.ok().data("list",list);
    }
    //分页显示列表
    @GetMapping("pageCourseList/{current}/{limit}")
    public R pageCourseList(@PathVariable long current,
                             @PathVariable long limit) {
        //创建page对象
        Page<EduCourse> pageCourse = new Page<>(current,limit);
        //调用方法实现分页
        //调用方法时候，底层封装，把分页所有数据封装到pageTeacher对象里面
        eduCourseService.page(pageCourse, null);

        long total = pageCourse.getTotal();//总记录数
        List<EduCourse> records = pageCourse.getRecords(); //数据list集合

        return R.ok().data("total",total).data("rows",records);
    }
    @PostMapping("pageCourseListCondition/{current}/{limit}")
    //条件查询
    public R pageCourseListCondition(@PathVariable long current,
                                     @PathVariable long limit, @RequestBody(required = false) CourseQuery courseQuery){
        Page<EduCourse> page = new Page<>(current,limit);

        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        String title = courseQuery.getTitle();
        String subjectId = courseQuery.getSubjectId();
        String subjectParentId = courseQuery.getSubjectParentId();
        String teacherId = courseQuery.getTeacherId();
        //判断条件值是否为空，如果不为空拼接条件
        if(!StringUtils.isEmpty(title)) {
            //构建条件
            wrapper.like("title",title);
        }
        if(!StringUtils.isEmpty(subjectId)) {
            wrapper.eq("subject_id",subjectId);
        }
        if(!StringUtils.isEmpty(subjectParentId)) {
            wrapper.ge("subject_parent_id",subjectParentId);
        }
        if(!StringUtils.isEmpty(teacherId)) {
            wrapper.le("teacher_id",teacherId);
        }
        wrapper.orderByDesc("gmt_create");
        //调用方法实现条件查询分页
        eduCourseService.page(page,wrapper);

        long total = page.getTotal();//总记录数
        List<EduCourse> records = page.getRecords(); //数据list集合
        return R.ok().data("total",total).data("rows",records);
    }

    @PostMapping("addCourseInfo")
    public R addCourseInfo(@RequestBody Course course){
        String id = eduCourseService.addCourseInfo(course);
        return R.ok().data("id",id);
    }
    @GetMapping("getCourseById/{courseId}")
    public R getCourseById(@PathVariable String courseId){
        Course course = eduCourseService.getCourseById(courseId);
        return R.ok().data("course",course);
    }

    @PostMapping("updateCourse")
    public R updateCourse(@RequestBody Course course){
        eduCourseService.updateCourse(course);
        return R.ok();
    }
    @GetMapping("getCoursePublishById/{id}")
    public R getCoursePublishById(@PathVariable String id){
      CoursePublish coursePublish =  eduCourseService.getCoursePublishById(id);
        return R.ok().data("coursePublish",coursePublish);
    }
    @PostMapping("publishCourseById/{id}")
    public R publishCourseById(@PathVariable String id){
        eduCourseService.publishCourseById(id);
        return R.ok();
    }
    @DeleteMapping("deleteCourseById/{id}")
    public R deleteCourseById(@PathVariable String id){
        eduCourseService.deleteCourseById(id);
        return R.ok();
    }


}

