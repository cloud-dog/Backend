package com.example.clouddog.board.api.dto.response;

import com.example.clouddog.board.domain.Board;
import com.example.clouddog.comment.api.dto.response.CommentResDto;
import java.util.List;
import lombok.Builder;

@Builder
public record BoardResDto(
        Long boardId,
        String boardTitle,
        String boardTime,
        String boardPlace,
        int boardTag,
        String boardContent,
        String imageUrl,
        List<CommentResDto> comments
) {

    public static BoardResDto of(Board board, List<CommentResDto> comments) {
        return BoardResDto.builder()
                .boardId(board.getBoardId())
                .boardTitle(board.getBoardTitle())
                .boardPlace(board.getBoardPlace())
                .boardTime(board.getBoardTime())
                .boardTag(board.getBoardTag())
                .boardContent(board.getBoardContent())
                .imageUrl(board.getImage().getImageUrl())
                .comments(comments)
                .build();
    }

    public static BoardResDto notImageOf(Board board, List<CommentResDto> comments) {
        return BoardResDto.builder()
                .boardId(board.getBoardId())
                .boardTitle(board.getBoardTitle())
                .boardPlace(board.getBoardPlace())
                .boardTime(board.getBoardTime())
                .boardTag(board.getBoardTag())
                .boardContent(board.getBoardContent())
                .imageUrl("이미지가 없습니다.")
                .comments(comments)
                .build();
    }
}
