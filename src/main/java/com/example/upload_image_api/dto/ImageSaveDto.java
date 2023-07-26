package com.example.upload_image_api.dto;


import com.example.upload_image_api.validation.constraint.FileType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

public record ImageSaveDto(@NotNull @FileType(type = "image") MultipartFile file, @NotBlank String title,
                           @NotBlank String description) {
}
