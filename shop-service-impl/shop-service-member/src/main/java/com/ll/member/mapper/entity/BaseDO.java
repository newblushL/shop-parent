package com.ll.member.mapper.entity;

import lombok.Data;

import java.util.Date;
/**
 * @description: 公共实体类DO
 * @author: LL
 * @create: 2019-08-30 15:37
 */
@Data
public class BaseDO {
	/**
	 * 注册时间
	 */
	private Date createTime;
	/**
	 * 修改时间
	 *
	 */
	private Date updateTime;
	/**
	 * id
	 */
	private Long id;

	/**
	 * 是否可用 0可用 1不可用
	 */
	private Long isAvailability;
}