package com.example.clouddog.global.error.dto;

public record ErrorResponse(
        int statusCode,
        String message
) {
}