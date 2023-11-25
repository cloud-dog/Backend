package com.example.clouddog.comment.api.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentSaveReqDto {

    private String cmContent;

    private Long preciousCommentId;

    public CommentSaveReqDto(String cmContent, Long preciousCommentId) {
        this.cmContent = cmContent;
        this.preciousCommentId = preciousCommentId;
    }
}
