package com.cui.eduservice.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 用于构建ChapterVo的链表
 *
 * @author water
 * @date 2024/3/31
 * @Description
 */
@ApiModel(value = "课时信息")
@Data
public class VideoVo implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private String title;
    private Boolean free;
    private String videoSourceId;
    @ApiModelProperty(value = "云服务器上存储的视频文件名称")
    private String videoOriginalName;
}