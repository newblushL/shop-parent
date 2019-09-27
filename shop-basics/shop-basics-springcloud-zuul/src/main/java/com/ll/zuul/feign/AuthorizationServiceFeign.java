package com.ll.zuul.feign;

import com.ll.service.auth.AuthorizationService;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @description: AuthFeign客户端
 * @author: LL
 * @create: 2019-09-27 16:07
 */
@FeignClient("app-auth")
public interface AuthorizationServiceFeign extends AuthorizationService {
}
