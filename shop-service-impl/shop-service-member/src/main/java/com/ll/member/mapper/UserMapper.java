package com.ll.member.mapper;

import com.ll.entity.UserEntity;
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
	int register(UserEntity userEntity);

	@Select("SELECT * FROM member WHERE MOBILE=#{mobile};")
	UserEntity existMobile(@Param("mobile") String mobile);
}