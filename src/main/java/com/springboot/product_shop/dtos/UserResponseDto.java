package com.springboot.product_shop.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter @Setter
public class UserResponseDto {

    private UUID id;

    private String document;

    private String username;

    private String fullName;

    private String email;

    private List<RoleResponseDto> roleResponseDtoList= new ArrayList<>();

}
