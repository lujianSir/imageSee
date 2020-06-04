package com.item.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.baidu.aip.imagesearch.AipImageSearch;
import com.item.entity.Height;
import com.item.entity.Similar;
import com.item.image.easydl.EasyBuilding;
import com.item.image.easydl.EasyDL;
import com.item.search.ImageSearchObject;
import com.item.search.ImageSearchUtil;
import com.item.service.ImageService;

@Controller
@RequestMapping(value = "image")
public class ImageController {

	@Autowired
	private ImageService imageService;

	@RequestMapping("/getAccessToken")
	@ResponseBody
	public String getAccessToken() {
		String accesstoken = imageService.getAccessToken();
		return accesstoken;
	}

	/**
	 * 
	 * @return
	 */
	@RequestMapping("/getFile")
	public String getFile() {
		return "file";
	}

	/**
	 * 检测柱子的根数
	 * 
	 * @return
	 */
	@RequestMapping("/getColumn")
	@ResponseBody
	public String getColumn(@RequestParam("file") MultipartFile file) {
		// String path1 = "D:/image/xuexiao/a3.png";
		String path1 = uploadFile(file);
		String accesstoken = imageService.getAccessToken();
		String total = EasyDL.easydlObjectDetection(path1, accesstoken);
		String num = imageNumber(total);
		return "已施工的柱子个数：" + num + "个";
	}

	/**
	 * 获取楼层的高度
	 * 
	 * @return
	 */
	@RequestMapping("/getBuilding")
	@ResponseBody
	public String getBuilding(@RequestParam("file") MultipartFile file) {
		// String image = "D:/image/xuexiao/3.png";
		String image = uploadFile(file);
		AipImageSearch client = ImageSearchObject.getClient();
		String str = ImageSearchUtil.sample(client, image);
		String path = imageSimilar(str);
		String accesstoken = imageService.getAccessToken();
		String building = EasyBuilding.easydlObjectDetection(path, accesstoken);
		String height = imageHeight(building);
		return "已施工层高：" + height + "层";
	}

	/**
	 * 上传文件
	 * 
	 * @param file
	 * @return
	 */
	public String uploadFile(MultipartFile file) {
		String filename = file.getOriginalFilename();
		String newrootPath = System.getProperty("user.dir") + "/upload";
		File dir = new File(newrootPath);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		String filePath = dir.getAbsolutePath() + File.separator + filename;
		// 写文件到服务器
		File serverFile = new File(filePath);
		try {
			file.transferTo(serverFile);
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return filePath;
	}

	/**
	 * 找到相似图片的地址
	 * 
	 * @return
	 */
	public String imageSimilar(String str) {
		JSONObject jsonObject = JSONObject.parseObject(str);
		String r = jsonObject.getString("result");
		List<Similar> list = JSONObject.parseArray(r, Similar.class);
		String path = list.get(0).getBrief();
		return path;
	}

	/**
	 * 获取楼层高度
	 * 
	 * @param str
	 * @return
	 */
	public String imageHeight(String str) {
		JSONObject jsonObject = JSONObject.parseObject(str);
		String r = jsonObject.getString("results");
		List<Height> list = JSONObject.parseArray(r, Height.class);
		String height = list.get(0).getName();
		return height;
	}

	/**
	 * 获取柱子的个数
	 * 
	 * @param str
	 * @return
	 */
	public String imageNumber(String str) {
		JSONObject jsonObject = JSONObject.parseObject(str);
		String r = jsonObject.getString("results");
		List<Height> list = JSONObject.parseArray(r, Height.class);
		return list.size() + "";
	}

}
