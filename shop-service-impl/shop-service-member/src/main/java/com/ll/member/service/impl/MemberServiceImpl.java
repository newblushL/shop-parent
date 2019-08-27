package com.ll.member.service.impl;

import com.ll.member.feign.WeiXinServiceFeign;
import com.ll.member.service.MemberService;
import ll.entity.AppEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: 会员接口实现
 * @author: LL
 * @create: 2019-08-27 16:12
 */
@RestController
public class MemberServiceImpl implements MemberService {

    @Autowired
    private WeiXinServiceFeign weiXinServiceFeign;
    
    @Override
    public AppEntity memberToWeiXin() {
        return weiXinServiceFeign.getApp();
    }
}