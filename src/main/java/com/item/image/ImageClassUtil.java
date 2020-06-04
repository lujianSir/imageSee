package com.item.image;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import com.baidu.aip.imageclassify.AipImageClassify;
import com.item.tool.TrustHttp;

/**
 * 图像识别 分析图像内容
 * 
 * @author Administrator
 *
 */
public class ImageClassUtil {

	// 通用物体识别接口
	public static String sample(AipImageClassify client, String imagepath) {

		// 传入可选参数调用接口
		HashMap<String, String> options = new HashMap<String, String>();
		options.put("baike_num", "5");

		JSONObject res = client.advancedGeneral(imagepath, options);
		System.out.println(res.toString(2));
		return res.toString(2);
	}

	/**
	 * logo识别
	 * 
	 * @param client
	 * @param imagepath
	 * @return
	 */
	public static String logoSearch(AipImageClassify client, String imagepath) {
		HashMap<String, String> options = new HashMap<String, String>();
		options.put("custom_lib", "false");

		JSONObject res = client.logoSearch(imagepath, options);
		return res.toString(2);

	}

	/**
	 * 动物分析
	 * 
	 * @param client
	 * @param imagepath
	 * @return
	 */
	public static String animalDetect(AipImageClassify client, String imagepath) {
		HashMap<String, String> options = new HashMap<String, String>();
		/*
		 * options.put("baike_num", "5"); options.put("top_num", "1");
		 */
		JSONObject res = client.animalDetect(imagepath, options);
		return res.toString(2);

	}

	/**
	 * 获取图像主体
	 * 
	 * @param client
	 * @param imagepath
	 * @return
	 */
	public static JSONObject objectDetect(AipImageClassify client, String imagepath) {
		HashMap<String, String> options = new HashMap<String, String>();
		TrustHttp.trustEveryone();
		JSONObject res = client.objectDetect(imagepath, options);
		return res;

	}

	public static void main(String[] args) {
		AipImageClassify client = ImageClassifyObject.getClient();
		String imagepath = "D:/image/2.jpg";
		System.out.println(objectDetect(client, imagepath));
		JSONObject json = objectDetect(client, imagepath);
		Map map = new HashMap();
		JSONObject result = json.getJSONObject("result");
		System.out.println(result.get("top"));
//		JSONObject words_result = res.getJSONObject("words_result");
//	    idCard.put("name", words_result.getJSONObject("姓名").get("words"));
	}
}
