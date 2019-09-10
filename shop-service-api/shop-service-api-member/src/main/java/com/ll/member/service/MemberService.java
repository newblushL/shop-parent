package com.ll.member.service;

import com.ll.base.BaseResponse;
import com.ll.member.input.dto.UserLoginInpDTO;
import com.ll.member.output.dto.UserOutDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @description: 会员服务接口
 * @author: LL
 * @create: 2019-08-27 16:01
 */
@Api(tags = "会员服务接口")
public interface MemberService {

    /**
     * @Author: LL
     * @Description: 检查手机是否已注册
     * @Date: 2019-09-03
     * @Param mobile: 手机号码
     * @return: com.ll.base.BaseResponse<com.ll.entity.UserEntity>
     **/
    @ApiOperation(value = "根据手机号码查询用户")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "mobile", dataType = "String",
                    required = true, value = "用户手机号码"),})
    @PostMapping("/existMobile")
    BaseResponse<UserOutDTO> existMobile(@RequestParam("mobile") String mobile);

    /**
     * @Author: LL
     * @Description: 根据token查询用户
     * @Date: 2019-09-04
     * @Param token: 用户登陆生成的token
     * @return: com.ll.base.BaseResponse<com.ll.member.output.dto.UserOutDTO>
     **/
    @ApiOperation(value = "根据token查询用户")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "token", dataType = "String",
                    required = true, value = "用户token"),})
    @GetMapping("/getUserInfo")
    BaseResponse<UserOutDTO> getInfo(@RequestParam("token") String token);


    /**
     * @Author: LL
     * @Description: xxl-sso登陆
     * @Date: 2019-09-10
     * @Param userLoginInpDTO:
     * @return: com.ll.base.BaseResponse<com.alibaba.fastjson.JSONObject>
     **/
    @PostMapping("/xxlSsoLogin")
    @ApiOperation(value = "xxl-sso登陆")
    BaseResponse<UserOutDTO> xxlSsoLogin(@RequestBody UserLoginInpDTO userLoginInpDTO);

}