package com.ll.member.service;

import com.alibaba.fastjson.JSONObject;
import com.ll.base.BaseResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @description: QQ授权接口
 * @author: LL
 * @create: 2019-09-09 16:29
 */
@Api(tags = "QQ授权服务接口")
public interface QQAuthoriService {

    /**
     * @Author: LL
     * @Description: 根据openid查询是否已经绑定,如果已经绑定，则直接实现自动登陆
     * @Date: 2019-09-09
     * @Param qqOpenId:
     * @return: com.ll.base.BaseResponse<com.alibaba.fastjson.JSONObject>
     **/
    @ApiOperation(value = "根据qqOpenId查询查询用户")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "qqOpenId", dataType = "String",
                    required = true, value = "qqOpenId"),})
    @RequestMapping("/findByOpenId")
    BaseResponse<JSONObject> findByOpenId(@RequestParam("qqOpenId") String qqOpenId);
}