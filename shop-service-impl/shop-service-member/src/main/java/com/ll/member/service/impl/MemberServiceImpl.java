package com.ll.member.service.impl;

import com.ll.base.BaseApiService;
import com.ll.base.BaseResponse;
import com.ll.constants.Constants;
import com.ll.entity.AppEntity;
import com.ll.entity.UserEntity;
import com.ll.member.feign.WeiXinServiceFeign;
import com.ll.member.mapper.UserMapper;
import com.ll.member.service.MemberService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: 会员接口实现
 * @author: LL
 * @create: 2019-08-27 16:12
 */
@RestController
public class MemberServiceImpl extends BaseApiService<UserEntity> implements MemberService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private WeiXinServiceFeign weiXinServiceFeign;


    @Override
    public BaseResponse<AppEntity> memberToWeiXin() {
        return weiXinServiceFeign.getApp();
    }

    @Override
    public BaseResponse<UserEntity> existMobile(String mobile) {
        if(StringUtils.isBlank(mobile)){
            return setResultError("用户手机不能为空！");
        }
        UserEntity userEntity = userMapper.existMobile(mobile);
        if(userEntity == null){
            return setResultError(Constants.HTTP_RES_CODE_MOBILE_EXIST_203,"用户不存在!");
        }
        // 对特殊字段脱敏
        userEntity.setPassword(null);
        return setResultSuccess(userEntity);
    }
}