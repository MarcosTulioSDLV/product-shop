package com.springboot.product_shop.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "TB_PRODUCT")
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter @ToString()//@ToString(exclude = "orderProductList")
//@EqualsAndHashCode(exclude = "orderProductList")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false,unique = true)
    private String name;

    @Column(nullable = false,columnDefinition = "TEXT")
    private String description;

    @Column(name = "image_url",nullable = false)
    private String imageUrl;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(name = "stock_quantity",nullable = false)
    private Integer stockQuantity;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    //@JsonIgnore
    //@OneToMany(mappedBy = "product",cascade = CascadeType.ALL/*, orphanRemoval = true*/)
    //List<OrderProduct> orderProductList= new ArrayList<>();

}
