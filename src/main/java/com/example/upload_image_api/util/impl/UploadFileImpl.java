package com.example.upload_image_api.util.impl;

import com.example.upload_image_api.util.UploadFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class UploadFileImpl implements UploadFile {
    private final Path root = Paths.get("uploads");

    private void createDirectory() {
        try {
            Files.createDirectory(root);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize folder for upload!");
        }
    }

    @Override
    public String save(MultipartFile file, String name) {
        if (!Files.exists(root)) {
            createDirectory();
        }
        try {
            Files.copy(file.getInputStream(), root.resolve(name));
            return root.toAbsolutePath().resolve(name).toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(String path) {
        try {
            Files.delete(Paths.get(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
