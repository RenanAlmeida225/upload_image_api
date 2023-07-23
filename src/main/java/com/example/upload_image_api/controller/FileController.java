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
        try {
            service.save(file, title, description);
            return ResponseEntity.status(HttpStatus.CREATED).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
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
    public ResponseEntity<?> getImageById(@PathVariable long id) {
        try {
            GetImageDto dto = service.getImageById(id);
            return ResponseEntity.status(HttpStatus.OK).body(dto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("image/{id}")
    public ResponseEntity<?> updateImage(@PathVariable long id, @RequestBody UpdateImageDto dto) {
        try {
            service.updateImage(id, dto);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("image/{id}")
    public ResponseEntity<?> deleteImage(@PathVariable long id) {
        try {
            service.deleteImage(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
