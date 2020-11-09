package com.atguigu.edu.service.impl;

import com.atguigu.edu.entity.EduCourse;
import com.atguigu.edu.entity.EduCourseDescription;
import com.atguigu.edu.entity.vo.Course;
import com.atguigu.edu.entity.vo.CoursePublish;
import com.atguigu.edu.mapper.EduCourseMapper;
import com.atguigu.edu.service.EduChapterService;
import com.atguigu.edu.service.EduCourseDescriptionService;
import com.atguigu.edu.service.EduCourseService;
import com.atguigu.edu.service.EduVideoService;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-07-28
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    @Resource
    private EduCourseDescriptionService eduCourseDescriptionService;
    @Resource
    private EduVideoService eduVideoService;
    @Resource
    private EduChapterService eduChapterService;
    @Override
    public String addCourseInfo(Course course) {
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(course,eduCourse);
        int insert = baseMapper.insert(eduCourse);
        if(insert == 0){
            throw new GuliException(201,"添加课程失败");
        }
        String cid = eduCourse.getId();

        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        eduCourseDescription.setDescription(course.getDescription());
        eduCourseDescription.setId(cid);
        eduCourseDescriptionService.save(eduCourseDescription);
        return cid;

    }

    @Override
    public Course getCourseById(String courseId) {
        Course course = new Course();
        EduCourse eduCourse = baseMapper.selectById(courseId);
        BeanUtils.copyProperties(eduCourse,course);
        EduCourseDescription eduCourseDescription = eduCourseDescriptionService.getById(courseId);
        BeanUtils.copyProperties(eduCourseDescription,course);
        return course;
    }

    @Override
    public void updateCourse(Course course) {
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(course,eduCourse);
        int i = baseMapper.updateById(eduCourse);
        if (i==0){
            throw new GuliException(20001,"修改课程信息失败");
        }
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        eduCourseDescription.setId(course.getId());
        eduCourseDescriptionService.updateById(eduCourseDescription);

    }

    @Override
    public CoursePublish getCoursePublishById(String id) {
        CoursePublish coursePublish = baseMapper.getCoursePublishInfo(id);
        return coursePublish;
    }

    @Override
    public void publishCourseById(String id) {
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(id);
        eduCourse.setStatus("Normal");
        baseMapper.updateById(eduCourse);
    }

    @Override
    public void deleteCourseById(String courseId) {
        eduVideoService.deleteVideoByCourseId(courseId);
        eduChapterService.deleteChapterByCourseId(courseId);
        eduCourseDescriptionService.removeById(courseId);//描述id和课程id一样
        int i = baseMapper.deleteById(courseId);
        if (i == 0){
            new GuliException(20001,"删除失败");
        }
    }

}
