package com.example.clouddog.member.domain.repository;

import com.example.clouddog.member.domain.Member;
import com.example.clouddog.member.domain.MemberWriteBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberWriteBoardRepository extends JpaRepository<MemberWriteBoard, Long> {
    Page<MemberWriteBoard> findByMember(Member member, PageRequest pageRequest);

    Page<MemberWriteBoard> findByMemberAndTag(Member member, int tag, PageRequest pageRequest);
}
