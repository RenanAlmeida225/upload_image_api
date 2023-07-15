package com.example.upload_image_api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetImageDto {
    private long id;
    private String originalName;
    private String url;
}
