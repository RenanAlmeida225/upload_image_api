package com.example.upload_image_api.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.upload_image_api.entity.File;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class FileRepositoryTest {

    @Autowired
    private FileRepository fileRepository;

    @Test
    void findByTitleOrDescription_shouldReturnImagesIfExists() {
        File file = new File("image", "image for search", "data-name", "original name", "image/png",
                "url/data-name.png");
        fileRepository.save(file);

        List<File> images = fileRepository.findByTitleOrDescription("image");
        assertEquals(images, List.of(file));
    }

    void findByTitleOrDescription_shouldReturnListEmpty() {
        List<File> images = fileRepository.findByTitleOrDescription("image");
        assertTrue(images.isEmpty());
    }
}
