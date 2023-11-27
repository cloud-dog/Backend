package com.example.clouddog.message.api.dto.response;

import com.example.clouddog.message.domain.Message;
import lombok.Builder;

@Builder
public record MessageResDto (
        Long msgId,
        String msgContent,
        String msgTime
) {
    public static MessageResDto from(Message message) {
        return MessageResDto.builder()
                .msgId(message.getMessageId())
                .msgContent(message.getMessageContent())
                .msgTime(message.getMessageTime())
                .build();
    }
}
