package com.atguigu.vodtest;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadVideoRequest;
import com.aliyun.vod.upload.resp.UploadVideoResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.*;

import java.util.List;

public class TestVod {
    public static void main(String[] args) {
        /**
         *  上传视频到阿里云vod
         */
        String accessKeyId = "LTAI4GGoMF4DPjcsFQYvynQb";
        String accessKeySecret = "ue7jer4cHOGb0AWVNpacoxcV8BKbT9";
        //getVideoInfo();
        //getVideoUrl();
        String title = "3 - How Does Project Submission Work - upload by sdk";
        //本地文件上传和文件流上传时，文件名称为上传文件绝对路径，如:/User/sample/文件名称.mp4 (必选)
        //文件名必须包含扩展名
        String fileName = "C:\\Users\\Guang\\Desktop\\在线教育\\项目资料\\1-阿里云上传测试视频\\6 - What If I Want to Move Faster.mp4";

        //上传视频到阿里云vod
        UploadVideoRequest request = new UploadVideoRequest(accessKeyId, accessKeySecret, title, fileName);
        request.setPartSize(2*1024*1024L);
        request.setTaskNum(1);
       // 是否开启断点续传, 默认断点续传功能关闭。当网络不稳定或者程序崩溃时，再次发起相同上传请求，可以继续未完成的上传任务，适用于超时3000秒仍不能上传完成的大文件。
        //注意: 断点续传开启后，会在上传过程中将上传位置写入本地磁盘文件，影响文件上传速度，请您根据实际情况选择是否开启
//        request.setEnableCheckpoint(false);
        UploadVideoImpl uploader = new UploadVideoImpl();
        UploadVideoResponse response = uploader.uploadVideo(request);
        System.out.print("RequestId=" + response.getRequestId() + "\n");  //请求视频点播服务的请求ID
        if (response.isSuccess()) {
            System.out.print("VideoId=" + response.getVideoId() + "\n");
        } else {
         //如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
            System.out.print("VideoId=" + response.getVideoId() + "\n");
            System.out.print("ErrorCode=" + response.getCode() + "\n");
            System.out.print("ErrorMessage=" + response.getMessage() + "\n");
        }


    }
    //只可以不加密视频
  public static  void getVideoInfo() throws ClientException {
        //根据视频id获取视频的内容

        //创建初始化对象
        DefaultAcsClient defaultAcsClient = InitObject.initVodClient("LTAI4GGoMF4DPjcsFQYvynQb", "ue7jer4cHOGb0AWVNpacoxcV8BKbT9");
        //获取视频地址request和response
        GetPlayInfoRequest getPlayInfoRequest = new GetPlayInfoRequest();
        GetPlayInfoResponse getPlayInfoResponse = new GetPlayInfoResponse();
        //向request中设置id
        getPlayInfoRequest.setVideoId("8bc3a824076d497dad6e671c8aaef911");
        //调用初始化中的方法，传递request，获取数据。
        getPlayInfoResponse = defaultAcsClient.getAcsResponse(getPlayInfoRequest);
        List<GetPlayInfoResponse.PlayInfo> playInfoList = getPlayInfoResponse.getPlayInfoList();
        //播放地址
        for (GetPlayInfoResponse.PlayInfo playInfo : playInfoList) {
            System.out.print("PlayInfo.PlayURL = " + playInfo.getPlayURL() + "\n");
        }
        //Base信息
        System.out.print("VideoBase.Title = " + getPlayInfoResponse.getVideoBase().getTitle() + "\n");
    }
    //根据获取视频播放凭证,视频加密，需要凭证进行播放。
    public static void getVideoUrl() throws ClientException {
        DefaultAcsClient defaultAcsClient = InitObject.initVodClient("LTAI4GGoMF4DPjcsFQYvynQb", "ue7jer4cHOGb0AWVNpacoxcV8BKbT9");
        //创建视频凭证的request和response对象
        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        GetVideoPlayAuthResponse response = new GetVideoPlayAuthResponse();
        //向request中设置id
        request.setVideoId("8bc3a824076d497dad6e671c8aaef911");
        //调用初始化对象的方法得到凭证
        response = defaultAcsClient.getAcsResponse(request);
        System.out.println(response.getPlayAuth());

    }
}
