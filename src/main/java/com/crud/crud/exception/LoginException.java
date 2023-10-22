package com.crud.crud.exception;

import lombok.*;

@NoArgsConstructor
public class LoginException extends RuntimeException{
    public LoginException(String message) {
        super(message);
    }
}
