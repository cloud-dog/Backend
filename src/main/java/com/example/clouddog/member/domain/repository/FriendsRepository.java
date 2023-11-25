package com.example.clouddog.member.domain.repository;

import com.example.clouddog.member.domain.Friendship;
import com.example.clouddog.member.domain.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendsRepository extends JpaRepository<Friendship, Long> {
    boolean existsByMemberAndFriend(Member member, Member friend);

    Page<Friendship> findByMember(Member member, Pageable pageable);
}
