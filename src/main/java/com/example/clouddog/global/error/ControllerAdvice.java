package com.example.clouddog.global.error;

import com.example.clouddog.auth.exception.MemberNotFoundException;
import com.example.clouddog.board.exception.NotFoundBoardException;
import com.example.clouddog.board.exception.NotFoundMemberException;
import com.example.clouddog.comment.exception.NotFoundCommentException;
import com.example.clouddog.global.error.dto.ErrorResponse;
import com.example.clouddog.image.exception.NotFoundImageException;
import com.example.clouddog.member.exception.EqualMemberAndFriendException;
import com.example.clouddog.member.exception.ExistsFriendShipException;
import com.example.clouddog.message.exception.NotFoundMessageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler({
            ExistsFriendShipException.class,
            EqualMemberAndFriendException.class
    })
    public ResponseEntity<ErrorResponse> handleInvalidData(RuntimeException e) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({
            MemberNotFoundException.class,
            NotFoundImageException.class,
            NotFoundBoardException.class,
            NotFoundMemberException.class,
            NotFoundCommentException.class,
            NotFoundMessageException.class
    })
    public ResponseEntity<ErrorResponse> handleNotFoundDate(RuntimeException e) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), e.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

}
