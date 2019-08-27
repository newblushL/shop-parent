package com.ll.member.service;

import ll.entity.AppEntity;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @description: 会员服务接口
 * @author: LL
 * @create: 2019-08-27 16:01
 */
public interface MemberService {

    /**
     * @Author: LL
     * @Description: 会员调用微信
     * @Date: 2019-08-27
     * @return: ll.entity.AppEntity
     **/
    @GetMapping("/memberToWeiXin")
    AppEntity memberToWeiXin();
}