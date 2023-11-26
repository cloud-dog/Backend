package com.example.clouddog.board.api.dto.request;

public record BoardReqDto(
        String boardTitle,
        String boardPlace,
        int boardTag,
        String boardContent,
        String boardTime,
        Long imageId
) {
}
