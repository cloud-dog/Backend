package com.example.clouddog.board.api.dto.response;

public record BoardListResDto(
        Long memberId,
        Long boardId,
        String boardTitle,
        int boardTag,
        String imageUrl
) {
}
