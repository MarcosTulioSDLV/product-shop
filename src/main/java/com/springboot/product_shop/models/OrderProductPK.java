package com.springboot.product_shop.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@AllArgsConstructor @NoArgsConstructor
@Getter @Setter @ToString
@EqualsAndHashCode
@Embeddable
public class OrderProductPK implements Serializable {

    @Serial
    private static final long serialVersionUID=1;

    @Column(name = "order_id")
    private UUID orderId;

    @Column(name = "product_id")
    private UUID productId;

}
