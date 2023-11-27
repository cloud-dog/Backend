package com.example.clouddog.comment.application;

import com.example.clouddog.board.domain.Board;
import com.example.clouddog.board.domain.repository.BoardRepository;
import com.example.clouddog.board.exception.NotFoundBoardException;
import com.example.clouddog.comment.api.dto.request.CommentDeleteReqDto;
import com.example.clouddog.comment.api.dto.request.CommentSaveReqDto;
import com.example.clouddog.comment.api.dto.request.CommentUpdateReqDto;
import com.example.clouddog.comment.api.dto.request.LikeCommentReqDto;
import com.example.clouddog.comment.domain.Comment;
import com.example.clouddog.comment.domain.repository.CommentRepository;
import com.example.clouddog.comment.domain.repository.LikeCommentRepository;
import com.example.clouddog.comment.exception.ExistsLikeCommentException;
import com.example.clouddog.comment.exception.NotFoundCommentException;
import com.example.clouddog.comment.exception.NotFoundLikeCommentException;
import com.example.clouddog.member.domain.Member;
import com.example.clouddog.member.domain.repository.MemberRepository;
import com.example.clouddog.member.exception.NotFoundMemberException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    private final LikeCommentRepository likeCommentRepository;

    public CommentService(CommentRepository commentRepository, BoardRepository boardRepository,
                          MemberRepository memberRepository, LikeCommentRepository likeCommentRepository) {
        this.commentRepository = commentRepository;
        this.boardRepository = boardRepository;
        this.memberRepository = memberRepository;
        this.likeCommentRepository = likeCommentRepository;
    }

    // 댓글 저장
    public void commentSave(Long memberId, Long boardId, CommentSaveReqDto commentSaveReqDto) {
        Board board = boardRepository.findById(boardId).orElseThrow(NotFoundBoardException::new);
        Member member = memberRepository.findById(memberId).orElseThrow(NotFoundMemberException::new);

        member.addComments(board, commentSaveReqDto.commentContent(), commentSaveReqDto.preciousCommentId());
        memberRepository.save(member);
    }

    //댓글 수정
    public void commentUpdate(Long memberId, CommentUpdateReqDto commentUpdateReqDto) {
        Member member = memberRepository.findById(memberId).orElseThrow(NotFoundMemberException::new);
        Comment comment = commentRepository.findByMemberAndCommentId(member, commentUpdateReqDto.commentId()).orElseThrow(NotFoundCommentException::new);

        comment.update(commentUpdateReqDto.commentContent());
    }

    //댓글 삭제
    public void commentDelete(Long memberId, CommentDeleteReqDto commentDeleteReqDto) {
        Member member = memberRepository.findById(memberId).orElseThrow(NotFoundMemberException::new);
        Comment comment = commentRepository.findById(commentDeleteReqDto.commentId()).orElseThrow(NotFoundCommentException::new);

        commentRepository.deleteByMemberAndCommentId(member, comment.getCommentId());
    }

    //댓글 좋아요 추가
    @Transactional
    public void addCommentLikes(LikeCommentReqDto likeCommentReqDto) {
        Member member = memberRepository.findById(likeCommentReqDto.memberId())
                .orElseThrow(NotFoundMemberException::new);
        Comment comment = commentRepository.findById(likeCommentReqDto.commentId()).orElseThrow(NotFoundCommentException::new);

        validateExistsLikeComment(member, comment);

        comment.addLikes(member);
        commentRepository.save(comment);
    }

    private void validateExistsLikeComment(Member member, Comment comment) {
        if (likeCommentRepository.existsByMemberAndComment(member, comment)) {
            throw new ExistsLikeCommentException();
        }
    }

    //댓글 좋아요 취소
    public void subCommentLikes(LikeCommentReqDto likeCommentReqDto) {
        Member member = memberRepository.findById(likeCommentReqDto.memberId())
                .orElseThrow(NotFoundMemberException::new);
        Comment comment = commentRepository.findById(likeCommentReqDto.commentId()).orElseThrow(NotFoundCommentException::new);

        validateNotFoundLikeComment(member, comment);

        comment.subLikes(member);
        commentRepository.save(comment);
    }

    private void validateNotFoundLikeComment(Member member, Comment comment) {
        if (!likeCommentRepository.existsByMemberAndComment(member, comment)) {
            throw new NotFoundLikeCommentException();
        }
    }
}
