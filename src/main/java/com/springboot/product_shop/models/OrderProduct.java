package com.springboot.product_shop.models;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Entity
@Table(name = "TB_ORDER_PRODUCT")
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter @ToString
public class OrderProduct {

    @EmbeddedId
    private OrderProductPK id;

    @ManyToOne
    @JoinColumn(name = "order_id", insertable = false,updatable = false)
    @MapsId(value = "orderId")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id", insertable = false,updatable = false)
    @MapsId(value = "productId")
    private Product product;

    @Column(name = "product_total_price",nullable = false)
    private BigDecimal productTotalPrice;

    @Column(name = "product_quantity",nullable = false)
    private Integer productQuantity;

    public BigDecimal GetProductUnitPrice(){
        if (productQuantity == null || productQuantity == 0) {
            throw new ArithmeticException("Cannot divide by zero or null quantity");
        }
        return productTotalPrice.divide(BigDecimal.valueOf(productQuantity),2, RoundingMode.HALF_UP);
    }

}
