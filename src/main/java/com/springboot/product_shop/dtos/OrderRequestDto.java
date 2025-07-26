package com.springboot.product_shop.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OrderRequestDto {

    @NotBlank
    private String paymentMethod;

    @NotBlank
    private String address;

    @NotBlank
    private String phoneNumber;

}
