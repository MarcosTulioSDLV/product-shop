package com.springboot.product_shop.services;

import com.springboot.product_shop.dtos.RoleResponseDto;
import com.springboot.product_shop.enums.RoleEnum;
import com.springboot.product_shop.exceptions.RoleEnumNotFoundException;
import com.springboot.product_shop.exceptions.RoleNotFoundException;
import com.springboot.product_shop.mappers.RoleMapper;
import com.springboot.product_shop.models.Role;
import com.springboot.product_shop.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class RoleServiceImp implements RoleService{

    private final RoleRepository roleRepository;

    private final RoleMapper roleMapper;

    @Autowired
    public RoleServiceImp(RoleRepository roleRepository, RoleMapper roleMapper) {
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
    }

    @Override
    public Page<RoleResponseDto> getAllRoles(Pageable pageable) {
        return roleRepository.findAll(pageable).map(roleMapper::toRoleResponseDto);
    }

    @Override
    public RoleResponseDto getRoleById(UUID id) {
        Role role= findRoleById(id);
        return roleMapper.toRoleResponseDto(role);
    }

    private Role findRoleById(UUID id) {
        return roleRepository.findById(id).orElseThrow(() -> new RoleNotFoundException("Role with id: " + id + " not found!"));
    }

    public List<Role> findRoleByIds(List<UUID> roleIds){
        return roleRepository.findAllById(roleIds);
    }

    public Role findRoleByRoleEnum(RoleEnum roleEnum){
        return roleRepository.findByRoleEnum(roleEnum).orElseThrow(()-> new RoleEnumNotFoundException("RoleEnum: "+roleEnum.toString()+" not found!"));
    }

}
