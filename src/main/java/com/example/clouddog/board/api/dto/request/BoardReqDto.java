package com.example.clouddog.board.api.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardReqDto {
    private String bdTitle;

    private String bdPlace;

    private int bdTag;

    private String bdContent;

    private String bdTime;

    private Long imageId;

    public BoardReqDto(String boardTitle, String boardPlace,
                       int boardTag, String boardContent, String boardTime, Long imageId) {
        this.bdTitle = boardTitle;
        this.bdPlace = boardPlace;
        this.bdTag = boardTag;
        this.bdContent = boardContent;
        this.bdTime = boardTime;
        this.imageId = imageId;
    }
}
