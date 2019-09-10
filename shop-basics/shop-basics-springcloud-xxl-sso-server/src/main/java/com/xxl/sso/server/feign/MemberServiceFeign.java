package com.xxl.sso.server.feign;

import com.ll.member.service.MemberService;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @description: 会员Feign客户端
 * @author: LL
 * @create: 2019-09-10 18:47
 */
@FeignClient("app-member")
public interface MemberServiceFeign extends MemberService {
}