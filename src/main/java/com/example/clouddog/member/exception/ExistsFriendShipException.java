package com.example.clouddog.member.exception;

import com.example.clouddog.member.domain.Member;

public class ExistsFriendShipException extends RuntimeException {
    public ExistsFriendShipException(Member member, Member friend) {
        super(String.format("%s와 %s는 이미 친구 상태 입니다.", member.getName(), friend.getName()));
    }
}
