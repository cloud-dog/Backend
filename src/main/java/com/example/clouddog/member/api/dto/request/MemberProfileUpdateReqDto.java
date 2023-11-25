package com.example.clouddog.member.api.dto.request;

public record MemberProfileUpdateReqDto(
        String nickName,
        int petNumber,
        String petName,
        String petDescription,
        int mindCount
) {
}
