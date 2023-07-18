package com.example.upload_image_api.service.impl;

import com.example.upload_image_api.dto.GetImageDto;
import com.example.upload_image_api.entity.File;
import com.example.upload_image_api.repository.FileRepository;
import com.example.upload_image_api.service.FileService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class FileServiceImpl implements FileService {

    private final Path root = Paths.get("uploads");
    @Autowired
    private FileRepository repository;

    @Override
    public void init() {
        try {
            Files.createDirectories(root);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize folder for upload!");
        }
    }

    @Override
    @Transactional
    public void save(MultipartFile file, String title, String description) {
        if (!Objects.requireNonNull(file.getContentType()).contains("image")) {
            throw new RuntimeException("only images");
        }
        LocalDateTime time = LocalDateTime.now();
        String name = time + file.getOriginalFilename();
        File f = new File();
        f.setName(name);
        f.setTitle(title);
        f.setDescription(description);
        f.setOriginalName(file.getOriginalFilename());
        f.setType(file.getContentType());
        f.setUrl(root.toAbsolutePath().resolve(name).toString());
        try {
            Files.copy(file.getInputStream(), this.root.resolve(name));
            repository.save(f);
        } catch (Exception e) {
            if (e instanceof FileAlreadyExistsException) {
                throw new RuntimeException("A file of that name already exists.");
            }
            throw new RuntimeException(e.getMessage());
        }
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
    public GetImageDto getImageById(long id) {
        return repository.findById(id).map(image -> {
            GetImageDto dto = new GetImageDto();
            dto.setId(image.getId());
            dto.setTitle(image.getTitle());
            dto.setDescription(image.getDescription());
            dto.setUrl(image.getUrl());
            return dto;
        }).orElse(null);
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
