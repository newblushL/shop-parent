package com.ll.web.bean;

import org.springframework.beans.BeanUtils;

/**
 * @description: VO/DTO转换工具类
 * @author: LL
 * @create: 2019-09-03 23:18
 */
public class PropertyUtils {
    /**
     * dot 转换为Do 工具类
     *
     * @param voEntity
     * @param dtoClass
     * @return
     */
    public static <Dto> Dto voToDto(Object voEntity, Class<Dto> dtoClass) {
        // 判断VoSF是否为空!
        if (voEntity == null) {
            return null;
        }
        // 判断DtoClass 是否为空
        if (dtoClass == null) {
            return null;
        }
        try {
            Dto newInstance = dtoClass.newInstance();
            BeanUtils.copyProperties(voEntity, newInstance);
            // Dto转换Do
            return newInstance;
        } catch (Exception e) {
            return null;
        }
    }

    // 后面集合类型带封装
}