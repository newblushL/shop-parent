package com.ll.member.feign;

import com.ll.member.service.MemberRegisterService;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @description: 会员注册Feign客户端
 * @author: LL
 * @create: 2019-09-08 20:19
 */
@FeignClient("app-member")
public interface MemberRegisterServiceFeign extends MemberRegisterService {
}