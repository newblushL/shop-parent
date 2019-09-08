package com.ll.member.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ll.base.BaseApiService;
import com.ll.base.BaseResponse;
import com.ll.core.bean.PropertyUtils;
import com.ll.core.utils.MD5Util;
import com.ll.member.input.dto.UserInpDTO;
import com.ll.member.mapper.UserMapper;
import com.ll.member.mapper.entity.UserDO;
import com.ll.member.service.MemberRegisterService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: 会员注册接口实现类
 * @author: LL
 * @create: 2019-08-30 15:37
 */
@RestController
public class MemberRegistServiceImpl extends BaseApiService<JSONObject> implements MemberRegisterService {

    @Autowired
    private UserMapper userMapper;

    @Transactional
    @Override
    public BaseResponse<JSONObject> register(@RequestBody UserInpDTO userInpDTO,
                                             String registCode) {
//        if (StringUtils.isBlank(userInpDTO.getUserName())) {
//            return setResultError("用户名不能为空!");
//        }
        if (StringUtils.isBlank(userInpDTO.getMobile())) {
            return setResultError("手机号不能为空!");
        }
        if (StringUtils.isBlank(userInpDTO.getPassword())) {
            return setResultError("密码不能为空!");
        }
        String newPass = MD5Util.MD5(userInpDTO.getPassword());
        userInpDTO.setPassword(newPass);
        UserDO userDO = PropertyUtils.dtoToDo(userInpDTO, UserDO.class);
        return userMapper.register(userDO) > 0 ? setResultSuccess("注册成功") :
                setResultError("注册失败");
    }
}