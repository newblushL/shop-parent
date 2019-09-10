package com.ll.pay.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @description: 支付请求
 * @author: LL
 * @create: 2019-09-10 21:41
 */
@Controller
public class PayController {

    @RequestMapping("/")
    public String index(){
        return "index";
    }
}