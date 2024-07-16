package com.knowit.commentService.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class CustomizedResponseEntityExceptionHandler
        extends ResponseEntityExceptionHandler {



    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(
            Exception ex,
            WebRequest request
    ) throws Exception{
        return new ResponseEntity<>(
                new CustomizedExceptionResponse(
                        LocalDateTime.now(),
                        ex.getMessage(),
                        request.getDescription(false)
                ),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @ExceptionHandler(CommentNotFoundException.class)
    public final ResponseEntity<Object> handleCommentNotFoundException(
            Exception ex,
            WebRequest request
    ) throws Exception{
        return new ResponseEntity<>(
                new CustomizedExceptionResponse(
                        LocalDateTime.now(),
                        ex.getMessage(),
                        request.getDescription(false)
                ),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(NotCommentOwnerException.class)
    public final ResponseEntity<Object> handleNotCommentOwnerException(
            Exception ex,
            WebRequest request
    ) throws Exception{
        return new ResponseEntity<>(
                new CustomizedExceptionResponse(
                        LocalDateTime.now(),
                        ex.getMessage(),
                        request.getDescription(false)
                ),
                HttpStatus.BAD_REQUEST
        );
    }

}
