package com.example.upload_image_api.service;

import com.example.upload_image_api.dto.GetImageDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileService {
    void init();

    void save(MultipartFile file);

    List<GetImageDto> getImages();

    List<GetImageDto> getImageByName(String name);

    GetImageDto getImageById(long id);
}
