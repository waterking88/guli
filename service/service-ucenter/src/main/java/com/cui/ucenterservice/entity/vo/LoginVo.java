package com.cui.ucenterservice.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 封装登录数据
 *
 * @author water
 * @date 2024/4/8
 * @Description
 */
@Data
@ApiModel(value = "登录对象", description = "登录对象")
public class LoginVo {
    @ApiModelProperty(value = "手机号")
    private String mobile;
    @ApiModelProperty(value = "密码")
    private String password;
}
