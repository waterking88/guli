package com.cui.vodtest;

import com.aliyun.oss.ClientException;
import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadVideoRequest;
import com.aliyun.vod.upload.resp.UploadVideoResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoRequest;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.cui.vod.utils.ConstantPropertiesUtil;
import org.junit.Test;

import java.util.List;

/**
 * @author water
 * @date 2024/4/4
 * @Description
 */
public class testVod {

    String accessKeyId = ConstantPropertiesUtil.ACCESS_KEY_ID;
    String accessKeySecret = ConstantPropertiesUtil.ACCESS_KEY_SECRET;


    /**
     * 获取视频播放凭证
     *
     * @throws ClientException
     */
    @Test
    public void testGetVideoPlayAuth() throws ClientException {
        //初始化客户端、请求对象和相应对象
        DefaultAcsClient client = initObject.initVodClient(accessKeyId,
                accessKeySecret);
        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        GetVideoPlayAuthResponse response = new GetVideoPlayAuthResponse();
        try {
            //设置请求参数
            //只能获取非加密的视频
            request.setVideoId("b05f4213f0c371ee95f26733a68f0102");
            //获取请求响应
            response = client.getAcsResponse(request);
            //输出请求结果
            //播放凭证
            System.out.print("PlayAuth = " + response.getPlayAuth() + "\n");
            //VideoMeta信息
            System.out.print("VideoMeta.Title = " + response.getVideoMeta().getTitle()
                    + "\n");
        } catch (Exception e) {
            System.out.print("ErrorMessage = " + e.getLocalizedMessage());
        }
        System.out.print("RequestId = " + response.getRequestId() + "\n");
    }

    /**
     * 获取视频播放地址
     *
     * @throws ClientException
     */
    @Test
    public void testGetPlayInfo() throws ClientException {
        //初始化客户端、请求对象和相应对象
        DefaultAcsClient client = initObject.initVodClient(accessKeyId,
                accessKeySecret);
        GetPlayInfoRequest request = new GetPlayInfoRequest();
        GetPlayInfoResponse response = new GetPlayInfoResponse();
        try {
            //设置请求参数
            //注意：这里只能获取非加密视频的播放地址
            request.setVideoId("b05f4213f0c371ee95f26733a68f0102");
            //获取请求响应
            response = client.getAcsResponse(request);
            //输出请求结果
            List<GetPlayInfoResponse.PlayInfo> playInfoList =
                    response.getPlayInfoList();
            //播放地址
            for (GetPlayInfoResponse.PlayInfo playInfo : playInfoList) {
                System.out.print("PlayInfo.PlayURL = " + playInfo.getPlayURL() +
                        "\n");
            }
            //Base信息
            System.out.print("VideoBase.Title = " + response.getVideoBase().getTitle()
                    + "\n");
        } catch (Exception e) {
            System.out.print("ErrorMessage = " + e.getLocalizedMessage());
        }
        System.out.print("RequestId = " + response.getRequestId() + "\n");
    }

    /**
     * 视频上传
     */
    @Test
    public void testUploadVideo() {
        //1.音视频上传-本地文件上传
        //视频标题(必选)
        String title = "3 - How Does Project Submission Work - upload by sdk";
        //本地文件上传和文件流上传时，文件名称为上传文件绝对路径，如:/User/sample/文件名称.mp4 (必选)
        //文件名必须包含扩展名
        String fileName = "C:\\Users\\waterking\\Videos\\Captures\\League of Legends (TM) Client 2024-02-26 23-09-23.mp4";
        //本地文件上传
        UploadVideoRequest request = new UploadVideoRequest(accessKeyId,
                accessKeySecret, title, fileName);
        /* 可指定分片上传时每个分片的大小，默认为1M字节 */
        request.setPartSize(1 * 1024 * 1024L);
        /* 可指定分片上传时的并发线程数，默认为1，(注：该配置会占用服务器CPU资源，需根据服务
        器情况指定）*/
        request.setTaskNum(1);
        /* 是否开启断点续传, 默认断点续传功能关闭。当网络不稳定或者程序崩溃时，再次发起相同上
        传请求，可以继续未完成的上传任务，适用于超时3000秒仍不能上传完成的大文件。
        注意: 断点续传开启后，会在上传过程中将上传位置写入本地磁盘文件，影响文件上传速
        度，请您根据实际情况选择是否开启*/
        request.setEnableCheckpoint(false);
        UploadVideoImpl uploader = new UploadVideoImpl();
        UploadVideoResponse response = uploader.uploadVideo(request);
        //请求视频点播服务的请求ID
        System.out.print("RequestId=" + response.getRequestId() + "\n");
        if (response.isSuccess()) {
            System.out.print("VideoId=" + response.getVideoId() + "\n");
        } else {
        /* 如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情
        况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因 */
            System.out.print("VideoId=" + response.getVideoId() + "\n");
            System.out.print("ErrorCode=" + response.getCode() + "\n");
            System.out.print("ErrorMessage=" + response.getMessage() + "\n");
        }
    }
}
