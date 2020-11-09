package com.atguigu.edu.service.impl;

import com.alibaba.excel.EasyExcel;
import com.atguigu.edu.entity.EduSubject;
import com.atguigu.edu.entity.excel.SubjectData;
import com.atguigu.edu.entity.subject.OneSubject;
import com.atguigu.edu.entity.subject.TwoSubject;
import com.atguigu.edu.listener.SubjectExcelListener;
import com.atguigu.edu.mapper.EduSubjectMapper;
import com.atguigu.edu.service.EduSubjectService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-07-27
 *
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {
        public void addSubject(MultipartFile file,EduSubjectService eduSubjectService){
            try {
                InputStream inputStream = file.getInputStream();
                EasyExcel.read(inputStream, SubjectData.class,new SubjectExcelListener(eduSubjectService)).sheet().doRead();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }

    @Override
    public List<OneSubject> getAllSubject() {
        QueryWrapper<EduSubject> wrapperOne = new QueryWrapper<>();
        wrapperOne.eq("parent_id",0);
        List<EduSubject> eduSubjects = baseMapper.selectList(wrapperOne);
        QueryWrapper<EduSubject> wrapperTwo = new QueryWrapper<>();
        wrapperTwo.ne("parent_id", 0);
        List<EduSubject> eduSubjects1 = baseMapper.selectList(wrapperTwo);
      List<OneSubject> list =  new ArrayList<>();
      String id = null;
      String title = null;
      for (EduSubject eduSubject:eduSubjects){
//          id = eduSubject.getId();
//          title = eduSubject.getTitle();
          OneSubject oneSubject = new OneSubject();
//          oneSubject.setId(id);
//          oneSubject.setTitle(title);
          BeanUtils.copyProperties(eduSubject,oneSubject);
          list.add(oneSubject);
          List<TwoSubject> twoSubjects =  new ArrayList<>();
          for (EduSubject eduSubject2:eduSubjects1){
           if (eduSubject2.getParentId().equals(eduSubject.getId())){
               TwoSubject twoSubject  = new TwoSubject();
               BeanUtils.copyProperties(eduSubject2,twoSubject);
               twoSubjects.add(twoSubject);

           }

          }
          oneSubject.setChildren(twoSubjects);
      }
        

        return list;
    }
}
