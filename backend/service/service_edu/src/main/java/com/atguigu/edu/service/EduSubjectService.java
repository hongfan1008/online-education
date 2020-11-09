package com.atguigu.edu.service;

import com.atguigu.edu.entity.EduSubject;
import com.atguigu.edu.entity.subject.OneSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-07-27
 */
public interface EduSubjectService extends IService<EduSubject> {
    public void addSubject(MultipartFile file,EduSubjectService eduSubjectService);

    public List<OneSubject> getAllSubject();
}
