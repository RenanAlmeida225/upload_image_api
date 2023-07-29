package com.example.upload_image_api.repository;

import com.example.upload_image_api.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FileRepository extends JpaRepository<File, Long> {
    @Query("SELECT f FROM File f WHERE f.title LIKE %:data% OR f.description LIKE %:data%")
    List<File> findByTitleOrDescription(@Param("data") String data);
}
