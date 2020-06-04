package com.item.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.item.entity.AccessToken;
import com.item.image.easydl.EasyDL;
import com.item.mapper.ImageMapper;
import com.item.tool.DateUtil;

@Service
public class ImageServiceImpl implements ImageService {

	@Autowired
	private ImageMapper imageMapper;

	@Override
	public String getAccessToken() {
		// TODO Auto-generated method stub
		AccessToken accessToken = imageMapper.getLastAccessToken();
		if (accessToken != null) {
			long curMillis = DateUtil.getCurrentMillis();
			long myCustomMillis = DateUtil.getCustomDateMillis(accessToken.getCreate_time());
			if ((curMillis - myCustomMillis) / 1000 >= 2592000) {
				System.out.println("access_token值已过期，重新获取并入库......");
				accessToken = EasyDL.getAuth();
				imageMapper.insertAccessToken(accessToken);
			}
		} else {
			accessToken = EasyDL.getAuth();
			imageMapper.insertAccessToken(accessToken);
		}
		return accessToken.getAccess_token();
	}

}
