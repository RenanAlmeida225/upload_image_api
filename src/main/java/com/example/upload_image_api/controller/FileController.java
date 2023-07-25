package com.example.upload_image_api.controller;

import com.example.upload_image_api.dto.GetImageDto;
import com.example.upload_image_api.dto.UpdateImageDto;
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
    public ResponseEntity<Object> save(@RequestPart("file") MultipartFile file, @RequestPart("title") String title, @RequestPart("description") String description) {
        service.save(file, title, description);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<List<GetImageDto>> getImages() {
        List<GetImageDto> images = service.getImages();
        return ResponseEntity.status(HttpStatus.OK).body(images);
    }

    @GetMapping("{title}")
    public ResponseEntity<List<GetImageDto>> getImageByName(@PathVariable String title) {
        List<GetImageDto> images = service.getImageByTitle(title);
        return ResponseEntity.status(HttpStatus.OK).body(images);
    }

    @GetMapping("image/{id}")
    public ResponseEntity<GetImageDto> getImageById(@PathVariable long id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getImageById(id));
    }

    @PutMapping("image/{id}")
    public ResponseEntity<Void> updateImage(@PathVariable long id, @RequestBody UpdateImageDto dto) {
        service.updateImage(id, dto);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("image/{id}")
    public ResponseEntity<Void> deleteImage(@PathVariable long id) {
        service.deleteImage(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
