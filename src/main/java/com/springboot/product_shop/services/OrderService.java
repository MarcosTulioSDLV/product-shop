package com.springboot.product_shop.services;

import com.springboot.product_shop.dtos.CreateOrderRequestDto;
import com.springboot.product_shop.dtos.OrderResponseDto;
import com.springboot.product_shop.models.Order;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface OrderService {

    Page<OrderResponseDto> getAllOrdersForSelf(Pageable pageable);

    Page<OrderResponseDto> getAllOrders(Pageable pageable);

    OrderResponseDto getOrderByIdForSelf(UUID id);

    Order findOrderById(UUID id);

    OrderResponseDto getOrderById(UUID id);

    Page<OrderResponseDto> getOrdersByUserId(UUID userId,Pageable pageable);

    @Transactional
    OrderResponseDto addOrder(CreateOrderRequestDto createOrderRequestDto);

}
