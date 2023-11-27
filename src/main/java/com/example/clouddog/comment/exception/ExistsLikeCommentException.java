package com.example.clouddog.comment.exception;

public class ExistsLikeCommentException extends RuntimeException{
    public ExistsLikeCommentException(String message) {
        super(message);
    }

    public ExistsLikeCommentException() {
        this("이미 좋아요 누른 댓글입니다.");
    }
}
