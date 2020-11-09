package com.atguigu.edu.service;

import com.atguigu.edu.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-07-28
 */
public interface EduVideoService extends IService<EduVideo> {

    void deleteVideoByCourseId(String courseId);

}
