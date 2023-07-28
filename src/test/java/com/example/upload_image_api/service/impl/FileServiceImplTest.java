package com.example.upload_image_api.service.impl;

import com.example.upload_image_api.dto.ImageDto;
import com.example.upload_image_api.dto.ImageSaveDto;
import com.example.upload_image_api.entity.File;
import com.example.upload_image_api.exception.EntityNotFoundException;
import com.example.upload_image_api.repository.FileRepository;
import com.example.upload_image_api.util.UploadFile;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FileServiceImplTest {
    MockedStatic<LocalDateTime> localDateTimeMocked;
    @InjectMocks
    private FileServiceImpl fileService;
    @Mock
    private FileRepository fileRepository;
    @Mock
    private UploadFile uploadFile;

    @Test
    void save_ShouldSaveImage() {
        MockMultipartFile mockFile = new MockMultipartFile("file", "image.png", MediaType.IMAGE_PNG_VALUE,
                "image".getBytes());
        String title = "any_title";
        String description = "any_title";
        File file = new File(title, description, LocalDateTime.of(2022, 10, 22, 10, 0) + "image.png",
                mockFile.getOriginalFilename(), mockFile.getContentType(), "any_url/image.png");
        ImageSaveDto image = new ImageSaveDto(mockFile, title, description);
        localDateTimeMocked = mockStatic(LocalDateTime.class, CALLS_REAL_METHODS);
        LocalDateTime now = LocalDateTime.of(2022, 10, 22, 10, 0);
        localDateTimeMocked.when(LocalDateTime::now).thenReturn(now);
        when(uploadFile.save(any(), any(), any())).thenReturn("any_url/image.png");

        fileService.save(image);

        verify(fileRepository).save(file);
    }

    @Test
    void getImages_ShouldReturnAllImages() {
        List<ImageDto> imagesDto = new ArrayList<>() {
            {
                new ImageDto(1L, "any_title", "any_description", "any_url/image.png");
                new ImageDto(2L, "any_title", "any_description", "any_url/image.png");
                new ImageDto(3L, "any_title", "any_description", "any_url/image.png");
            }
        };

        List<File> files = new ArrayList<>() {
            {
                new File("any_title", "any_description", "any_name", "any_originalName", "image/png",
                        "any_url/image.png").setId(1L);
                new File("any_title", "any_description", "any_name", "any_originalName", "image/png",
                        "any_url/image.png").setId(2L);
                new File("any_title", "any_description", "any_name", "any_originalName", "image/png",
                        "any_url/image.png").setId(3L);
            }
        };
        when(fileRepository.findAll()).thenReturn(files);
        List<ImageDto> images = fileService.getImages();
        assertEquals(images, imagesDto);
    }

    @Test
    void getImages_ShouldReturnEmptyList() {
        when(fileRepository.findAll()).thenReturn(new ArrayList<File>());
        List<ImageDto> images = fileService.getImages();
        assertTrue(images.isEmpty());
    }

    @Test
    void getImageByTitle_ShouldReturnAllImagesIfTitleExists() {
        List<ImageDto> imagesDto = new ArrayList<>() {
            {
                new ImageDto(1L, "any_title", "any_description", "any_url/image.png");
                new ImageDto(2L, "any_title", "any_description", "any_url/image.png");
                new ImageDto(3L, "any_title", "any_description", "any_url/image.png");
            }
        };

        List<File> files = new ArrayList<>() {
            {
                new File("any_title", "any_description", "any_name", "any_originalName", "image/png",
                        "any_url/image.png").setId(1L);
                new File("any_title", "any_description", "any_name", "any_originalName", "image/png",
                        "any_url/image.png").setId(2L);
                new File("any_title", "any_description", "any_name", "any_originalName", "image/png",
                        "any_url/image.png").setId(3L);
            }
        };

        when(fileRepository.findByTitleContaining("title")).thenReturn(files);
        List<ImageDto> images = fileService.getImageByTitle("title");
        assertEquals(images, imagesDto);
    }

    @Test
    void getImageById_ShouldReturnImageById() {
        ImageDto dto = new ImageDto(1L, "any_title", "any_description", "any_url/image.png");
        File file = new File("any_title", "any_description", "any_name", "any_originalName", "image/png",
                "any_url/image.png");
        file.setId(1L);

        when(fileRepository.findById(1L)).thenReturn(Optional.of(file));
        ImageDto image = fileService.getImageById(1L);

        assertEquals(image, dto);
    }

    @Test
    void getImageById_ShouldThrowIfImageNotFound() {
        when(fileRepository.findById(1L)).thenThrow(EntityNotFoundException.class);
        assertThrows(EntityNotFoundException.class, () -> fileService.getImageById(1L));
    }

    @Test
    void updateImage_ShouldUpdateImage() {
        File file = new File("any_title", "any_description", "any_name", "any_originalName", "image/png",
                "any_url/image.png");
        file.setId(1L);
        when(fileRepository.findById(1L)).thenReturn(Optional.of(file));

        fileService.updateImage(new ImageDto(1L, "new title", "new description", null));

        verify(fileRepository).save(any());
    }

    @Test
    void updateImage_ShouldThrowIfImageNotFound() {
        when(fileRepository.findById(1L)).thenThrow(EntityNotFoundException.class);
        assertThrows(EntityNotFoundException.class, () -> fileService.updateImage(new ImageDto(1L, "new title", "new description", null)));
    }

    @Test
    void deleteImage_ShouldDeleteImage() {
        File file = new File("any_title", "any_description", "any_name", "any_originalName", "image/png",
                "any_url/image.png");
        file.setId(1L);
        when(fileRepository.findById(1L)).thenReturn(Optional.of(file));

        fileService.deleteImage(1L);
        verify(uploadFile).delete(any());
        verify(fileRepository).delete(any());
    }

    @Test
    void deleteImage_ShouldThrowIfImageNotFound() {
        when(fileRepository.findById(1L)).thenThrow(EntityNotFoundException.class);
        assertThrows(EntityNotFoundException.class, () -> fileService.deleteImage(1L));
    }
}