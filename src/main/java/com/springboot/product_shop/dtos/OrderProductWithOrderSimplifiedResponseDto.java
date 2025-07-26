package com.springboot.product_shop.dtos;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter @Setter
public class OrderProductWithOrderSimplifiedResponseDto {

    private UUID orderId;

    private OrderSimplifiedResponseDto orderSimplifiedResponseDto;

    private ProductResponseDto productResponseDto;

    private BigDecimal productTotalPrice;

    private Integer productQuantity;

    private BigDecimal productUnitPrice;

}