package com.springboot.product_shop.controllers;

import com.springboot.product_shop.dtos.OrderProductWithOrderSimplifiedResponseDto;
import com.springboot.product_shop.services.OrderProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Tag(name = "Order Products", description = "Operations for managing the relationship between orders and products")
@RestController
@RequestMapping(value = "/api/v1/order-products")
public class OrderProductController {

    private final OrderProductService orderProductService;

    @Autowired
    public OrderProductController(OrderProductService orderProductService) {
        this.orderProductService = orderProductService;
    }

    @Operation(summary = "Get all order-product relationships", description = "Retrieves a paginated list of all order-product entries. Accessible only to users with ADMIN or MANAGER roles.")
    @GetMapping
    public ResponseEntity<Page<OrderProductWithOrderSimplifiedResponseDto>> getAllOrderProducts(@PageableDefault(size = 10) Pageable pageable){
        return ResponseEntity.ok(orderProductService.getAllOrderProducts(pageable));
    }

    @Operation(summary = "Get order-product by order ID and product ID", description = "Retrieves a specific order-product relationship by order ID and product ID. Accessible only to users with ADMIN or MANAGER roles.")
    @GetMapping(value = "/by-id")
    public ResponseEntity<OrderProductWithOrderSimplifiedResponseDto> getOrderProductById(@RequestParam UUID orderId,
                                                                                          @RequestParam UUID productId){
        return ResponseEntity.ok(orderProductService.getOrderProductById(orderId,productId));
    }

}
