package com.springboot.product_shop.mappers;

import com.springboot.product_shop.dtos.*;
import com.springboot.product_shop.models.Order;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderMapper {

    private final ModelMapper modelMapper;
    private final UserMapper userMapper;
    private final OrderProductMapper orderProductMapper;

    @Autowired
    public OrderMapper(ModelMapper modelMapper, UserMapper userMapper, OrderProductMapper orderProductMapper) {
        this.modelMapper = modelMapper;
        this.userMapper = userMapper;
        this.orderProductMapper = orderProductMapper;
    }

    public OrderResponseDto toOrderResponseDto(Order order) {
        OrderResponseDto orderResponseDto = modelMapper.map(order, OrderResponseDto.class);

        orderResponseDto.setUserResponseDto(userMapper.toUserResponseDto(order.getUser()));

        List<OrderProductResponseDto> orderProductResponseDtoList = orderProductMapper.toOrderProductResponseDtoList(order.getOrderProductList());
        orderResponseDto.setOrderProductResponseDtoList(orderProductResponseDtoList);

        return orderResponseDto;
    }

    public OrderSimplifiedResponseDto toOrderSimplifiedResponseDto(Order order) {
        OrderSimplifiedResponseDto orderSimplifiedResponseDto = modelMapper.map(order, OrderSimplifiedResponseDto.class);

        orderSimplifiedResponseDto.setUserResponseDto(userMapper.toUserResponseDto(order.getUser()));

        return orderSimplifiedResponseDto;
    }

    public Order toOrder(OrderRequestDto orderRequestDto) {
        return modelMapper.map(orderRequestDto, Order.class);
    }


}


