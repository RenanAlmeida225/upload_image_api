package com.example.upload_image_api.controller;

import com.example.upload_image_api.dto.ImageDto;
import com.example.upload_image_api.dto.ImageSaveDto;
import com.example.upload_image_api.service.FileService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("file")
public class FileController {
    @Autowired
    private FileService service;

    @PostMapping
    public ResponseEntity<Object> save(@Valid @ModelAttribute ImageSaveDto image) {
        service.save(image);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<List<ImageDto>> getImages() {
        List<ImageDto> images = service.getImages();
        return ResponseEntity.status(HttpStatus.OK).body(images);
    }

    @GetMapping("{data}")
    public ResponseEntity<List<ImageDto>> searchImages(@PathVariable String data) {
        List<ImageDto> images = service.searchImages(data);
        return ResponseEntity.status(HttpStatus.OK).body(images);
    }

    @GetMapping("image/{id}")
    public ResponseEntity<ImageDto> getImageById(@PathVariable long id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getImageById(id));
    }

    @PutMapping("image/{id}")
    public ResponseEntity<Void> updateImage(@Valid @PathVariable long id, @RequestBody ImageDto image) {
        service.updateImage(new ImageDto(id, image.title(), image.description(), null));
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("image/{id}")
    public ResponseEntity<Void> deleteImage(@PathVariable long id) {
        service.deleteImage(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
