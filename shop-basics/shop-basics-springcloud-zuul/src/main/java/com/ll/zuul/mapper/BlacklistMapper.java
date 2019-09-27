package com.ll.zuul.mapper;

import com.ll.zuul.mapper.entity.Blacklist;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface BlacklistMapper {

	@Select(" select ID AS ID ,ip_addres AS ipAddres,restriction_type  as restrictionType, availability  as availability from blacklist where  ip_addres =#{ipAddres} and  restriction_type='1' ")
	Blacklist findBlacklist(String ipAddres);

}
