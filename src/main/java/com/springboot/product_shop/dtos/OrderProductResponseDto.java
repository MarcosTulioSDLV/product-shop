package com.springboot.product_shop.dtos;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

// Note: This ResponseDto does not contain any field related to Order.
@Getter @Setter
public class OrderProductResponseDto {

    private UUID orderId;

    private ProductResponseDto productResponseDto;

    private BigDecimal productTotalPrice;

    private Integer productQuantity;

    private BigDecimal productUnitPrice;

}
