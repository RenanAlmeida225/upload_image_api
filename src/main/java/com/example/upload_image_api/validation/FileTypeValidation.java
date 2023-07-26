package com.example.upload_image_api.validation;

import com.example.upload_image_api.validation.constraint.FileType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

public class FileTypeValidation implements ConstraintValidator<FileType, MultipartFile> {
    String fileType;

    @Override
    public void initialize(FileType constraintAnnotation) {
        fileType = constraintAnnotation.type();
    }

    @Override
    public boolean isValid(MultipartFile multipartFile, ConstraintValidatorContext constraintValidatorContext) {
        return Objects.requireNonNull(multipartFile.getContentType()).contains(fileType);
    }
}
