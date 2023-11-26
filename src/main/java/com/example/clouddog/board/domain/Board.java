package com.example.clouddog.board.domain;

import com.example.clouddog.board.api.dto.request.BoardReqDto;
import com.example.clouddog.comment.domain.Comment;
import com.example.clouddog.image.domain.Image;
import com.example.clouddog.member.domain.MemberWriteBoard;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    @Schema(description = "아카이빙 Id", example = "1")
    private Long boardId;

    @Schema(description = "아카이빙 제목", example = "강아지와 산책한 날")
    private String boardTitle;

    @Schema(description = "추억의 날짜", example = "2023-11-14")
    private String boardTime;

    @Schema(description = "추억의 장소", example = "집 앞 공원")
    private String boardPlace;

    @Schema(description = "아카이빙 테그", example = "0")
    private int boardTag;

    @Schema(description = "아카이빙 내용", example = "아카이빙 내용")
    private String boardContent;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MemberWriteBoard> memberWriteBoards = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "image_id")
    @Schema(description = "추억 이미지, 없으면 0을 요청")
    private Image image;

    @Builder
    private Board(String boardTitle, String boardPlace, int boardTag, String boardContent, String boardTime, Image image) {
        this.boardTitle = boardTitle;
        this.boardPlace = boardPlace;
        this.boardTag = boardTag;
        this.boardContent = boardContent;
        this.boardTime = boardTime;
        this.image = image;
    }

    public static Board of(BoardReqDto boardReqDto, Image image) {
        return Board.builder()
                .boardTitle(boardReqDto.boardTitle())
                .boardTime(boardReqDto.boardTime())
                .boardPlace(boardReqDto.boardPlace())
                .boardTag(boardReqDto.boardTag())
                .boardContent(boardReqDto.boardContent())
                .image(image)
                .build();
    }

    public void update(BoardReqDto boardReqDto) {
        this.boardTitle = boardReqDto.boardTitle();
        this.boardPlace = boardReqDto.boardPlace();
        this.boardTag = boardReqDto.boardTag();
        this.boardContent = boardReqDto.boardContent();
        this.boardTime = boardReqDto.boardTime();
    }
}
