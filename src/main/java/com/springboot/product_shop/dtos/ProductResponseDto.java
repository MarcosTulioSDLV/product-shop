package com.springboot.product_shop.dtos;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter @Setter
public class ProductResponseDto {

    private UUID id;

    private String name;

    private String description;

    private String imageUrl;

    private BigDecimal price;

    private Integer stockQuantity;

    private CategoryResponseDto categoryResponseDto;

}
