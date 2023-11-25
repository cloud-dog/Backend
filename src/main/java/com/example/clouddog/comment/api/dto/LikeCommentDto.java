package com.example.clouddog.comment.api.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LikeCommentDto {

    private Long memberId;

    public LikeCommentDto(Long memberId) {
        this.memberId = memberId;
    }
}
