package com.example.upload_image_api.util;

import java.io.InputStream;

public interface UploadFile {

    String save(InputStream content, String name, String mimetype);

    void delete(String fileName);
}
