package com.example.clouddog.member.domain.repository;

import com.example.clouddog.member.domain.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByMemberName(String memberName);

    Optional<Member> findByEmail(String email);
}
