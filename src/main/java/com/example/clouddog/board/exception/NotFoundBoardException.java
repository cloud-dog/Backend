package com.example.clouddog.board.exception;

public class NotFoundBoardException extends RuntimeException {
    public NotFoundBoardException(final String message) {
        super(message);
    }

    public NotFoundBoardException() {
        this("게시글을 찾을 수 없습니다.");
    }
}