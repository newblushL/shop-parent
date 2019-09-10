package com.ll.member.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ll.base.BaseApiService;
import com.ll.base.BaseResponse;
import com.ll.constants.Constants;
import com.ll.core.token.GenerateToken;
import com.ll.core.transaction.RedisDataSoureceTransaction;
import com.ll.core.type.TypeCastHelper;
import com.ll.core.utils.MD5Util;
import com.ll.member.input.dto.UserLoginInpDTO;
import com.ll.member.mapper.UserMapper;
import com.ll.member.mapper.UserTokenMapper;
import com.ll.member.mapper.entity.UserDO;
import com.ll.member.mapper.entity.UserTokenDO;
import com.ll.member.service.MemberLoginService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: 会员登陆接口实现类
 * @author: LL
 * @create: 2019-09-04 14:43
 */
@RestController
public class MemberLoginServiceImpl extends BaseApiService<JSONObject> implements MemberLoginService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisDataSoureceTransaction redisDataSoureceTransaction;

    @Autowired
    private UserTokenMapper userTokenMapper;

    @Autowired
    private GenerateToken generateToken;

    @Override
    public BaseResponse<JSONObject> login(@RequestBody UserLoginInpDTO userLoginInpDTO) {
        if (StringUtils.isBlank(userLoginInpDTO.getMobile())) {
            return setResultError("手机号码不能为空!");
        }
        if (StringUtils.isBlank(userLoginInpDTO.getPassword())) {
            return setResultError("密码不能为空");
        }
        if (StringUtils.isBlank(userLoginInpDTO.getLoginType())) {
            return setResultError("登陆类型不能为空!");
        }
        if (!userLoginInpDTO.getLoginType().equals(Constants.MEMBER_LOGIN_TYPE_ANDROID) &&
                !userLoginInpDTO.getLoginType().equals(Constants.MEMBER_LOGIN_TYPE_IOS) &&
                !userLoginInpDTO.getLoginType().equals(Constants.MEMBER_LOGIN_TYPE_PC)) {
            return setResultError("登陆类型错误!");
        }
        String deviceInfor = userLoginInpDTO.getDeviceInfor();
        if (StringUtils.isBlank(deviceInfor)) {
            return setResultError("设备信息不能为空!");
        }
        String newPass = MD5Util.MD5(userLoginInpDTO.getPassword());
        UserDO userDO = userMapper.login(userLoginInpDTO.getMobile(), newPass);
        if (userDO == null) {
            return setResultError("用户名或密码不正确!");
        }
        TransactionStatus transactionStatus = null;
        try {
            Long userId = userDO.getUserId();
            String loginType = userLoginInpDTO.getLoginType();
            UserTokenDO userTokenDO =
                    userTokenMapper.selectByUserIdAndLoginType(userId, loginType);
            transactionStatus = redisDataSoureceTransaction.begin();
            if (userTokenDO != null) {
                String oldToken = userTokenDO.getToken();
                generateToken.removeToken(oldToken);
                int updateTokenAvailability =
                        userTokenMapper.updateTokenAvailability(oldToken);
                if (updateTokenAvailability < 0) {
                    redisDataSoureceTransaction.rollback(transactionStatus);
                    return setResultError("系统错误!");
                }
            }
            // 如果有传递openid参数，修改到数据中
            String qqOpenId = userLoginInpDTO.getQqOpenId();
            if (!StringUtils.isBlank(qqOpenId)) {
                userMapper.updateUserOpenId(qqOpenId, userId);
            }
            // 插入新Token
            String keyPrefix = Constants.MEMBER_TOKEN_KEYPREFIX + userLoginInpDTO.getLoginType();
            String newToken = generateToken.createToken(keyPrefix,
                    TypeCastHelper.toString(userId),
                    Constants.MEMBRE_LOGIN_TOKEN_TIME);

            UserTokenDO userToken = new UserTokenDO();
            userToken.setUserId(userId);
            userToken.setDeviceInfor(deviceInfor);
            userToken.setToken(newToken);
            userToken.setLoginType(loginType);
            int result = userTokenMapper.insertUserToken(userToken);
            if (!toDaoResult(result)) {
                redisDataSoureceTransaction.rollback(transactionStatus);
                return setResultError("系统错误!");
            }
            JSONObject data = new JSONObject();
            data.put("token", newToken);
            redisDataSoureceTransaction.commit(transactionStatus);
            return setResultSuccess(data);
        } catch (Exception e) {
            try {
                redisDataSoureceTransaction.rollback(transactionStatus);
            } catch (Exception e1) {
            }
            return setResultError("系统错误!");
        }
    }


}