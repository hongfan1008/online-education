<template>
  <div class="app-container">
    <h2 style="text-align: center;">发布新课程</h2>
    <el-steps :active="1" process-status="wait" align-center style="margin-bottom: 40px;">
      <el-step title="填写课程基本信息"/>
      <el-step title="创建课程大纲"/>
      <el-step title="提交审核"/>
    </el-steps>
    <el-form label-width="120px">
      <el-form-item label="课程标题">
        <el-input v-model="courseInfo.title" placeholder=" 示例：机器学习项目课：从基础到搭建项目视频课程。专业名称注意大小写"/>
      </el-form-item>

      <!-- 所属分类 TODO -->
      <el-form-item label="课程类别">
        <el-select
          v-model="courseInfo.subjectParentId"
          placeholder="请选择" @change="subjectLevelOneChanged">
          <el-option
            v-for="subject in subjectNestedList"
            :key="subject.id"
            :label="subject.title"
            :value="subject.id"/>
        </el-select>
        <el-select v-model="courseInfo.subjectId" placeholder="请选择">
          <el-option
            v-for="subject in subSubjectList"
            :key="subject.id"
            :label="subject.title"
            :value="subject.id"/>
        </el-select>
      </el-form-item>

      <!-- 课程讲师 TODO -->
      <el-form-item label="课程讲师">
        <el-select
          v-model="courseInfo.teacherId"
          placeholder="请选择">
          <el-option
            v-for="teacher in teacherList"
            :key="teacher.id"
            :label="teacher.name"
            :value="teacher.id"/>
        </el-select>
      </el-form-item>
      <el-form-item label="总课时">
        <el-input-number :min="0" v-model="courseInfo.lessonNum" controls-position="right" placeholder="请填写课程的总课时数"/>
      </el-form-item>
      <!-- 课程简介 TODO -->
      <el-form-item label="课程简介">
        <tinymce :height="300" v-model="courseInfo.description"/>
      </el-form-item>
      <!-- 课程封面 TODO -->
      <el-form-item label="课程封面">
        <el-upload
          :show-file-list="false"
          :on-success="handleAvatarSuccess"
          :before-upload="beforeAvatarUpload"
         :action="BASE_API+'/eduoss/file/upload'"
          class="avatar-uploader">
          <img :src="courseInfo.cover">
        </el-upload>
      </el-form-item>

      <el-form-item label="课程价格">
        <el-input-number :min="0" v-model="courseInfo.price" controls-position="right" placeholder="免费课程请设置为0元"/> 元
      </el-form-item>
      <el-form-item>
        <el-button :disabled="saveBtnDisabled" type="primary" @click="SaveOrUpdate">保存并下一步</el-button>
      </el-form-item>
    </el-form>

  </div>
</template>

<script>
  import course from '@/api/edu/course'
  import subject from '@/api/edu/subject'
  import Tinymce from '@/components/Tinymce'

  export default {
    components: { Tinymce },
    name: 'info',
    data(){
      return{
        saveBtnDisabled:false,
        courseInfo:{
          title: '',
          subjectId: '',
          teacherId: '',
          lessonNum: 0,
          description: '',
          cover: '/static/01.jpg',
          price: 0,
          subjectParentId:''
        },
        teacherList:[],
        subjectNestedList:[],
        subSubjectList:[],
        BASE_API:process.env.BASE_API,
        courseId:''
      }
    },
    created() {

      if (this.$route.params && this.$route.params.id){
        this.courseId = this.$route.params.id;
       this.getCourseInfoById();
      }else{
        this.getAllTeacher();
        this.getOneSubject();
      }

    },
    watch: {
      $route(to, from) { //路由发生变化，方法就会执行
        this.init()
      }
    },
    methods:{
      init() {
        if (this.$route.params && this.$route.params.id) {
          const id = this.$route.params.id
          this.getCourseInfoById()
        } else {
          // 使用对象拓展运算符，拷贝对象，而不是引用，
          // 否则新增一条记录后，defaultForm就变成了之前新增的teacher的值
          this.courseInfo = {}
        }
      },
      getCourseInfoById(){
        course.getCourseById(this.courseId).then(response =>{
          this.courseInfo = response.data.course;
          subject.getAllSubject().then( response =>{
            this.subjectNestedList = response.data.allSubject;
            for (let i = 0;i<this.subjectNestedList.length;i++){
              let oneSubject = this.subjectNestedList[i];
              if (this.courseInfo.subjectParentId == oneSubject.id){
                this.subSubjectList = oneSubject.children;
              }
            }
          })
          this.getAllTeacher();
        })
      },
      beforeAvatarUpload(file){
        const isJPG = file.type === 'image/jpeg'
        const isLt2M = file.size / 1024 / 1024 < 2
        if (!isJPG) {
          this.$message.error('上传头像图片只能是 JPG 格式!')
        }
        if (!isLt2M) {
          this.$message.error('上传头像图片大小不能超过 2MB!')
        }
        return isJPG && isLt2M

      },
      handleAvatarSuccess(res, file){
        this.courseInfo.cover = res.data.url;
      },
      subjectLevelOneChanged(value){//value就是一级分类id
        for(let i = 0;i<this.subjectNestedList.length;i++){
            const oneSubject = this.subjectNestedList[i];
            if(value === oneSubject.id){
              this.subSubjectList = oneSubject.children;
              this.courseInfo.subjectId = "";  //一级分类改变，二级分类清空
            }
        }
      },
      getOneSubject(){
        subject.getAllSubject().then( response =>{
          this.subjectNestedList = response.data.allSubject;
        })
      },
      getAllTeacher(){
        course.getAllTeacher().then( response =>{
          this.teacherList = response.data.items;
        })
      },
      SaveOrUpdate(){
        if(this.courseInfo.id){
          this.updateCourse();
          this.$router.push({path:'/course/chapter/'+this.courseId});
        }else{
          this.saveCourse();
        }

      },
      saveCourse(){
        course.addCourseInfo(this.courseInfo).then(response =>{
          this.$message({
            type:'成功',
            message:"添加成功"
          });
          this.$router.push({path:'/course/chapter/'+response.data.id});

        })
      },
      updateCourse(){
        course.updateCourse(this.courseInfo).then(response =>{
          return this.$message({
            type: 'success',
            message: '修改成功!'
          });
        })
      }

    }
  }
</script>

<style scoped>
  .tinymce-container {
    line-height: 29px;
  }
</style>
