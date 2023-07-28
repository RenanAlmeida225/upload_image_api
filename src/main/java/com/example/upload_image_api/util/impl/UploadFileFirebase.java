package com.example.upload_image_api.util.impl;

import com.example.upload_image_api.config.Properties;
import com.example.upload_image_api.util.UploadFile;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.StorageClient;
import org.springframework.stereotype.Component;
import java.io.FileInputStream;
import java.io.InputStream;

@Component
public class UploadFileFirebase implements UploadFile {

    private final Properties properties;

    public UploadFileFirebase(Properties properties) {
        this.properties = properties;
    }

    public Bucket connectToFirebase() {
        try {
            if (FirebaseApp.getApps().isEmpty()) {
                FileInputStream serviceAccount = new FileInputStream(properties.getServiceAccountKey());
                FirebaseOptions options = new FirebaseOptions.Builder()
                        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                        .setStorageBucket(properties.getBucketName())
                        .build();
                FirebaseApp.initializeApp(options);
            }
            return StorageClient.getInstance().bucket();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String save(InputStream content, String name, String mimetype) {
        Bucket bucket = connectToFirebase();
        bucket.create(name, content, mimetype);
        return properties.getImageUrl() + name;
    }

    @Override
    public void delete(String fileName) {
        Bucket bucket = connectToFirebase();
        Blob blob = bucket.get(fileName);
        blob.delete();
    }
}
