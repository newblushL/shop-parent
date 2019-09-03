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
}