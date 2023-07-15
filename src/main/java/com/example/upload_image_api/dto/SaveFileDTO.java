package com.example.upload_image_api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SaveFileDTO {
    private String name;
    private String type;
    private String url;
}
