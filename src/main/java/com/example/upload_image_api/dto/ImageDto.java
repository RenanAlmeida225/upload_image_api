package com.example.upload_image_api.dto;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;

public record ImageDto(@NotNull @Min(1) long id, @NotBlank String title, @NotBlank String description,
                       @Null String url) {
}
