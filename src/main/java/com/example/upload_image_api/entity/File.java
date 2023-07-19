package com.example.upload_image_api.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "files")
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String title;
    private String description;
    private String name;
    private String originalName;
    private String type;
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
