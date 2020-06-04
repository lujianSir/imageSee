package com.item.search;

import com.baidu.aip.imagesearch.AipImageSearch;

public class ImageSearchObject {

	// 设置APPID/AK/SK
	public static final String APP_ID = "19777547";
	public static final String API_KEY = "ZXzEnsfiPzejDGxHvMGF6Gwv";
	public static final String SECRET_KEY = "VEDz42cvpPZrLhtuttwyST5W6qjjNXyX";

	private static AipImageSearch client;

	public static AipImageSearch getClient() {
		if (client == null) {
			client = new AipImageSearch(APP_ID, API_KEY, SECRET_KEY);
			client.setConnectionTimeoutInMillis(2000);
			client.setSocketTimeoutInMillis(60000);
		}
		return client;
	}
}
