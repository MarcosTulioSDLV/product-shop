package com.springboot.product_shop.dtos;

import com.springboot.product_shop.models.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;


@Getter @Setter
public class ProductRequestDto {

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @NotBlank
    private String imageUrl;

    @NotNull @PositiveOrZero
    private BigDecimal price;

    @NotNull @PositiveOrZero
    private Integer stockQuantity;

    @NotNull
    private UUID categoryId;

}
