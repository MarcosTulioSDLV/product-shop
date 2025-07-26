package com.springboot.product_shop.dtos;

import com.springboot.product_shop.enums.OrderStatusEnum;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter @Setter
public class OrderResponseDto {

    private UUID id;

    private String paymentMethod;

    private BigDecimal purchaseTotalPrice;

    private String address;

    private String phoneNumber;

    private OrderStatusEnum orderStatusEnum;

    private LocalDateTime createdAt;

    private UserResponseDto userResponseDto;

    List<OrderProductResponseDto> orderProductResponseDtoList= new ArrayList<>();

}
