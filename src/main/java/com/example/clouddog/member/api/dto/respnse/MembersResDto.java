package com.example.clouddog.member.api.dto.respnse;

import java.util.List;
import lombok.Builder;

@Builder
public record MembersResDto(
        List<MemberResDto> memberResDtos
) {
    public static MembersResDto of(List<MemberResDto> memberResDtos) {
        return MembersResDto.builder()
                .memberResDtos(memberResDtos)
                .build();
    }
}
