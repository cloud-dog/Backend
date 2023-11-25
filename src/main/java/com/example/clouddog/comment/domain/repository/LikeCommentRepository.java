package com.example.clouddog.comment.domain.repository;

import com.example.clouddog.comment.domain.Comment;
import com.example.clouddog.comment.domain.LikeComment;
import com.example.clouddog.member.domain.Member;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeCommentRepository extends JpaRepository<LikeComment, Long> {


    List<LikeComment> findByComment(Comment comment);

    LikeComment findByMemberAndComment(Member member, Comment comment);

    boolean existsByMemberAndComment(Member member, Comment comment);
}
