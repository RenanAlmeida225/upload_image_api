package com.example.upload_image_api.controller;

import com.example.upload_image_api.dto.GetImageDto;
import com.example.upload_image_api.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("file")
public class FileController {
    @Autowired
    private FileService service;

    @PostMapping
    public ResponseEntity<Object> save(@RequestParam("file") MultipartFile file) {
        try {
            service.save(file);
            return ResponseEntity.status(HttpStatus.OK).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<GetImageDto>> getImages() {
        List<GetImageDto> images = service.getImages();
        return ResponseEntity.status(HttpStatus.OK).body(images);
    }

    @GetMapping("{name}")
    public ResponseEntity<List<GetImageDto>> getImageByName(@PathVariable String name) {
        List<GetImageDto> images = service.getImageByName(name);
        return ResponseEntity.status(HttpStatus.OK).body(images);
    }

    @GetMapping("image/{id}")
    public ResponseEntity<?> getImageById(@PathVariable Long id) {
        GetImageDto dto = service.getImageById(id);
        if (dto == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Image not found!");
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }


}
