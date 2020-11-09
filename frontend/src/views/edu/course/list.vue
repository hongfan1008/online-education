<template>
  <div class="app-container">
    <!--查询表单-->
    <el-form :inline="true" class="demo-form-inline">
      <el-form-item label="课程类别">
        <el-select
          v-model="courseQuery.subjectParentId"
          placeholder="请选择" @change="subjectLevelOneChanged">
          <el-option
            v-for="subject in subjectNestedList"
            :key="subject.id"
            :label="subject.title"
            :value="subject.id"/>
        </el-select>
        <!-- 二级分类 -->
        <el-select
          v-model="courseQuery.subjectId"
          placeholder="请选择">
          <el-option
            v-for="subject in subSubjectList"
            :key="subject.id"
            :label="subject.title"
            :value="subject.id"/>
        </el-select>
      </el-form-item>
      <!-- 标题 -->
      <el-form-item>
        <el-input v-model="courseQuery.title" placeholder="课程标题"/>
      </el-form-item>
      <!-- 讲师 -->
      <el-form-item>
        <el-select
          v-model="courseQuery.teacherId"
          placeholder="请选择讲师">
          <el-option
            v-for="teacher in teacherList"
            :key="teacher.id"
            :label="teacher.name"
            :value="teacher.id"/>
        </el-select>
      </el-form-item>
      <el-button type="primary" icon="el-icon-search" @click="getList">查询</el-button>
      <el-button type="default" @click="resetData()">清空</el-button>
    </el-form>

    <!-- 表格 -->
    <el-table
      :data="list"
      border
      fit
      highlight-current-row>

      <el-table-column
        label="序号"
        width="70"
        align="center">
        <template slot-scope="scope">
          {{ (currentPage - 1) * limit + scope.$index + 1 }}
        </template>
      </el-table-column>

      <el-table-column prop="title" label="课程名称" width="80" />

      <el-table-column label="课程状态" width="80">
        <template slot-scope="scope">
          {{ scope.row.status==='Normal'?'已发布':'未发布' }}
        </template>
      </el-table-column>

      <el-table-column prop="lessonNum" label="课时数" width="80"/>

      <el-table-column prop="gmtCreate" label="添加时间" width="160"/>

      <el-table-column prop="viewCount" label="浏览数量" width="60" />

      <el-table-column label="操作" align="center">
        <template slot-scope="scope">
          <router-link :to="'/course/info/'+scope.row.id">
            <el-button type="primary" size="mini" icon="el-icon-edit">编辑课程基本信息</el-button>
          </router-link>
          <router-link :to="'/course/chapter/'+scope.row.id">
            <el-button type="primary" size="mini" icon="el-icon-edit">编辑课程大纲息</el-button>
          </router-link>
          <el-button type="danger" size="mini" icon="el-icon-delete" @click="removeDataById(scope.row.id)">删除课程信息</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页 -->
    <el-pagination :current-page="currentPage" :page-size="limit" :total="total" style="padding: 30px 0; text-align: center;"
                   layout="total, prev, pager, next, jumper" @current-change="getList"/>

  </div>
</template>
<script>
  //引入调用teacher.js文件
  import course from '@/api/edu/course'
  import subject from '@/api/edu/subject'

  export default {
    //写核心代码位置
    // data:{
    // },
    data() { //定义变量和初始值
      return {
        list:null,//查询之后接口返回集合
        currentPage:1,//当前页
        limit:5,//每页记录数
        total:null,//总记录数
        courseQuery:{
          subjectParentId:'',
          subjectId:'',
          title:'',
          teacherId:''
        } ,
        subjectNestedList:[],
        subSubjectList:[],
        teacherList:[]

      }
    },
    created() { //页面渲染之前执行，一般调用methods定义的方法
      //调用
      this.getOneSubject();
      this.getTeacherList();
      this.getList();

    },
    methods:{
      removeDataById(courseId){
        this.$confirm('此操作将永久删除该记录, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          course.deleteCourseById(courseId)
            .then(response =>{
              this.$message({
                type: 'success',
                message: '删除成功!'
              });
              this.getList();
            })
        })
      },
      getTeacherList(){
        course.getAllTeacher().then(response =>{
          this.teacherList = response.data.items;
        })
      },
      subjectLevelOneChanged(value){
        for (let i=0;i<this.subjectNestedList.length;i++){
          const  oneSubject = this.subjectNestedList[i];
          if (value === oneSubject.id){
            this.subSubjectList = oneSubject.children;
            this.courseQuery.subjectId = "";
          }

        }
      },
      //获取一级分类
      getOneSubject(){
        subject.getAllSubject().then(response =>{
          this.subjectNestedList = response.data.allSubject;
        })
      },
      getList(currentPage=1){
        this.currentPage = currentPage;
        course.pageCourseListCondition(this.currentPage,this.limit,this.courseQuery)
          .then(response =>{
            this.list = response.data.rows;
            this.total = response.data.total;
          })
          .catch(error =>{
            console.log(error);
          })
      },
      resetData() {//清空的方法
        //表单输入项数据清空
        this.courseQuery = {}
        //查询所有讲师数据
        this.getList()
      }

    }
  }
</script>

<style scoped>

</style>
