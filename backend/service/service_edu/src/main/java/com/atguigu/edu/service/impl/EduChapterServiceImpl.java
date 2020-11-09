package com.atguigu.edu.service.impl;

import com.atguigu.edu.entity.EduChapter;
import com.atguigu.edu.entity.EduVideo;
import com.atguigu.edu.entity.chapter.Chapter;
import com.atguigu.edu.entity.chapter.Video;
import com.atguigu.edu.mapper.EduChapterMapper;
import com.atguigu.edu.service.EduChapterService;
import com.atguigu.edu.service.EduVideoService;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-07-28
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Resource
    private EduVideoService eduVideoService;
    @Override
    public List<Chapter> getChapterVideo(String courseId) {
        QueryWrapper<EduChapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_Id",courseId);
        List<EduChapter> chapters = baseMapper.selectList(wrapper);

        QueryWrapper<EduVideo> wrapper2 = new QueryWrapper<>();
        wrapper2.eq("course_Id",courseId);
        List<EduVideo> videos = eduVideoService.list(wrapper2);

        List<Chapter> list = new ArrayList<>();

        for (EduChapter eduChapter:chapters){
            Chapter chapter = new Chapter();
            BeanUtils.copyProperties(eduChapter,chapter);
            list.add(chapter);

            List<Video> videos1 = new ArrayList<>();

            for (EduVideo eduVideo:videos){
                if (eduVideo.getChapterId().equals(eduChapter.getId())){
                    Video video = new Video();
                    BeanUtils.copyProperties(eduVideo,video);
                    videos1.add(video);

                }
            }
            chapter.setVideos(videos1);
        }



        return list;

    }

//    @Override
//    public void addChapter(EduChapter eduChapter,String courseId) {
//        eduChapter.setCourseId(courseId);
//        baseMapper.insert(eduChapter);
//    }


    @Override
    public boolean deleteChapter(String chapterId) {
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("chapter_id",chapterId);
        int count = eduVideoService.count(wrapper);
        if (count >0){
            throw new GuliException(20001,"不能删除");
        }else {
            int i = baseMapper.deleteById(chapterId);
            return i>0;
        }

    }

    @Override
    public void deleteChapterByCourseId(String courseId) {
        QueryWrapper<EduChapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        baseMapper.delete(wrapper);
    }
}
