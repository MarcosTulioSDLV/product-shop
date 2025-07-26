package com.springboot.product_shop.models;

import com.springboot.product_shop.enums.RoleEnum;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "TB_ROLE")
@AllArgsConstructor @NoArgsConstructor
@Getter @Setter @ToString
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false,unique = true)
    @Enumerated(EnumType.STRING)
    private RoleEnum roleEnum;

}
