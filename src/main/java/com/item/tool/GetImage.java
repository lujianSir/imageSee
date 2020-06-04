package com.item.tool;

import java.io.IOException;

import org.json.JSONObject;

import com.baidu.aip.imageclassify.AipImageClassify;
import com.item.image.Entity;
import com.item.image.ImageClassUtil;
import com.item.image.ImageClassifyObject;

public class GetImage {

	public static void getImage(String imagepath, String endpath) {
		AipImageClassify client = ImageClassifyObject.getClient();
		// String imagepath = "D:/image/2.jpg";
		JSONObject json = ImageClassUtil.objectDetect(client, imagepath);
		Entity entity = new Entity();
		if (json != null) {
			JSONObject result = json.getJSONObject("result");
			entity.setLeft(Integer.parseInt(result.get("left").toString()));
			entity.setTop(Integer.parseInt(result.get("top").toString()));
			entity.setHeight(Integer.parseInt(result.get("height").toString()));
			entity.setWidth(Integer.parseInt(result.get("width").toString()));
		}
		try {
			ImageCutUtil.cutImage(imagepath, endpath, entity.getLeft(), entity.getTop(), entity.getWidth(),
					entity.getHeight());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
