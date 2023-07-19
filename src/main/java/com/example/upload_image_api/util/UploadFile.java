package com.example.upload_image_api.util;

import org.springframework.web.multipart.MultipartFile;

public interface UploadFile {

    String save(MultipartFile file, String name);
}
