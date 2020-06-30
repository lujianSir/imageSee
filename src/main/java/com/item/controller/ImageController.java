package com.item.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
		String total = EasyDL.easydlZhuZiDetection(path1, accesstoken);
		String num = imageNumber(total);
		return num;
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
		String building = EasyDL.easydlHeightDetection(path, accesstoken);
		String height = imageHeight(building);
		return "已施工层高：" + height + "层";
	}

	/**
	 * 墩面的个数
	 * 
	 * @param file
	 * @return
	 */
	@RequestMapping("/getPierMumber")
	@ResponseBody
	public Map<String, Integer> getPierMumber(@RequestParam("file") MultipartFile file) {
		// String path1 = "D:/image/xuexiao/a3.png";
		String path = uploadFile(file);
		String accesstoken = imageService.getAccessToken();
		String total = EasyDL.easydDunMianDetection(path, accesstoken);
		Map<String, Integer> map = imagePier(total);
		return map;
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

	/**
	 * 获取墩面个数
	 * 
	 * @param str
	 * @return
	 */
	public Map<String, Integer> imagePier(String str) {
		JSONObject jsonObject = JSONObject.parseObject(str);
		String r = jsonObject.getString("results");
		List<Height> list = JSONObject.parseArray(r, Height.class);
		List<Height> zhuzis = new ArrayList<Height>();
		List<Height> dunmians = new ArrayList<Height>();
		for (Height height : list) {
			if (height.getName().equals("zhuzi")) {
				zhuzis.add(height);
			} else if (height.getName().equals("louban")) {
				dunmians.add(height);
			}
		}
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("zhuzi", zhuzis.size());
		map.put("loumian", dunmians.size());
		return map;
	}

}
