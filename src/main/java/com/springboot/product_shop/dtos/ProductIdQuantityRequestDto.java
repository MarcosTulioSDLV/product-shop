package com.springboot.product_shop.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter @Setter
public class ProductIdQuantityRequestDto {

    @NotNull
    private UUID productId;

    @NotNull @PositiveOrZero
    private Integer productQuantity;

}
