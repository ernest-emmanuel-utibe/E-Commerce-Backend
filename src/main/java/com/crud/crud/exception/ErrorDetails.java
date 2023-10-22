package com.crud.crud.exception;

import java.time.LocalDateTime;
import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class ErrorDetails {
    private LocalDateTime timestamp;
    private String message;
    private String details;
}
