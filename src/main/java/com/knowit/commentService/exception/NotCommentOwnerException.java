package com.knowit.commentService.exception;

public class NotCommentOwnerException extends RuntimeException{

    public NotCommentOwnerException(String message) {
        super(message);
    }
}
