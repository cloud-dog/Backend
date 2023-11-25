package com.example.clouddog.member.application;

import com.example.clouddog.member.api.dto.request.FriendSaveReqDto;
import com.example.clouddog.member.api.dto.request.MemberProfileUpdateReqDto;
import com.example.clouddog.member.api.dto.respnse.MemberResDto;
import com.example.clouddog.member.domain.Friendship;
import com.example.clouddog.member.domain.Member;
import com.example.clouddog.member.domain.repository.FriendsRepository;
import com.example.clouddog.member.domain.repository.MemberRepository;
import com.example.clouddog.member.exception.EqualMemberAndFriendException;
import com.example.clouddog.member.exception.ExistsFriendShipException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;
    private final FriendsRepository friendsRepository;

    public MemberService(MemberRepository memberRepository, FriendsRepository friendsRepository) {
        this.memberRepository = memberRepository;
        this.friendsRepository = friendsRepository;
    }

    // 메인에서 멤버(본인) 응답 -> 어차피 헤더에 엑세스토큰이 들어오므로 토큰에서 검증 후 이메일로 멤버 찾아서 반환하는 로직임.
    public MemberResDto memberInfo(String uid) {
        return MemberResDto.of(memberRepository.findByMemberName(uid).orElseThrow());
    }

    // 멤버 프로필 저장, 업데이트.
    @Transactional
    public void myProfileUpdate(Long memberId, MemberProfileUpdateReqDto memberProfileUpdateReqDto) {
        Member member = memberRepository.findById(memberId).orElseThrow();
        member.profileUpdate(memberProfileUpdateReqDto);
    }

    // 친구 찾기
    public MemberResDto friendInfo(String friendEmail) {
        return MemberResDto.of(memberRepository.findByEmail(friendEmail).orElseThrow());
    }

    // 친구 추가
    @Transactional
    public void addFriend(Long memberId, FriendSaveReqDto friendSaveReqDto) {
        Member member = memberRepository.findById(memberId).orElseThrow();
        Member friend = memberRepository.findByEmail(friendSaveReqDto.friendEmail()).orElseThrow();

        validateEqualMemberAndFriend(member, friend);
        validateDuplicationFriendship(member, friend);

        friendsRepository.save(Friendship.of(member, friend));
    }

    private void validateEqualMemberAndFriend(Member member, Member friend) {
        if (member.equals(friend)) {
            throw new EqualMemberAndFriendException(member, friend);
        }
    }

    private void validateDuplicationFriendship(Member member, Member friend) {
        if (friendsRepository.existsByMemberAndFriend(member, friend)) {
            throw new ExistsFriendShipException(member, friend);
        }
    }

    // 친구 목록
    public Page<MemberResDto> friendsInfoList(Long memberId, int page, int size) {
        Member member = memberRepository.findById(memberId).orElseThrow();

        Page<Friendship> friendships = friendsRepository.findByMember(member, PageRequest.of(page, size));

        return friendships.map(friendship -> MemberResDto.of(friendship.getFriend()));
    }
}
