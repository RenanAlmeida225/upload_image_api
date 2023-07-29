package com.example.upload_image_api.service.impl;

import com.example.upload_image_api.dto.ImageDto;
import com.example.upload_image_api.dto.ImageSaveDto;
import com.example.upload_image_api.entity.File;
import com.example.upload_image_api.exception.EntityNotFoundException;
import com.example.upload_image_api.repository.FileRepository;
import com.example.upload_image_api.service.FileService;
import com.example.upload_image_api.util.UploadFile;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FileServiceImpl implements FileService {
    @Autowired
    private FileRepository repository;
    @Autowired
    private UploadFile uploadFile;

    @Override
    @Transactional
    public void save(ImageSaveDto image) {
        try {
            String name = LocalDateTime.now() + image.file().getOriginalFilename();
            String path = uploadFile.save(image.file().getInputStream(), name, image.file().getContentType());
            File file = new File(image.title(), image.description(), name, image.file().getOriginalFilename(),
                    image.file().getContentType(), path);
            repository.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    @Transactional
    public List<ImageDto> getImages() {
        return convertToGetImageDto(repository.findAll());
    }

    @Override
    @Transactional
    public List<ImageDto> searchImages(String information) {
        return convertToGetImageDto(repository.findByTitleOrDescription(information));
    }

    @Override
    @Transactional
    public ImageDto getImageById(long id) {
        return repository.findById(id)
                .map(image -> new ImageDto(image.getId(), image.getTitle(), image.getDescription(), image.getUrl()))
                .orElseThrow(() -> new EntityNotFoundException("Image not found"));
    }

    @Override
    @Transactional
    public void updateImage(ImageDto image) {
        repository.findById(image.id()).map(file -> {
            file.setTitle(image.title());
            file.setDescription(image.description());
            repository.save(file);
            return file;
        }).orElseThrow(() -> new EntityNotFoundException("Image not found"));
    }

    @Override
    @Transactional
    public void deleteImage(long id) {
        repository.findById(id).map((image) -> {
            uploadFile.delete(image.getName());
            repository.delete(image);
            return image;
        }).orElseThrow(() -> new EntityNotFoundException("Image not found"));
    }

    private List<ImageDto> convertToGetImageDto(List<File> list) {
        return list.stream()
                .map(image -> new ImageDto(image.getId(), image.getTitle(), image.getDescription(), image.getUrl()))
                .collect(Collectors.toList());
    }
}
