package com.item.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "face")
public class FaceController {

	@RequestMapping("/register")
	public String register() {
		return "register";
	}

	@RequestMapping("/image")
	public String image() {
		return "image";
	}

	@RequestMapping("/canvas")
	public String canvas() {
		return "canvas";
	}

	@RequestMapping("/camera")
	public String camera() {
		return "camera";
	}
}
