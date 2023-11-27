package com.example.clouddog.comment.api;

import com.example.clouddog.comment.api.dto.request.CommentDeleteReqDto;
import com.example.clouddog.comment.api.dto.request.CommentSaveReqDto;
import com.example.clouddog.comment.api.dto.request.CommentUpdateReqDto;
import com.example.clouddog.comment.api.dto.request.LikeCommentReqDto;
import com.example.clouddog.comment.application.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @Operation(summary = "댓글 작성", description = "댓글을 작성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "SUCCESS"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 값"),
            @ApiResponse(responseCode = "401", description = "헤더 없음 or 토큰 불일치", content = @Content(schema = @Schema(example = "INVALID_HEADER or INVALID_TOKEN")))
    })
    @PostMapping("/{memberId}/comments/{boardId}")
    public ResponseEntity<String> addComment(@PathVariable(name = "memberId") Long memberId,
                                             @PathVariable(name = "boardId") Long boardId,
                                             @RequestBody CommentSaveReqDto commentSaveReqDto) {
        commentService.commentSave(memberId, boardId, commentSaveReqDto);
        return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
    }

    @Operation(summary = "댓글 수정", description = "댓글을 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "SUCCESS"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 값"),
            @ApiResponse(responseCode = "401", description = "헤더 없음 or 토큰 불일치", content = @Content(schema = @Schema(example = "INVALID_HEADER or INVALID_TOKEN")))
    })
    @PatchMapping("/{memberId}/comments")
    public ResponseEntity<String> updateComment(@PathVariable(name = "memberId") Long memberId,
                                                @RequestBody CommentUpdateReqDto commentUpdateReqDto) {
        commentService.commentUpdate(memberId, commentUpdateReqDto);
        return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
    }

    @Operation(summary = "댓글 삭제", description = "댓글을 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "SUCCESS"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 값"),
            @ApiResponse(responseCode = "401", description = "헤더 없음 or 토큰 불일치", content = @Content(schema = @Schema(example = "INVALID_HEADER or INVALID_TOKEN")))
    })
    @DeleteMapping("/{memberId}/comments")
    public ResponseEntity<String> deleteComment(@PathVariable(name = "memberId") Long memberId,
                                                @RequestBody CommentDeleteReqDto commentDeleteReqDto) {
        commentService.commentDelete(memberId, commentDeleteReqDto);
        return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
    }

    @Operation(summary = "댓글에 좋아요 추가", description = "댓글에 좋아요를 추가합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "SUCCESS"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 값"),
            @ApiResponse(responseCode = "401", description = "헤더 없음 or 토큰 불일치", content = @Content(schema = @Schema(example = "INVALID_HEADER or INVALID_TOKEN")))
    })
    @PostMapping("/comment/add-likes")
    public ResponseEntity<String> addCmLikes(@RequestBody LikeCommentReqDto likeCommentReqDto) {
        commentService.addCmLikes(likeCommentReqDto);
        return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
    }

    @Operation(summary = "댓글 좋아요 취소", description = "댓글에 좋아요를 취소합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "SUCCESS"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 값"),
            @ApiResponse(responseCode = "401", description = "헤더 없음 or 토큰 불일치", content = @Content(schema = @Schema(example = "INVALID_HEADER or INVALID_TOKEN")))
    })
    @PostMapping("/comment/sub-likes")
    public ResponseEntity<String> subCmLikes(@RequestBody LikeCommentReqDto likeCommentReqDto) {
        commentService.subCmLikes(likeCommentReqDto);
        return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
    }

}
