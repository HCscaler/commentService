package com.knowit.commentService.exception;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CustomizedExceptionResponse {
    private LocalDateTime timestamp;
    private String message;
    private String description;
}
