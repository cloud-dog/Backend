package com.example.clouddog.message.domain.repository;

import com.example.clouddog.member.domain.Member;
import com.example.clouddog.message.domain.Message;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByMember(Member member);
    Message findByMemberAndMessageId(Member member, Long messageId);
    void deleteByMemberAndMessageId(Member member, Long messageId);
}
