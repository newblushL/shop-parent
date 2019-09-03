package com.ll.base;

import lombok.Data;

/**
 * @description: 微服务接口统一返回码
 * @author: LL
 * @create: 2019-08-30 14:24
 */
@Data
public class BaseResponse<T> {

    private Integer code;
    private String msg;
    private T data;

    public BaseResponse() {

    }

    public BaseResponse(Integer code, String msg, T data) {
        super();
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
}
