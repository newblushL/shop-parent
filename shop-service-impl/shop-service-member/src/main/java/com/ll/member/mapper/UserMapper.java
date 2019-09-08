package com.ll.member.mapper;

import com.ll.member.mapper.entity.UserDO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @description: 用户Mapper
 * @author: LL
 * @create: 2019-08-30 15:37
 */
@Repository
public interface UserMapper {

    @Insert("INSERT INTO `member` VALUES (null,#{mobile}, #{email}, #{password}, " +
            "#{userName}, null, null, null, '1', null, null, null);")
    int register(UserDO userDo);

    @Select("SELECT * FROM member WHERE MOBILE=#{mobile};")
    UserDO existMobile(@Param("mobile") String mobile);

    @Select("SELECT USER_ID AS USERID FROM member WHERE mobile=#{mobile} and " +
            "password=#{password};")
    UserDO login(@Param("mobile") String mobile, @Param("password") String password);

    @Select("SELECT USER_ID AS USERID ,MOBILE AS MOBILE,EMAIL AS EMAIL,PASSWORD AS PASSWORD, USER_NAME AS USER_NAME ,SEX AS SEX ,AGE AS AGE ,CREATE_TIME AS CREATETIME,IS_AVALIBLE AS ISAVALIBLE,PIC_IMG AS PICIMG,QQ_OPENID AS QQOPENID,WX_OPENID AS WXOPENID"
            + " FROM member WHERE user_Id=#{userId}")
    UserDO findByUserId(@Param("userId") Long userId);
}