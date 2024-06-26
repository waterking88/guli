package com.cui.eduservice.entity.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author water
 * @date 2024/3/28
 * @Description
 */
@Data
public class SubjectNestedVo {
    private String id;
    private String title;
    private List<SubjectVo> children = new ArrayList<>();
}