package com.example.upload_image_api.util.impl;

import com.example.upload_image_api.config.Properties;
import com.example.upload_image_api.util.UploadFile;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.StorageClient;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;

@Component
public class UploadFileFirebase implements UploadFile {

    @Autowired
    Properties properties;

    @PostConstruct
    public void init() {
        try {
            FileInputStream serviceAccount = new FileInputStream(properties.getServiceAccountKey());
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setStorageBucket(properties.getBucketName())
                    .build();
            FirebaseApp.initializeApp(options);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String save(MultipartFile file, String name) {
        try {
            Bucket bucket = StorageClient.getInstance().bucket();
            bucket.create(name, file.getBytes(), file.getContentType());
            return properties.getImageUrl() + name;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(String fileName) {
        Bucket bucket = StorageClient.getInstance().bucket();
        Blob blob = bucket.get(fileName);
        blob.delete();
    }
}
