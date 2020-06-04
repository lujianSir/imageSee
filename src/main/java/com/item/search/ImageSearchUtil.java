package com.item.search;

import java.util.HashMap;

import org.json.JSONObject;

import com.baidu.aip.imagesearch.AipImageSearch;
import com.item.tool.TrustHttp;

public class ImageSearchUtil {

	// 相似度识别接口
	public static String sample(AipImageSearch client, String image) {

		// 传入可选参数调用接口
		HashMap<String, String> options = new HashMap<String, String>();
		options.put("pn", "0");
		options.put("rn", "1");
		TrustHttp.trustEveryone();
		JSONObject res = client.similarSearch(image, options);
		System.out.println(res.toString(2));
		return res.toString(2);
	}

	public static void main(String[] args) {
		AipImageSearch client = ImageSearchObject.getClient();
		String image = "D:/image/xuexiao/a3.png";
		sample(client, image);
	}
}
