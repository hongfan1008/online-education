<template>
  <div class="app-container">
    <h2 style="text-align: center;">发布新课程</h2>
    <el-steps :active="2" process-status="wait" align-center style="margin-bottom: 40px;">
      <el-step title="填写课程基本信息"/>
      <el-step title="创建课程大纲"/>
      <el-step title="提交审核"/>
    </el-steps>
    <el-button type="text" @click="openChapterDialog">添加章节</el-button>
    <el-dialog :visible.sync="dialogChapterFormVisible" title="添加章节">
      <el-form :model="chapter" label-width="120px">
        <el-form-item label="章节标题">
          <el-input v-model="chapter.title"/>
        </el-form-item>
        <el-form-item label="章节排序">
          <el-input-number v-model="chapter.sort" :min="0" controls-position="right"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogChapterFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveOrUpdate">确 定</el-button>
      </div>
    </el-dialog>
    <ul class="chanpterList">
      <li
        v-for="chapter in chapterVideoList"
        :key="chapter.id">
        <p>
          {{ chapter.title }}
          <span class="acts">
                <el-button type="text" @click="openVideo(chapter.id)">添加课时</el-button>
                <el-button style="" type="text" @click="EditChapterDialog(chapter.id)">编辑</el-button>
                <el-button type="text" @click="deleteChapter(chapter.id)">删除</el-button>
            </span>
        </p>
        <!-- 视频 -->
        <ul class="chanpterList videoList">
          <li
            v-for="video in chapter.videos"
            :key="video.id">
            <p>{{ video.title }}
              <span class="acts">
                        <el-button type="text" @click="EditDialogVideo(video.id)">编辑</el-button>
                        <el-button type="text" @click="deleteVideo(video.id)">删除</el-button>
              </span>
            </p>
          </li>
        </ul>
      </li>
    </ul>
    <div>
      <el-button @click="previous">上一步</el-button>
      <el-button :disabled="saveBtnDisabled" type="primary" @click="next">下一步</el-button>
    </div>
    <el-dialog :visible.sync="dialogVideoFormVisible" title="添加课时">
      <el-form :model="video" label-width="120px">
        <el-form-item label="课时标题">
          <el-input v-model="video.title"/>
        </el-form-item>
        <el-form-item label="课时排序">
          <el-input-number v-model="video.sort" :min="0" controls-position="right"/>
        </el-form-item>
        <el-form-item label="是否免费">
          <el-radio-group v-model="video.isFree">
            <el-radio :label="0">免费</el-radio>
            <el-radio :label="1">默认</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="上传视频">
          <el-upload
            :on-success="handleVodUploadSuccess"
            :on-remove="handleVodRemove"
            :before-remove="beforeVodRemove"
            :on-exceed="handleUploadExceed"
            :file-list="fileList"
            :action="BASE_API+'/eduvod/video/uploadVideo'"
            :limit="1"
            class="upload-demo">
            <el-button size="small" type="primary">上传视频</el-button>
            <el-tooltip placement="right-end">
              <div slot="content">最大支持1G，<br>
                支持3GP、ASF、AVI、DAT、DV、FLV、F4V、<br>
                GIF、M2T、M4V、MJ2、MJPEG、MKV、MOV、MP4、<br>
                MPE、MPG、MPEG、MTS、OGG、QT、RM、RMVB、<br>
                SWF、TS、VOB、WMV、WEBM 等视频格式上传</div>
              <i class="el-icon-question"/>
            </el-tooltip>
          </el-upload>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogVideoFormVisible = false">取 消</el-button>
        <el-button :disabled="saveVideoBtnDisabled" type="primary" @click="saveOrUpdateVideo">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import chapter from '@/api/edu/chapter'

  import video from '@/api/edu/video'

  export default {
    name: 'chapter',
    data(){
      return{
        saveBtnDisabled:false,
        chapterVideoList:[],
        courseId:'',
        dialogChapterFormVisible:false,
        dialogVideoFormVisible:false,
        saveVideoBtnDisabled:false,
        chapter:{
          title:'',
          sort:'',
          id:''
        },
        video: {// 课时对象
          title: '',
          sort: 0,
          isFree: 0,
          videoSourceId: '',
          courseId:'',
          chapterId:'',
          id:'',
          videoOriginalName:''//上传视频名称
        },
        fileList:[],
        BASE_API:process.env.BASE_API
      }
    },
    created() {
      if (this.$route.params && this.$route.params.id){
        this.courseId = this.$route.params.id;
      }
      this.getChapterVideo();

    },
    methods:{
      beforeVodRemove(file,fileList){
        return this.$confirm(`确定移除 ${ file.name }？`);
      },
      handleVodRemove(){
        video.deleteAlyVideo(this.video.videoSourceId).then(response =>{
          this.$message({
            type:'success',
            message:"删除成功"
          })
          this.fileList = "";
          this.video.videoSourceId = '';
          this.video.videoOriginalName = '';
        });

      },
      //上传视频成功调用的方法,file表示当前上传的文件
      handleVodUploadSuccess(response, file, fileList) {
        //上传视频id赋值
        this.video.videoSourceId = response.data.videoId
        //上传视频名称赋值
        this.video.videoOriginalName = file.name
        console.log(this.video);
      },
      //上传之前
      handleUploadExceed(){
        this.$message.warning("想要重新上传视频请先删除已上传视频");
      },
      EditDialogVideo(videoId){
        this.dialogVideoFormVisible = true;
        video.getVideoById(videoId).then(response =>{
          this.video = response.data.video;
        })
      },
      updateVideo(){
        video.updateVideo(this.video).then(response =>{
          this.dialogVideoFormVisible = false;
          this.$message({
            type:'success',
            message:"修改成功"
          });
          this.getChapterVideo();
        })

      },
      deleteVideo(videoId){
        this.$confirm('此操作将永久删除该记录, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          return video.deleteVideo(videoId)
        }).then(() => {
          this.getChapterVideo()// 刷新列表
          this.$message({
            type: 'success',
            message: '删除成功!'
          })
        }).catch((response) => { // 失败
          if (response === 'cancel') {
            this.$message({
              type: 'info',
              message: '已取消删除'
            })
          } else {
            this.$message({
              type: 'error',
              message: response.message
            })
          }
        })
      },
      //添加课时
      openVideo(chapterId){
        this.dialogVideoFormVisible  = true;
        this.video.courseId = this.courseId;
        //设置章节id到video
        this.video.chapterId = chapterId;
        this.video.title = "";
        this.video.sort = ""

      },
      addVideo(){
        console.log(this.video)
        video.addVideo(this.video).then(response =>{
          this.dialogVideoFormVisible = false;
          this.$message({
            type:'success',
            message:"添加成功"
          });
          this.getChapterVideo();
        })
      },
      saveOrUpdateVideo(){
        console.log("video:"+this.video.videoOriginalName)
        console.log("videoId:"+this.video.videoSourceId)
        if (this.video.id){
          this.updateVideo();
        } else{
          this.addVideo();
        }
      },
      deleteChapter(chapterId){
        this.$confirm('此操作将永久删除该记录, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          return chapter.deleteChapter(chapterId)
        }).then(() => {
          this.getChapterVideo()// 刷新列表
          this.$message({
            type: 'success',
            message: '删除成功!'
          })
        }).catch((response) => { // 失败
          if (response === 'cancel') {
            this.$message({
              type: 'info',
              message: '已取消删除'
            })
          } else {
            this.$message({
              type: 'error',
              message: response.message
            })
          }
        })

      },
      openChapterDialog(){
        this.dialogChapterFormVisible = true;
        this.chapter.title = '';
        this.chapter.sort = ''
      },
      saveOrUpdate(){
        this.chapter.courseId = this.courseId;
        if (this.chapter.id){
          this.updateChapter();
        }else{
          this.saveChapter();
        }
      },
      saveChapter(){
        chapter.addChapter(this.chapter).then(response=>{
          //关弹框
          this.dialogChapterFormVisible = false;
          this.$message({
            type:'success',
            message:"添加成功"
          });
          this.getChapterVideo();
        })
      },
      updateChapter(){
        chapter.updateChapter(this.chapter).then(response =>{
          this.dialogChapterFormVisible = false;
          this.$message({
            type:'success',
            message:"修改成功"
          });
          this.getChapterVideo();
        })
      },
      EditChapterDialog(chapterId){
        this.dialogChapterFormVisible = true;
        chapter.getChapterById(chapterId).then( response =>{
         this.chapter =  response.data.chapter;
        })
      },

      getChapterVideo(){
        chapter.getChapterVideoList(this.courseId).then(response =>{
          this.chapterVideoList = response.data.list;
        })
      },
      previous(){
        this.$router.push({path:'/course/info/'+this.courseId})
      },
      next(){
        this.$router.push({path:'/course/publish/'+this.courseId})

      }
    }
  }
</script>

<style scoped>
  .chanpterList{
    position: relative;
    list-style: none;
    margin: 0;
    padding: 0;
  }
  .chanpterList li{
    position: relative;
  }
  .chanpterList p{
    float: left;
    font-size: 20px;
    margin: 10px 0;
    padding: 10px;
    height: 70px;
    line-height: 50px;
    width: 100%;
    border: 1px solid #DDD;
  }
  .chanpterList .acts {
    float: right;
    font-size: 14px;
  }
  .videoList{
    padding-left: 50px;
  }
  .videoList p{
    float: left;
    font-size: 14px;
    margin: 10px 0;
    padding: 10px;
    height: 50px;
    line-height: 30px;
    width: 100%;
    border: 1px dotted #DDD;
  }
</style>
