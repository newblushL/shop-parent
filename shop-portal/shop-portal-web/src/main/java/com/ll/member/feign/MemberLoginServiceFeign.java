package com.ll.member.feign;

import com.ll.member.service.MemberLoginService;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @description: 用户登陆Feign客户端
 * @author: LL
 * @create: 2019-09-08 23:04
 */
@FeignClient("app-member")
public interface MemberLoginServiceFeign extends MemberLoginService {
}