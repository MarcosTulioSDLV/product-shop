package com.springboot.product_shop.dtos;

import jakarta.validation.Valid;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class CreateOrderRequestDto {

    @Valid
    private OrderRequestDto orderRequestDto;

    @Valid
    private List<ProductIdQuantityRequestDto> productIdQuantityRequestDtoList;

}
