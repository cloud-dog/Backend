package com.example.clouddog.board.api.dto.response;

import com.example.clouddog.board.domain.Board;
import com.example.clouddog.comment.api.dto.response.CommentResDto;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardResDto {

    private Long bdId;

    private String bdTitle;

    private String bdTime;

    private String bdPlace;

    private int bdTag;

    private String bdContent;

    private String imageUrl;

    List<CommentResDto> comments = new ArrayList<>();

    @Builder
    private BoardResDto(Long bdId, String bdTitle, String bdPlace, String bdTime,
                        int bdTag, String bdContent, String imageUrl,
                        List<CommentResDto> comments) {
        this.bdId = bdId;
        this.bdTitle = bdTitle;
        this.bdPlace = bdPlace;
        this.bdTime = bdTime;
        this.bdTag = bdTag;
        this.bdContent = bdContent;
        this.imageUrl = imageUrl;
        this.comments = comments;
    }

    public static BoardResDto of(Board board, List<CommentResDto> comments) {
        return BoardResDto.builder()
                .bdId(board.getBoardId())
                .bdTitle(board.getBoardTitle())
                .bdPlace(board.getBoardPlace())
                .bdTime(board.getBoardTime())
                .bdTag(board.getBoardTag())
                .bdContent(board.getBoardContent())
                .imageUrl(board.getImage().getImageUrl())
                .comments(comments)
                .build();
    }
}
