package com.ll.twitter;

/**
 * @Author: LL
 * @Description: 使用雪花算法生成全局id
 * @Date: 2019-09-19
 **/
public class SnowflakeIdUtils {
    private static SnowflakeIdWorker idWorker;

    static {
        // 使用静态代码块初始化 SnowflakeIdWorker
        idWorker = new SnowflakeIdWorker(1, 1);
    }

    public static String nextId() {
        return idWorker.nextId() + "";
    }

}
