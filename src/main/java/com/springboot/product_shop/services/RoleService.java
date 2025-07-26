package com.springboot.product_shop.services;

import com.springboot.product_shop.dtos.RoleResponseDto;
import com.springboot.product_shop.enums.RoleEnum;
import com.springboot.product_shop.models.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface RoleService {

    Page<RoleResponseDto> getAllRoles(Pageable pageable);

    RoleResponseDto getRoleById(UUID id);

    List<Role> findRoleByIds(List<UUID> roleIds);

    Role findRoleByRoleEnum(RoleEnum roleEnum);
}
