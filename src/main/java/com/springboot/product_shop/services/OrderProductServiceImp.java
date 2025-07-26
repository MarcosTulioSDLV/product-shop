package com.springboot.product_shop.services;

import com.springboot.product_shop.dtos.OrderProductWithOrderSimplifiedResponseDto;
import com.springboot.product_shop.dtos.OrderSimplifiedResponseDto;
import com.springboot.product_shop.exceptions.OrderProductNotFoundException;
import com.springboot.product_shop.mappers.OrderMapper;
import com.springboot.product_shop.mappers.OrderProductMapper;
import com.springboot.product_shop.models.Order;
import com.springboot.product_shop.models.OrderProduct;
import com.springboot.product_shop.models.OrderProductPK;
import com.springboot.product_shop.repositories.OrderProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OrderProductServiceImp implements OrderProductService{

    private final OrderProductRepository orderProductRepository;

    private final OrderProductMapper orderProductMapper;

    private final OrderService orderService;

    private final OrderMapper orderMapper;

    @Autowired
    public OrderProductServiceImp(OrderProductRepository orderProductRepository, OrderProductMapper orderProductMapper, OrderService orderService, OrderMapper orderMapper) {
        this.orderProductRepository = orderProductRepository;
        this.orderProductMapper = orderProductMapper;
        this.orderService = orderService;
        this.orderMapper = orderMapper;
    }

    @Override
    public Page<OrderProductWithOrderSimplifiedResponseDto> getAllOrderProducts(Pageable pageable) {
        return orderProductRepository.findAll(pageable).map(orderProductMapper::toOrderProductWithOrderSimplifiedResponseDto)
                // Note: This map sets the missing orderSimplifiedResponseDto field because it cannot be mapped directly in the mapper
                // to avoid a potential circular reference between OrderProductMapper and OrderMapper DTOs.
                .map(orderProductWithOrderSimplifiedResponseDto -> {
                    UUID orderId= orderProductWithOrderSimplifiedResponseDto.getOrderId();
                    Order order= orderService.findOrderById(orderId);
                    OrderSimplifiedResponseDto orderSimplifiedResponseDto = orderMapper.toOrderSimplifiedResponseDto(order);
                    orderProductWithOrderSimplifiedResponseDto.setOrderSimplifiedResponseDto(orderSimplifiedResponseDto);
                    return orderProductWithOrderSimplifiedResponseDto;
                });
    }

    @Override
    public OrderProductWithOrderSimplifiedResponseDto getOrderProductById(UUID orderId, UUID productId) {
        OrderProductPK orderProductPK= new OrderProductPK(orderId,productId);
        OrderProduct orderProduct= findOrderProductById(orderProductPK);
        OrderProductWithOrderSimplifiedResponseDto orderProductWithOrderSimplifiedResponseDto= orderProductMapper.toOrderProductWithOrderSimplifiedResponseDto(orderProduct);
        // Note: We set the missing orderSimplifiedResponseDto field here because it cannot be mapped directly in the mapper
        // to avoid potential circular references between OrderProductMapper and OrderMapper DTOs.
        Order order= orderService.findOrderById(orderId);
        OrderSimplifiedResponseDto orderSimplifiedResponseDto= orderMapper.toOrderSimplifiedResponseDto(order);
        orderProductWithOrderSimplifiedResponseDto.setOrderSimplifiedResponseDto(orderSimplifiedResponseDto);
        //---
        return orderProductWithOrderSimplifiedResponseDto;
    }

    private OrderProduct findOrderProductById(OrderProductPK orderProductPK) {
        return orderProductRepository.findById(orderProductPK).orElseThrow(() -> new OrderProductNotFoundException("OrderProduct with id: " + orderProductPK + " not found exception!"));
    }

    @Transactional
    public void addOrderProduct(OrderProduct orderProduct){
        orderProductRepository.save(orderProduct);
    }

}
