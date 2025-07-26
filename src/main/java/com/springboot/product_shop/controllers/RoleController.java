package com.springboot.product_shop.controllers;

import com.springboot.product_shop.dtos.RoleResponseDto;
import com.springboot.product_shop.services.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Tag(name = "Roles", description = "Operations related to role management")
@RestController
@RequestMapping(value = "/api/v1/roles")
public class RoleController {

    private final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @Operation(summary = "Get all roles", description = "Retrieves a paginated list of all available roles in the system. Accessible by ADMIN only.")
    @GetMapping
    public ResponseEntity<Page<RoleResponseDto>> getAllRoles(@PageableDefault(size = 10) Pageable pageable){
        return ResponseEntity.ok(roleService.getAllRoles(pageable));
    }

    @Operation(summary = "Get role by ID", description = "Retrieves the role with the specified ID. Accessible by ADMIN only.")
    @GetMapping(value = "/{id}")
    public ResponseEntity<RoleResponseDto> getRoleById(@PathVariable UUID id){
        return ResponseEntity.ok(roleService.getRoleById(id));
    }

}


