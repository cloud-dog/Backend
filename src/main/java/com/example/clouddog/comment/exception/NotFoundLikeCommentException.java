package com.example.clouddog.comment.exception;

public class NotFoundLikeCommentException extends RuntimeException{
    public NotFoundLikeCommentException(String message) {
        super(message);
    }
    public NotFoundLikeCommentException() {
        this("좋아요가 존재하지 않아 취소할 수 없습니다.");
    }
}
