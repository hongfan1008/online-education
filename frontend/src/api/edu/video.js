import request from '@/utils/request'
export default {

  addVideo(video) {
    return request({
      url: `/eduservice/video/addVideo`,
      method: 'post',
      data: video
    })
  },
  deleteVideo(id) {
    return request({
      url: `/eduservice/video/deleteVideo/${id}`,
      method: 'delete'
    })
  },
  updateVideo(video) {
    return request({
      url: `/eduservice/video/updateVideo`,
      method: 'post',
      data: video
    })
  },
  getVideoById(id) {
    return request({
      url: `/eduservice/video/getVideoById/${id}`,
      method: 'get'
    })
  },
  deleteAlyVideo(videoId){
    return request({
      url: `/eduvod/video/removeAlyVideo/${videoId}`,
      method: 'delete'
    })
  }
}
