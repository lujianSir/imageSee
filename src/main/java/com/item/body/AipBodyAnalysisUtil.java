package com.item.body;

import java.util.HashMap;

import org.json.JSONObject;

import com.baidu.aip.bodyanalysis.AipBodyAnalysis;
import com.item.tool.TrustHttp;

/**
 * 人体检测与属性识别
 * 
 * @author Administrator
 *
 */
public class AipBodyAnalysisUtil {
	/**
	 * 检测图像中的所有人体并返回每个人体的矩形框位置，识别人体的静态属性和行为
	 * 
	 * @param client
	 * @return
	 */
	public static String bodyAttr(AipBodyAnalysis client, String imagePath) {
		// 传入可选参数调用接口
		HashMap<String, String> options = new HashMap<String, String>();
		options.put("type",
				"gender,age,lower_wear,upper_wear,headwear,upper_color,lower_color,glasses,cellphone,vehicle,is_human,umbrella,carrying_item");
		TrustHttp.trustEveryone();
		JSONObject res = client.bodyAttr(imagePath, options);
		System.out.println(res.toString(2));
		return res.toString(2);
	}

	public static void main(String[] args) {
		AipBodyAnalysis client = AipBodyAnalysisObject.getClient();
		String imagePath = "D:/image/1.jpg";
		bodyAttr(client, imagePath);
	}

}
