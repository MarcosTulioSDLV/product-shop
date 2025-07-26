package com.springboot.product_shop.controllers;

import com.springboot.product_shop.dtos.CreateOrderRequestDto;
import com.springboot.product_shop.dtos.OrderResponseDto;
import com.springboot.product_shop.services.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "Orders", description = "Operations related to order management")
@RestController
@RequestMapping(value = "/api/v1/orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @Operation(summary = "Get own orders", description = "Retrieves a paginated list of orders made by the currently authenticated customer. Accessible only to users with the CUSTOMER role.")
    @GetMapping(value = "/self")
    public ResponseEntity<Page<OrderResponseDto>> getAllOrdersForSelf(@PageableDefault(size = 10) Pageable pageable){
        return ResponseEntity.ok(orderService.getAllOrdersForSelf(pageable));
    }

    @Operation(summary = "Get all orders", description = "Retrieves a paginated list of all orders in the system. Accessible by MANAGER and ADMIN only.")
    @GetMapping
    public ResponseEntity<Page<OrderResponseDto>> getAllOrders(@PageableDefault(size = 10) Pageable pageable){
        return ResponseEntity.ok(orderService.getAllOrders(pageable));
    }

    @Operation(summary = "Get order by ID (self)", description = "Retrieves the details of a specific order made by the currently authenticated customer. Accessible only to users with the CUSTOMER role.")
    @GetMapping(value = "/self/{id}")
    public ResponseEntity<OrderResponseDto> getOrderByIdForSelf(@PathVariable UUID id){
        return ResponseEntity.ok(orderService.getOrderByIdForSelf(id));
    }

    @Operation(summary = "Get order by ID", description = "Retrieves the details of a specific order by its ID. Accessible by MANAGER and ADMIN only.")
    @GetMapping(value = "/{id}")
    public ResponseEntity<OrderResponseDto> getOrderById(@PathVariable UUID id){
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    @Operation(summary = "Get orders by user ID", description = "Retrieves a paginated list of orders made by a specific user. Accessible by MANAGER and ADMIN only.")
    @GetMapping(value = "/by-user/{userId}")
    public ResponseEntity<Page<OrderResponseDto>> getOrdersByUserId(@PathVariable UUID userId,
                                                                    @PageableDefault(size = 10) Pageable pageable){
        return ResponseEntity.ok(orderService.getOrdersByUserId(userId,pageable));
    }

    @Operation(summary = "Create new order", description = "Places a new order. Accessible only to users with the CUSTOMER role.")
    @PostMapping
    public ResponseEntity<OrderResponseDto> addOrder(@RequestBody @Valid CreateOrderRequestDto createOrderRequestDto){
        return new ResponseEntity<>(orderService.addOrder(createOrderRequestDto), HttpStatus.CREATED);
    }

}
