package com.example.clouddog.comment.application;

import com.example.clouddog.board.domain.Board;
import com.example.clouddog.board.domain.repository.BoardRepository;
import com.example.clouddog.board.exception.NotFoundBoardException;
import com.example.clouddog.board.exception.NotFoundMemberException;
import com.example.clouddog.comment.api.dto.LikeCommentDto;
import com.example.clouddog.comment.api.dto.request.CommentSaveReqDto;
import com.example.clouddog.comment.api.dto.request.CommentUpdateReqDto;
import com.example.clouddog.comment.domain.Comment;
import com.example.clouddog.comment.domain.repository.CommentRepository;
import com.example.clouddog.comment.domain.repository.LikeCommentRepository;
import com.example.clouddog.comment.exception.NotFoundCommentException;
import com.example.clouddog.member.domain.Member;
import com.example.clouddog.member.domain.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
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

        member.addComments(board, commentSaveReqDto.getCmContent(), commentSaveReqDto.getPreciousCommentId());
        memberRepository.save(member);
    }

    //댓글 좋아요 추가
    @Transactional
    public void addCmLikes(Long cmId, LikeCommentDto likeCommentDto) {
        Member member = memberRepository.findById(likeCommentDto.getMemberId())
                .orElseThrow(NotFoundMemberException::new);
        Comment comment = commentRepository.findById(cmId).orElseThrow(NotFoundCommentException::new);

        if (likeCommentRepository.existsByMemberAndComment(member, comment)) {
            throw new IllegalArgumentException();
        }

        comment.addLikes(member);
        commentRepository.save(comment);
    }

    //댓글 좋아요 취소
    public void subCmLikes(Long cmId, LikeCommentDto likeCommentDto) {
        Member member = memberRepository.findById(likeCommentDto.getMemberId())
                .orElseThrow(NotFoundMemberException::new);
        Comment comment = commentRepository.findById(cmId).orElseThrow(NotFoundCommentException::new);

        if (!likeCommentRepository.existsByMemberAndComment(member, comment)) {
            throw new IllegalArgumentException();
        }

        comment.subLikes(member);
        commentRepository.save(comment);
    }

    //댓글 수정
    public void commentUpdate(Long cmId, CommentUpdateReqDto updateCm) {
        commentRepository.findById(cmId).orElseThrow(NotFoundCommentException::new).update(
                updateCm.getCmContent()
        );
    }

    //댓글 삭제
    public void commentDelete(Long cmId) {
        commentRepository.deleteById(cmId);
    }
}
