package com.example.clouddog.comment.api.dto.response;

import com.example.clouddog.comment.domain.Comment;
import java.time.LocalDate;
import java.util.List;
import lombok.Builder;

@Builder
public record CommentResDto(
        Long commentId,
        Long memberId,
        Long boardId,
        Long previousCmId,
        String commentContent,
        LocalDate commentTime,
        int commentLikes,
        List<Long> likeCommentMembers

) {
    public static CommentResDto of(Comment comment, List<Long> likeCommentMembers) {
        return CommentResDto.builder()
                .commentId(comment.getCommentId())
                .memberId(comment.getMember().getMemberId())
                .previousCmId(comment.getPreviousCmId())
                .boardId(comment.getBoard().getBoardId())
                .commentContent(comment.getCommentContent())
                .commentTime(comment.getCommentTime())
                .commentLikes(comment.getCommentLikes())
                .likeCommentMembers(likeCommentMembers)
                .build();
    }
}