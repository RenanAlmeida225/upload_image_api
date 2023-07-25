package com.example.upload_image_api.exception;

import java.time.Instant;

public record StandardError(Instant timestamp, Integer status, String error, String message, String path) {
}
