package com.springboot.product_shop.repositories;

import com.springboot.product_shop.enums.RoleEnum;
import com.springboot.product_shop.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {

    Optional<Role> findByRoleEnum(RoleEnum roleEnum);

}
