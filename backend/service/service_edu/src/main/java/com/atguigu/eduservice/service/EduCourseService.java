package com.atguigu.eduservice.service;

import com.atguigu.eduservice.entity.EduCourse;
import com.atguigu.eduservice.entity.vo.Course;
import com.atguigu.eduservice.entity.vo.CoursePublish;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-07-28
 */
public interface EduCourseService extends IService<EduCourse> {
    public String addCourseInfo(Course course);
    public Course getCourseById(String courseId);
    public void updateCourse(Course course);
    public CoursePublish getCoursePublishById(String id);
    public void publishCourseById(String id);
    public void deleteCourseById(String id);

}
