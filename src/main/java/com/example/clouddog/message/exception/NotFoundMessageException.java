package com.example.clouddog.message.exception;

public class NotFoundMessageException extends RuntimeException {
    public NotFoundMessageException(final String message) {
        super(message);
    }

    public NotFoundMessageException() {
        this("메세지를 찾을 수 없습니다.");
    }
}