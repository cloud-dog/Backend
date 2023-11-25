package com.example.clouddog.comment.api.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentUpdateReqDto {

    private String cmContent;

    public CommentUpdateReqDto(String cmContent) {
        this.cmContent = cmContent;
    }
}