package com.ll.member.service.impl;

import com.ll.base.BaseApiService;
import com.ll.base.BaseResponse;
import com.ll.constants.Constants;
import com.ll.core.bean.PropertyUtils;
import com.ll.core.token.GenerateToken;
import com.ll.core.type.TypeCastHelper;
import com.ll.core.utils.MD5Util;
import com.ll.member.feign.WeiXinServiceFeign;
import com.ll.member.input.dto.UserLoginInpDTO;
import com.ll.member.mapper.UserMapper;
import com.ll.member.mapper.entity.UserDO;
import com.ll.member.output.dto.UserOutDTO;
import com.ll.member.service.MemberService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: 会员接口实现类
 * @author: LL
 * @create: 2019-08-27 16:12
 */
@RestController
public class MemberServiceImpl extends BaseApiService<UserOutDTO> implements MemberService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private GenerateToken generateToken;

    @Autowired
    private WeiXinServiceFeign weiXinServiceFeign;

    @Override
    public BaseResponse<UserOutDTO> existMobile(String mobile) {
        if (StringUtils.isBlank(mobile)) {
            return setResultError("用户手机不能为空！");
        }
        UserDO userDO = userMapper.existMobile(mobile);
        if (userDO == null) {
            return setResultError(Constants.HTTP_RES_CODE_MEMBER_NOT_EXIST_203, "用户不存在!");
        }
        UserOutDTO userOutDTO = PropertyUtils.doToDto(userDO, UserOutDTO.class);
        return setResultSuccess(userOutDTO);
    }

    @Override
    public BaseResponse<UserOutDTO> getInfo(String token) {
        if (StringUtils.isBlank(token)) {
            return setResultError("token不能为空!");
        }
        String redisUserId = generateToken.getToken(token);
        if (StringUtils.isBlank(redisUserId)) {
            return setResultError("token已过期或不正确!");
        }
        UserDO userDO = userMapper.findByUserId(TypeCastHelper.toLong(redisUserId));
        if (userDO == null) {
            return setResultError("用户信息不存在!");
        }
        return setResultSuccess(PropertyUtils.doToDto(userDO, UserOutDTO.class));
    }

    @Override
    public BaseResponse<UserOutDTO> xxlSsoLogin(@RequestBody UserLoginInpDTO userLoginInpDTO) {
        // 1.验证参数
        String mobile = userLoginInpDTO.getMobile();
        if (StringUtils.isEmpty(mobile)) {
            return setResultError("手机号码不能为空!");
        }
        String password = userLoginInpDTO.getPassword();
        if (StringUtils.isEmpty(password)) {
            return setResultError("密码不能为空!");
        }
        // 判断登陆类型
        String loginType = userLoginInpDTO.getLoginType();
        if (StringUtils.isEmpty(loginType)) {
            return setResultError("登陆类型不能为空!");
        }
        // 目的是限制范围
        if (!(loginType.equals(Constants.MEMBER_LOGIN_TYPE_ANDROID) || loginType.equals(Constants.MEMBER_LOGIN_TYPE_IOS)
                || loginType.equals(Constants.MEMBER_LOGIN_TYPE_PC))) {
            return setResultError("登陆类型出现错误!");
        }

        // 设备信息
        String deviceInfor = userLoginInpDTO.getDeviceInfor();
        if (StringUtils.isEmpty(deviceInfor)) {
            return setResultError("设备信息不能为空!");
        }
        // 2.对登陆密码实现加密
        String newPassWord = MD5Util.MD5(password);
        // 3.使用手机号码+密码查询数据库 ，判断用户是否存在
        UserDO userDO = userMapper.login(mobile, newPassWord);
        if (userDO == null) {
            return setResultError("用户名称或者密码错误!");
        }
        return setResultSuccess(PropertyUtils.doToDto(userDO, UserOutDTO.class));
    }
}