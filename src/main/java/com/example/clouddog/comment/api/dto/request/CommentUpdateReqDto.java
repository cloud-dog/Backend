package com.example.clouddog.comment.api.dto.request;

public record CommentUpdateReqDto(
        Long commentId,
        String commentContent
) {
}