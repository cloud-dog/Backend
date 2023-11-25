package com.example.clouddog.board.api;

import com.example.clouddog.board.api.dto.request.BoardReqDto;
import com.example.clouddog.board.api.dto.response.BoardListResDto;
import com.example.clouddog.board.api.dto.response.BoardResDto;
import com.example.clouddog.board.application.BoardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class BoardController {

    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    //게시글 불러오기
    @GetMapping("/{memberId}/boards/{bdTag}")
    public ResponseEntity<Page<BoardListResDto>> myScrapList(@PathVariable("memberId") Long memberId,
                                                             @PathVariable int bdTag,
                                                             @RequestParam(value = "page", defaultValue = "0") int page,
                                                             @RequestParam(value = "size", defaultValue = "8") int size) {
        if (bdTag == 0) {
            return new ResponseEntity<>(boardService.findAllPage(memberId, page, size), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(boardService.findByTagPage(memberId, bdTag, page, size), HttpStatus.OK);
        }
    }

    //게시글 작성
    @PostMapping("/{memberId}/board")
    public ResponseEntity<String> addBoard(@PathVariable(name = "memberId") Long memberId,
                                           @RequestBody BoardReqDto board) {
        if (board.getImageId() != 0) {
            boardService.boardAndImageSave(memberId, board);
        } else {
            boardService.boardNotImageSave(memberId, board);
        }

        return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
    }

    //게시글 상세 불러오기
    @GetMapping("/{memberId}/board/{bdId}")
    public ResponseEntity<BoardResDto> findBoard(@PathVariable(name = "memberId") Long memberId,
                                                 @PathVariable Long bdId) {
        BoardResDto boardDto = boardService.findById(memberId, bdId);
        return new ResponseEntity<>(boardDto, HttpStatus.OK);
    }

    //게시글 수정
    @PatchMapping("/{memberId}/board/{bdId}")
    public ResponseEntity<String> updateBoard(@PathVariable(name = "memberId") Long memberId, @PathVariable Long bdId,
                                              @RequestBody BoardReqDto boardDto) {
        boardService.boardUpdate(bdId, boardDto);
        return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
    }

    //게시글 삭제
    @DeleteMapping("/{memberId}/board/{bdId}")
    public ResponseEntity<String> deleteBoard(@PathVariable(name = "memberId") Long memberId, @PathVariable Long bdId) {
        boardService.boardDelete(memberId, bdId);
        return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
    }

}
