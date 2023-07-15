package com.example.upload_image_api.service.impl;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.upload_image_api.entity.File;
import com.example.upload_image_api.repository.FileRepository;
import com.example.upload_image_api.service.FileService;

import jakarta.transaction.Transactional;

@Service
public class FileServiceImpl implements FileService {

    private final FileRepository repository;
    private final Path root = Paths.get("uploads");

    public FileServiceImpl(FileRepository repository) {
        this.repository = repository;
    }

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
    public void save(MultipartFile file) {
        if(!Objects.requireNonNull(file.getContentType()).contains("image")){
            throw new RuntimeException("only images");
        }
        LocalDateTime time = LocalDateTime.now();
        String name = time + file.getOriginalFilename();
        File f = new File();
        f.setName(name);
        f.setOriginalName(Objects.requireNonNull(file.getOriginalFilename()).split("\\.")[0]);
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

}
