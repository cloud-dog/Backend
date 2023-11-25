package com.example.clouddog.member.api.dto.respnse;

import com.example.clouddog.member.domain.Member;
import lombok.Builder;

@Builder
public record MemberResDto(
        Long memberId,
        String name,
        String email,
        String picture,
        int petNumber,
        String petName,
        String petDescription,
        int mindCount
) {
    public static MemberResDto of(Member member) {
        return MemberResDto.builder()
                .memberId(member.getMemberId())
                .name(member.getName())
                .email(member.getEmail())
                .picture(member.getPicture())
                .petNumber(member.getPetNumber())
                .petName(member.getPetName())
                .petDescription(member.getPetDescription())
                .mindCount(member.getMindCount())
                .build();
    }
}
