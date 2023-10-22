package com.crud.crud.data.dto;

import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class SessionDto {
    private String token;

    private String message;
}
