import request from '@/utils/request'
export default {

  getChapterVideoList(courseId) {
    return request({
      url: `/eduservice/chapter/getChapterVideo/${courseId}`,
      method: 'get'
    })
  },
  addChapter(eduChapter) {
    return request({
      url: `/eduservice/chapter/addChapter`,
      method: 'post',
      data:eduChapter
    })
  },
  getChapterById(chapterId) {
    return request({
      url: `/eduservice/chapter/getChapterById/${chapterId}`,
      method: 'get'
    })
  },
  updateChapter(eduChapter) {
    return request({
      url: `/eduservice/chapter/updateChapter`,
      method: 'post',
      data:eduChapter
    })
  },
  deleteChapter(chapterId) {
    return request({
      url: `/eduservice/chapter/deleteChapter/${chapterId}`,
      method: 'delete'
    })
  }

}
