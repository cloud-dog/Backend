package com.example.clouddog.board.api.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardListResDto {
    private Long memberId;
    private Long bdId;
    private String bdTitle;
    private int bdTag;
    private String bdImageUrl;

    public BoardListResDto(Long memberId, Long bdId, String bdTitle, int bdTag, String bdImageUrl) {
        this.memberId = memberId;
        this.bdId = bdId;
        this.bdTitle = bdTitle;
        this.bdTag = bdTag;
        this.bdImageUrl = bdImageUrl;
    }

}
