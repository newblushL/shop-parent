package com.ll.auth.utils;

import com.ll.core.utils.MD5Util;

import java.util.UUID;

public class Guid {
    public String appKey;

    /**
     * @return
     * @description:随机获取key值
     */
    public String guid() {
        UUID uuid = UUID.randomUUID();
        String key = uuid.toString();
        return key;
    }

    /**
     * 这是其中一个url的参数，是GUID的，全球唯一标志符
     *
     * @param
     * @return
     */
    public String getAppId() {
        Guid g = new Guid();
        String guid = g.guid();
        appKey = guid;
        return appKey;
    }

    /**
     * 根据md5加密 appid+key 实现MD5
     *
     * @param
     * @return
     */
    public String getAppSecret() {
        String mw = "key" + appKey;
        String app_sign = MD5Util.MD5(mw).toUpperCase();
        return app_sign;
    }

}