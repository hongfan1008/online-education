import request from '@/utils/request'
export default {

  addCourseInfo(courseInfo){
    return request({
      url:`/eduservice/course/addCourseInfo`,
      method:'post',
      data:courseInfo
    })
  },
  getAllTeacher(){
    return request({
      url:`/eduservice/teacher/findAll`,
      method:'get'
    })
  },
  getCourseById(courseId){
    return request({
      url:`/eduservice/course/getCourseById/${courseId}`,
      method:'get'
    })
  },
  updateCourse(course){
    return request({
      url:`/eduservice/course/updateCourse`,
      method:'post',
      data:course
    })
  },
  getCoursePublishById(id){
    return request({
      url:`/eduservice/course/getCoursePublishById/${id}`,
      method:'get',
    })
  },
  publishCourseById(id){
    return request({
      url:`/eduservice/course/publishCourseById/${id}`,
      method:'post',
    })
  },
  getCourseList(){
    return request({
      url:`/eduservice/course/getCourseList`,
      method:'get',
    })
  },
  pageCourseList(current,limit){
    return request({
      url:`/eduservice/course/pageCourseList/${current}/${limit}`,
      method:'get',
    })
  },
  pageCourseListCondition(current,limit,courseQuery){
    return request({
      url:`/eduservice/course/pageCourseListCondition/${current}/${limit}`,
      method:'post',
      data:courseQuery
    })
  },
  deleteCourseById(courseId){
    return request({
      url:`/eduservice/course/deleteCourseById/${courseId}`,
      method:'delete'
    })
  },
}
