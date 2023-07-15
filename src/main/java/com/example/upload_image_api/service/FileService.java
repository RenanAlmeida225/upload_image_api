package com.example.upload_image_api.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    public void init();

    public void save(MultipartFile file);
}
