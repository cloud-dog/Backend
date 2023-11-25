package com.example.clouddog.board.domain;

import com.example.clouddog.board.api.dto.request.BoardReqDto;
import com.example.clouddog.comment.domain.Comment;
import com.example.clouddog.image.domain.Image;
import com.example.clouddog.member.domain.MemberWriteBoard;
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
    private Long boardId;

    private String boardTitle;

    private String boardTime;

    private String boardPlace;

    private int boardTag;

    private String boardContent;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MemberWriteBoard> memberWriteBoards = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "image_id")
    private Image image;

    @Builder
    private Board(String boardTitle, String boardPlace,
                  int boardTag, String boardContent, String boardTime, Image image) {
        this.boardTitle = boardTitle;
        this.boardPlace = boardPlace;
        this.boardTag = boardTag;
        this.boardContent = boardContent;
        this.boardTime = boardTime;
        this.image = image;
    }

    public static Board of(BoardReqDto boardReqDto, Image image) {
        return Board.builder()
                .boardTitle(boardReqDto.getBdTitle())
                .boardTime(boardReqDto.getBdTime())
                .boardPlace(boardReqDto.getBdPlace())
                .boardTag(boardReqDto.getBdTag())
                .boardContent(boardReqDto.getBdContent())
                .image(image)
                .build();
    }

    public void update(BoardReqDto boardDto) {
        this.boardTitle = boardDto.getBdTitle();
        this.boardPlace = boardDto.getBdPlace();
        this.boardTag = boardDto.getBdTag();
        this.boardContent = boardDto.getBdContent();
        this.boardTime = boardDto.getBdTime();
    }
}
