package com.example.upload_image_api.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "files")
@Data
@NoArgsConstructor
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotBlank
    private String title;
    @NotBlank
    private String description;
    @NotBlank
    private String name;
    @NotBlank
    private String originalName;
    @NotBlank
    private String type;
    @NotBlank
    private String url;

    public File(String title, String description, String name, String originalName, String type, String url) {
        this.title = title;
        this.description = description;
        this.name = name;
        this.originalName = originalName;
        this.type = type;
        this.url = url;
    }
}
