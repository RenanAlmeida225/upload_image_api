package com.example.upload_image_api.service;

import com.example.upload_image_api.dto.GetImageDto;
import com.example.upload_image_api.dto.UpdateImageDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileService {
    void save(MultipartFile file, String title, String description);

    List<GetImageDto> getImages();

    List<GetImageDto> getImageByTitle(String title);

    GetImageDto getImageById(long id);

    void updateImage(long id, UpdateImageDto dto);
}
