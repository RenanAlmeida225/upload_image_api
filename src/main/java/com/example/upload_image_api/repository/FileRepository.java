package com.example.upload_image_api.repository;

import com.example.upload_image_api.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileRepository extends JpaRepository<File, Long> {

    List<File> findByOriginalNameContaining(String originalName);
}
