package com.example.clouddog.message.api.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MessageReqDto {
    private Long memberId;
    private String msgContent;

    public MessageReqDto(Long memberId, String msgContent) {
        this.memberId = memberId;
        this.msgContent = msgContent;
    }
}
