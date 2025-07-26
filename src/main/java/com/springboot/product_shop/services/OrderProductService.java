package com.springboot.product_shop.services;

import com.springboot.product_shop.dtos.OrderProductWithOrderSimplifiedResponseDto;
import com.springboot.product_shop.models.OrderProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface OrderProductService {

    Page<OrderProductWithOrderSimplifiedResponseDto> getAllOrderProducts(Pageable pageable);

    OrderProductWithOrderSimplifiedResponseDto getOrderProductById(UUID orderId, UUID productId);

    void addOrderProduct(OrderProduct orderProduct);
}
