package com.example.upload_image_api;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.upload_image_api.service.FileService;

import jakarta.annotation.Resource;

@SpringBootApplication
public class UploadImageApiApplication implements CommandLineRunner {

	@Resource
	FileService service;

	public static void main(String[] args) {
		SpringApplication.run(UploadImageApiApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		service.init();
	}

}
