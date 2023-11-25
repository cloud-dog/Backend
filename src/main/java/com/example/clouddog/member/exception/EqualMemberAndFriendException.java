package com.example.clouddog.member.exception;

import com.example.clouddog.member.domain.Member;

public class EqualMemberAndFriendException extends RuntimeException {
    public EqualMemberAndFriendException(Member member, Member friend) {
        super(String.format("%s와 %s는 동일한 사람입니다.", member.getName(), friend.getName()));
    }
}
