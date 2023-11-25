package com.example.clouddog.comment.domain.repository;

import com.example.clouddog.board.domain.Board;
import com.example.clouddog.comment.domain.Comment;
import com.example.clouddog.member.domain.Member;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByBoardAndMember(Board board, Member member);

    List<Comment> findByBoard(Board board);
}
