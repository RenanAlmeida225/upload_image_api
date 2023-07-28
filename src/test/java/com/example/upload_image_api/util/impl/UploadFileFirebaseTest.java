package com.example.upload_image_api.util.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.io.IOException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import com.example.upload_image_api.config.Properties;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.firebase.cloud.StorageClient;

@ExtendWith(MockitoExtension.class)
public class UploadFileFirebaseTest {

    @Mock
    private Properties properties;
    @InjectMocks
    private UploadFileFirebase uploadFileFirebase;

    private static final String serviceAccount = "/home/italo/Downloads/serviceAccountKey.json";
    private static final String imageUrl = "https://storage.googleapis.com/upload-image-97fae.appspot.com/";
    private static final String bucketName = "upload-image-97fae.appspot.com";

    @BeforeEach
    void setup() {
        when(properties.getServiceAccountKey()).thenReturn(serviceAccount);        
        when(properties.getImageUrl()).thenReturn(imageUrl);
        when(properties.getBucketName()).thenReturn(bucketName);
    }

    @AfterEach
    void reset() {
        Mockito.reset(properties);
    }

    @Test
    void save_shouldSavefileInFirebase() throws IOException {
        MockMultipartFile mockFile = new MockMultipartFile("file", "image.png", MediaType.IMAGE_PNG_VALUE,
                "image".getBytes());
        String path = uploadFileFirebase.save(mockFile.getInputStream(), "data-file", mockFile.getContentType());
        path.equals(imageUrl + "data-file");
        Bucket bucket = StorageClient.getInstance().bucket();
        boolean exists = bucket.get("data-file").exists();
        assertTrue(exists);
    }

    @Test
    void delete_shouldDeleteFileInFireBase() throws IOException {
        MockMultipartFile mockFile = new MockMultipartFile("file", "image.png",
                MediaType.IMAGE_PNG_VALUE,
                "image".getBytes());
        uploadFileFirebase.save(mockFile.getInputStream(), "delete-file",
                mockFile.getContentType());
        uploadFileFirebase.delete("delete-file");
        Bucket bucket = StorageClient.getInstance().bucket();
        Blob blob = bucket.get("delete-file");
        assertThrows(NullPointerException.class, () -> blob.exists());
    }
}
