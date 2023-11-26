package com.example.clouddog.board.api;

import com.example.clouddog.board.api.dto.request.BoardReqDto;
import com.example.clouddog.board.api.dto.response.BoardListResDto;
import com.example.clouddog.board.api.dto.response.BoardResDto;
import com.example.clouddog.board.application.BoardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "추억 아카이빙 리스트 불러오기", description = "본인의 추억 아카이빙을 모두 불러옵니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "추억 아카이빙 불러오기 성공"),
            @ApiResponse(responseCode = "401", description = "헤더 없음 or 토큰 불일치", content = @Content(schema = @Schema(example = "INVALID_HEADER or INVALID_TOKEN")))
    })
    @GetMapping("/{memberId}/boards/{boardTag}")
    public ResponseEntity<Page<BoardListResDto>> myScrapList(@PathVariable(name = "memberId") Long memberId,
                                                             @PathVariable(name = "boardTag") int boardTag,
                                                             @RequestParam(value = "page", defaultValue = "0") int page,
                                                             @RequestParam(value = "size", defaultValue = "8") int size) {
        if (boardTag == 0) {
            return new ResponseEntity<>(boardService.findAllPage(memberId, page, size), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(boardService.findByTagPage(memberId, boardTag, page, size), HttpStatus.OK);
        }
    }

    @Operation(summary = "추억 아카이빙 작성", description = "추억 아카이빙을 작성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "SUCCESS"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 값"),
            @ApiResponse(responseCode = "401", description = "헤더 없음 or 토큰 불일치", content = @Content(schema = @Schema(example = "INVALID_HEADER or INVALID_TOKEN")))
    })
    @PostMapping("/{memberId}/board")
    public ResponseEntity<String> addBoard(@PathVariable(name = "memberId") Long memberId,
                                           @RequestBody BoardReqDto boardReqDto) {
        if (boardReqDto.imageId() != 0) {
            boardService.boardAndImageSave(memberId, boardReqDto);
        } else {
            boardService.boardNotImageSave(memberId, boardReqDto);
        }

        return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
    }

    @Operation(summary = "추억 아카이빙 상세보기", description = "본인의 추억 아카이빙을 자세히 봅니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "추억 아카이빙 상세보기 불러오기 성공"),
            @ApiResponse(responseCode = "401", description = "헤더 없음 or 토큰 불일치", content = @Content(schema = @Schema(example = "INVALID_HEADER or INVALID_TOKEN")))
    })
    @GetMapping("/{memberId}/board/{boardId}")
    public ResponseEntity<BoardResDto> findBoard(@PathVariable(name = "memberId") Long memberId,
                                                 @PathVariable(name = "boardId") Long boardId) {
        return new ResponseEntity<>(boardService.findById(memberId, boardId), HttpStatus.OK);
    }

    @Operation(summary = "추억 아카이빙 수정", description = "추억 아카이빙을 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "SUCCESS"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 값"),
            @ApiResponse(responseCode = "401", description = "헤더 없음 or 토큰 불일치", content = @Content(schema = @Schema(example = "INVALID_HEADER or INVALID_TOKEN")))
    })
    @PatchMapping("/{memberId}/board/{boardId}")
    public ResponseEntity<String> updateBoard(@PathVariable(name = "memberId") Long memberId,
                                              @PathVariable(name = "boardId") Long boardId,
                                              @RequestBody BoardReqDto boardReqDto) {
        boardService.boardUpdate(boardId, boardReqDto);
        return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
    }

    @Operation(summary = "추억 아카이빙 삭제", description = "추억 아카이빙을 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "SUCCESS"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 값"),
            @ApiResponse(responseCode = "401", description = "헤더 없음 or 토큰 불일치", content = @Content(schema = @Schema(example = "INVALID_HEADER or INVALID_TOKEN")))
    })
    @DeleteMapping("/{memberId}/board/{boardId}")
    public ResponseEntity<String> deleteBoard(@PathVariable(name = "memberId") Long memberId,
                                              @PathVariable(name = "boardId") Long boardId) {
        boardService.boardDelete(memberId, boardId);
        return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
    }

}
