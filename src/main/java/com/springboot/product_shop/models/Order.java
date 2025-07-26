package com.springboot.product_shop.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.springboot.product_shop.enums.OrderStatusEnum;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "TB_ORDER")
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter @ToString(exclude = "orderProductList")
//@EqualsAndHashCode(exclude = "orderProductList")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "payment_method",nullable = false)
    private String paymentMethod;

    @Column(name = "purchase_total_price",nullable = false)
    private BigDecimal purchaseTotalPrice;

    @Column(nullable = false)
    private String address;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "order_status_enum", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private OrderStatusEnum orderStatusEnum;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @JsonIgnore
    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL/*, orphanRemoval = true*/)
    List<OrderProduct> orderProductList= new ArrayList<>();
    
}
