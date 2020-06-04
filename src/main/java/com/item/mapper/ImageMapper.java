package com.item.mapper;

import org.springframework.stereotype.Repository;

import com.item.entity.AccessToken;

@Repository
public interface ImageMapper {

	/**
	 * 获取最后一个token
	 * 
	 * @return
	 */
	AccessToken getLastAccessToken();

	/**
	 * 添加token
	 */
	void insertAccessToken(AccessToken accesstoken);
}
