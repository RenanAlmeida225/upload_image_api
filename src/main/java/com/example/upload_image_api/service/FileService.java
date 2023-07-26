package com.example.upload_image_api.service;

import com.example.upload_image_api.dto.ImageDto;
import com.example.upload_image_api.dto.ImageSaveDto;

import java.util.List;

public interface FileService {
    void save(ImageSaveDto image);

    List<ImageDto> getImages();

    List<ImageDto> getImageByTitle(String title);

    ImageDto getImageById(long id);

    void updateImage(ImageDto image);

    void deleteImage(long id);
}
