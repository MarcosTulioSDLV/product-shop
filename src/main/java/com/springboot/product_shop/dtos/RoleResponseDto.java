package com.springboot.product_shop.dtos;

import com.springboot.product_shop.enums.RoleEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter @Setter
@Builder
@AllArgsConstructor
public class RoleResponseDto {

    private UUID id;

    private RoleEnum roleEnum;

    public RoleResponseDto(){
    }

}
