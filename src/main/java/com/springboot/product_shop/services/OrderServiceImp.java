package com.springboot.product_shop.services;

import com.springboot.product_shop.dtos.CreateOrderRequestDto;
import com.springboot.product_shop.dtos.OrderRequestDto;
import com.springboot.product_shop.dtos.OrderResponseDto;
import com.springboot.product_shop.dtos.ProductIdQuantityRequestDto;
import com.springboot.product_shop.enums.OrderStatusEnum;
import com.springboot.product_shop.exceptions.NotEnoughProductsInStockException;
import com.springboot.product_shop.exceptions.OrderNotFoundException;
import com.springboot.product_shop.exceptions.OwnershipException;
import com.springboot.product_shop.mappers.OrderMapper;
import com.springboot.product_shop.models.*;
import com.springboot.product_shop.repositories.OrderRepository;
import com.springboot.product_shop.security.CustomUserDetails;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class OrderServiceImp implements OrderService{

    private final OrderRepository orderRepository;

    private final OrderMapper orderMapper;

    private final UserService userService;

    private final ProductService productService;

    @Autowired
    public OrderServiceImp(OrderRepository orderRepository, OrderMapper orderMapper, UserService userService, ProductService productService) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.userService = userService;
        this.productService = productService;
    }

    @Override
    public Page<OrderResponseDto> getAllOrdersForSelf(Pageable pageable) {
        return orderRepository.findAllByUser(getCurrentLoggedUser(),pageable).map(orderMapper::toOrderResponseDto);
    }

    @Override
    public Page<OrderResponseDto> getAllOrders(Pageable pageable) {
        return orderRepository.findAll(pageable).map(orderMapper::toOrderResponseDto);
    }

    @Override
    public OrderResponseDto getOrderByIdForSelf(UUID id) {
        Order order= findOrderById(id);
        validateOrderOwnership(order);
        return orderMapper.toOrderResponseDto(order);
    }

    public Order findOrderById(UUID id) {
        return orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException("Order with id: " + id + " not found!"));
    }

    //Note: Validate if the order belongs to the current logged-in user
    private void validateOrderOwnership(Order order) {
        UUID currentLoggedUserId= getCurrentLoggedUser().getId();
        if(!order.getUser().getId().equals(currentLoggedUserId))
            throw new OwnershipException("Recipe does not belong to the current user!");
    }

    private UserEntity getCurrentLoggedUser(){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || !(authentication.getPrincipal() instanceof UserDetails userDetails)) {
            System.out.println("Unauthorized access!");
            throw new AccessDeniedException("Unauthorized access!");
        }
        if(!(userDetails instanceof CustomUserDetails customUserDetails)){
            System.out.println("UserDetails is not an instance of CustomUserDetails!");
            throw new ClassCastException("UserDetails is not an instance of CustomUserDetails!");
        }
        return customUserDetails.getUser();
    }

    @Override
    public OrderResponseDto getOrderById(UUID id) {
        Order order= findOrderById(id);
        return orderMapper.toOrderResponseDto(order);
    }

    @Override
    public Page<OrderResponseDto> getOrdersByUserId(UUID userId,Pageable pageable) {
        UserEntity user= userService.findUserById(userId);
        return orderRepository.findAllByUser(user,pageable).map(orderMapper::toOrderResponseDto);
    }

    @Override
    @Transactional
    public OrderResponseDto addOrder(CreateOrderRequestDto createOrderRequestDto) {
        OrderRequestDto orderRequestDto= createOrderRequestDto.getOrderRequestDto();
        Order order= orderMapper.toOrder(orderRequestDto);
        //Note: Old version method // UUID userId= orderRequestDto.getUserId();
        UUID userId= getCurrentLoggedUser().getId();
        order.setUser(userService.findUserById(userId));
        order.setCreatedAt(LocalDateTime.now());
        order.setOrderStatusEnum(OrderStatusEnum.PENDING);
        order.setPurchaseTotalPrice(BigDecimal.ZERO);

        List<ProductIdQuantityRequestDto> productIdQuantityList= createOrderRequestDto.getProductIdQuantityRequestDtoList();
        BigDecimal purchaseTotalPrice = BigDecimal.ZERO;
        for(ProductIdQuantityRequestDto productIdQuantity: productIdQuantityList){
            Integer quantity= productIdQuantity.getProductQuantity();
            Product product= productService.findProductById(productIdQuantity.getProductId());
            if(quantity>product.getStockQuantity()){
                throw new NotEnoughProductsInStockException("Not enough stock for product: '" + product.getName() +
                        "'. Requested: " + quantity + ", Available: " + product.getStockQuantity());
            }
            BigDecimal productTotalPrice= product.getPrice().multiply(BigDecimal.valueOf(quantity));

            //method 1
            OrderProductPK orderProductPK= new OrderProductPK(order.getId(),product.getId());
            OrderProduct orderProduct= new OrderProduct(orderProductPK,order,product,productTotalPrice,quantity);
            //Or method 2
            //OrderProduct orderProduct= new OrderProduct(new OrderProductPK(),order,product,productTotalPrice,quantity);

            order.getOrderProductList().add(orderProduct);
            //orderProductService.addOrderProduct(orderProduct);//OLD

            purchaseTotalPrice= purchaseTotalPrice.add(productTotalPrice);
            product.setStockQuantity(product.getStockQuantity()-quantity);//Update stockQuantity
        }

        order.setPurchaseTotalPrice(purchaseTotalPrice);//Update purchaseTotalPrice
        orderRepository.save(order);
        return orderMapper.toOrderResponseDto(order);
    }


}
