package com.ll.member.feign;

import com.ll.member.service.QQAuthoriService;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @description: QQ授权Feign客户端
 * @author: LL
 * @create: 2019-09-09 16:45
 */
@FeignClient("app-member")
public interface QQAuthoriServiceFeign extends QQAuthoriService {
}