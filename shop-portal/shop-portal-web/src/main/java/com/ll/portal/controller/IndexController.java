package com.ll.portal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @description: 首页
 * @author: LL
 * @create: 2019-09-08 19:32
 */
@Controller
public class IndexController {

    /**
     * 跳转到首页
     *
     * @return
     */
    @RequestMapping("/")
    public String index() {
        return "index";
    }

    /**
     * 跳转到首页
     *
     * @return
     */
    @RequestMapping("/index")
    public String indexHtml() {
        return index();
    }
}