package com.example.clouddog.comment.api.dto.request;

public record LikeCommentReqDto(
        Long commentId,
        Long memberId
) {
}
