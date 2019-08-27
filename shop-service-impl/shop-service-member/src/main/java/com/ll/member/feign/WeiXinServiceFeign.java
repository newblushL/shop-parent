package com.ll.member.feign;

import com.ll.weixin.service.WeiXinService;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @description: 微信Feign客户端
 * @author: LL
 * @create: 2019-08-27 16:18
 */
@FeignClient("app-weixin")
public interface WeiXinServiceFeign extends WeiXinService {

}