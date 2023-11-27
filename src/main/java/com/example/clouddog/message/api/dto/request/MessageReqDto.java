package com.example.clouddog.message.api.dto.request;

public record MessageReqDto(
        Long messageId,
        String messageContent
) {
}
