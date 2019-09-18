package com.ll.elk.aop.error;

import com.alibaba.fastjson.JSONObject;
import com.ll.elk.kafka.KafkaSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * @description: 全局捕获异常
 * @author: LL
 * @create: 2019-08-27 14:50
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @Autowired
    private KafkaSender<JSONObject> kafkaSender;

    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public JSONObject exceptionHandler(Exception e) {
        log.info("###全局捕获异常###,error:{}", e);

        JSONObject errorJson = new JSONObject();
        JSONObject logJson = new JSONObject();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        logJson.put("request_time", df.format(new Date()));
        logJson.put("error_info", e);
        errorJson.put("request_error", logJson);
        kafkaSender.send(errorJson);
        JSONObject result = new JSONObject();
        result.put("code", 500);
        result.put("msg", "系统错误");

        return result;
    }
}
