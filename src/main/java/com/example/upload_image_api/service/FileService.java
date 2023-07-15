package com.example.upload_image_api.service;

import com.example.upload_image_api.entity.File;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileService {
    void init();

    void save(MultipartFile file);

    List<File> getImages();

    List<File> getImageByName(String name);
}
