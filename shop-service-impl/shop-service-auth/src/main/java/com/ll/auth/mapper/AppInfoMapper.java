package com.ll.auth.mapper;

import com.ll.auth.mapper.entity.AppInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface AppInfoMapper {

    @Insert("INSERT INTO `app_info` VALUES (null,#{appName}, #{appId}, #{appSecret}, '0', null, null, null, null, null);")
    int insertAppInfo(AppInfo AppInfo);

    @Select("SELECT ID AS ID ,app_id as appId, app_name AS appName ,app_secret as appSecret  FROM app_info where app_id=#{appId} and app_secret=#{appSecret}; ")
    AppInfo selectByAppInfo(@Param("appId") String appId, @Param("appSecret") String appSecret);

    @Select("SELECT ID AS ID ,app_id as appId, app_name AS appName ,app_secret as appSecret  FROM app_info where app_id=#{appId}  ")
    AppInfo findByAppInfo(@Param("appId") String appId);
}
