package com.atguigu.edu.mapper;

import com.atguigu.edu.entity.EduCourse;
import com.atguigu.edu.entity.vo.CoursePublish;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2020-07-28
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {
    public CoursePublish getCoursePublishInfo(String courseId);
}
