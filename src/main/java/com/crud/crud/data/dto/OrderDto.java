package com.crud.crud.data.dto;

import javax.persistence.Embedded;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.CreditCardNumber;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class OrderDto {
    @NotNull
    @Embedded
    private CreditCard cardNumber;
    @NotNull
    private String addressType;
}
