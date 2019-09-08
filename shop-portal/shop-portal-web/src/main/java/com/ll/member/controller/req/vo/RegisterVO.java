package com.ll.member.controller.req.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * @description: 注册参数
 * @author: LL
 * @create: 2019-09-08 19:32
 */
@Data
public class RegisterVO {

    /**
     * 手机号码
     */
    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = "^(((13[0-9])|(14[579])|(15([0-3]|[5-9]))|(16[6])|(17[0135678])|" +
            "(18[0-9])|(19[89]))\\d{8})$", message = "手机号格式错误")
    @Size(max = 11, min = 11, message = "手机号码长度不正确")
    private String mobile;

    /**
     * 密码
     */
    @NotNull(message = "密码不能为空")
    private String password;

    /**
     * 注册码
     */
    @NotNull(message = "注册码不能为空")
    private String registCode;

    /**
     * 图形验证码
     */
    @NotNull(message = "图形验证码不能为空")
    private String graphicCode;

}