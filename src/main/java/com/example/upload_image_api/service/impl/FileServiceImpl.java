package com.example.upload_image_api.service.impl;

import com.example.upload_image_api.dto.GetImageDto;
import com.example.upload_image_api.dto.UpdateImageDto;
import com.example.upload_image_api.entity.File;
import com.example.upload_image_api.repository.FileRepository;
import com.example.upload_image_api.service.FileService;
import com.example.upload_image_api.util.UploadFile;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class FileServiceImpl implements FileService {
    @Autowired
    private FileRepository repository;
    @Autowired
    private UploadFile uploadFile;

    @Override
    @Transactional
    public void save(MultipartFile file, String title, String description) {
        if (!Objects.requireNonNull(file.getContentType()).contains("image")) {
            throw new RuntimeException("only images");
        }
        String name = LocalDateTime.now() + file.getOriginalFilename();
        String path = uploadFile.save(file, name);
        File f = new File(title, description, name, file.getOriginalFilename(), file.getContentType(), path);
        repository.save(f);
    }

    @Override
    @Transactional
    public List<GetImageDto> getImages() {
        return convertToGetImageDto(repository.findAll());
    }

    @Override
    @Transactional
    public List<GetImageDto> getImageByTitle(String title) {
        return convertToGetImageDto(repository.findByTitleContaining(title));
    }

    @Override
    @Transactional
    public GetImageDto getImageById(long id) {
        return repository.findById(id).map(image -> {
            GetImageDto dto = new GetImageDto();
            dto.setId(image.getId());
            dto.setTitle(image.getTitle());
            dto.setDescription(image.getDescription());
            dto.setUrl(image.getUrl());
            return dto;
        }).orElseThrow(()->new RuntimeException("image not found"));
    }

    @Override
    @Transactional
    public void updateImage(long id, UpdateImageDto dto) {
        repository.findById(id).map(image->{
            image.setTitle(dto.getTitle());
            image.setDescription(dto.getDescription());
            return repository.save(image);
        }).orElseThrow(()->new RuntimeException("image not found"));
    }

    private List<GetImageDto> convertToGetImageDto(List<File> list) {
        return list.stream().map(image -> {
            GetImageDto dto = new GetImageDto();
            dto.setId(image.getId());
            dto.setTitle(image.getTitle());
            dto.setDescription(image.getDescription());
            dto.setUrl(image.getUrl());
            return dto;
        }).collect(Collectors.toList());
    }



}
