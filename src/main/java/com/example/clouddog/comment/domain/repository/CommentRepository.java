package com.example.clouddog.comment.domain.repository;

import com.example.clouddog.board.domain.Board;
import com.example.clouddog.comment.domain.Comment;
import com.example.clouddog.member.domain.Member;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByBoard(Board board);
    void deleteByMemberAndCommentId(Member member, Long commentId);
    Optional<Comment> findByMemberAndCommentId(Member member, Long commentId);
}
