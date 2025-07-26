package com.springboot.product_shop.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "TB_USER")
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter @ToString(exclude = "orders")
//@EqualsAndHashCode(exclude = "orders")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false,unique = true)
    private String document;

    @Column(nullable = false,unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(nullable = false,unique = true)
    private String email;

    @ManyToMany
    @JoinTable(name = "TB_USER_ROLE",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")})
    List<Role> roles= new ArrayList<>();

    //NEW (si es necesario?, dejar o borrar?)
    //Note: If you use this field, ignore it in the update methods.
    //@JsonIgnore
    //@OneToMany(mappedBy = "user",cascade = CascadeType.ALL/*, orphanRemoval = true*/)
    //List<Order> orders= new ArrayList<>();

}
