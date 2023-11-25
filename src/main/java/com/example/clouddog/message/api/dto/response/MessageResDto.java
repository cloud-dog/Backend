package com.example.clouddog.message.api.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MessageResDto {
    private Long msgId;

    private String msgContent;

    private String msgTime;

    public MessageResDto(Long msgId, String msgContent, String msgTime) {
        this.msgId = msgId;
        this.msgTime = msgTime;
        this.msgContent = msgContent;
    }
}
