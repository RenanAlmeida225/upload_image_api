package com.example.upload_image_api.validation.constraint;

import com.example.upload_image_api.validation.FileTypeValidation;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = FileTypeValidation.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
public @interface FileType {
    String type();

    String message() default "Only images";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
