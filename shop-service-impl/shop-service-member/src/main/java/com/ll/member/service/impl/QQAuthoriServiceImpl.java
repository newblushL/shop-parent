package com.ll.member.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ll.base.BaseApiService;
import com.ll.base.BaseResponse;
import com.ll.constants.Constants;
import com.ll.core.token.GenerateToken;
import com.ll.member.mapper.UserMapper;
import com.ll.member.mapper.entity.UserDO;
import com.ll.member.service.QQAuthoriService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @description: QQ授权接口实现类
 * @author: LL
 * @create: 2019-09-09 16:36
 */
public class QQAuthoriServiceImpl extends BaseApiService<JSONObject> implements QQAuthoriService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private GenerateToken generateToken;

    @Override
    public BaseResponse<JSONObject> findByOpenId(String qqOpenId) {
        // 1.根据qqOpenId查询用户信息
        if (StringUtils.isEmpty(qqOpenId)) {
            return setResultError("qqOpenId不能为空!");
        }
        // 2.如果没有查询到 直接返回状态码203
        UserDO userDO = userMapper.findByOpenId(qqOpenId);
        if (userDO == null) {
            return setResultError(Constants.HTTP_RES_CODE_NOTUSER_203,
                    "根据qqOpenId没有查询到用户信息");
        }
        // 3.如果能够查询到用户信息的话 返回对应用户信息token
        String keyPrefix = Constants.MEMBER_TOKEN_KEYPREFIX + Constants.MEMBER_LOGIN_TYPE_QQ;
        Long userId = userDO.getUserId();
        String userToken = generateToken.createToken(keyPrefix, userId + "");
        JSONObject data = new JSONObject();
        data.put("token", userToken);
        return setResultSuccess(data);
    }
}