package com.atguigu.eduservice.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CourseQuery {
    @ApiModelProperty(value = "课程专业ID")
    private String subjectId;

    @ApiModelProperty(value = "课程专业父级ID")
    private String subjectParentId;
    @ApiModelProperty(value = "课程标题")
    private String title;
    @ApiModelProperty(value = "课程讲师ID")
    private String teacherId;
}
