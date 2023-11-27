package com.example.clouddog.comment.api.dto.request;

public record CommentSaveReqDto(
        String commentContent,
        Long preciousCommentId
) {
}
