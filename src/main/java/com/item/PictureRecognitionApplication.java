package com.item;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.item.mapper")
@SpringBootApplication
public class PictureRecognitionApplication {

	public static void main(String[] args) {
		SpringApplication.run(PictureRecognitionApplication.class, args);
	}

}
