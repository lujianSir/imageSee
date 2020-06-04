package com.item.image;

import java.util.HashMap;

import org.json.JSONObject;

import com.baidu.aip.imageclassify.AipImageClassify;
import com.item.tool.TrustHttp;

public class Sample {

	// 设置APPID/AK/SK
	public static final String APP_ID = "16815493";
	public static final String API_KEY = "CDcfYWMGp0TSRF1gRnrz7537";
	public static final String SECRET_KEY = "7ykQTKcMLnsHIm9wmpY1qani0rlcs2ob";

	public static void main(String[] args) {
		// 初始化一个AipImageClassify
		AipImageClassify client = new AipImageClassify(APP_ID, API_KEY, SECRET_KEY);

		// 可选：设置网络连接参数
		client.setConnectionTimeoutInMillis(2000);
		client.setSocketTimeoutInMillis(60000);

		// 调用接口
		String path = "D:/image/2.jpg";

		TrustHttp.trustEveryone();

		JSONObject res = client.objectDetect(path, new HashMap<String, String>());
		System.out.println(res.toString(2));

	}
}
