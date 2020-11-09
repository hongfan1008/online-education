package com.atguigu.edu.service;

import com.atguigu.edu.entity.EduChapter;
import com.atguigu.edu.entity.chapter.Chapter;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-07-28
 */
public interface EduChapterService extends IService<EduChapter> {
        public List<Chapter> getChapterVideo(String courseId);
//        public void addChapter(EduChapter eduChapter,String courseId);
//        public EduChapter getChapterById(String chapterId);
//        public void updateChapter(EduChapter eduChapter);
        public boolean deleteChapter(String chapterId);

        void deleteChapterByCourseId(String courseId);
}
