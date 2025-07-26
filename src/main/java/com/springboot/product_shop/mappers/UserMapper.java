package com.springboot.product_shop.mappers;

import com.springboot.product_shop.dtos.RoleResponseDto;
import com.springboot.product_shop.dtos.UserSimplifiedRequestDto;
import com.springboot.product_shop.dtos.UserRequestDto;
import com.springboot.product_shop.dtos.UserResponseDto;
import com.springboot.product_shop.models.UserEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class UserMapper {

    private final ModelMapper modelMapper;

    private final RoleMapper roleMapper;

    @Autowired
    public UserMapper(ModelMapper modelMapper, RoleMapper roleMapper) {
        this.modelMapper = modelMapper;
        this.roleMapper = roleMapper;
    }

    public UserResponseDto toUserResponseDto(UserEntity user){
        UserResponseDto userResponseDto= modelMapper.map(user,UserResponseDto.class);

        List<RoleResponseDto> roleResponseDtoList= user.getRoles().stream()
                .map(roleMapper::toRoleResponseDto)
                .collect(toList());

        userResponseDto.setRoleResponseDtoList(roleResponseDtoList);
        return userResponseDto;
    }

    public UserEntity toUser(UserRequestDto userRequestDto){
        return modelMapper.map(userRequestDto,UserEntity.class);
    }

    public UserEntity toUser(UserSimplifiedRequestDto userForSelfRequestDto){
        return modelMapper.map(userForSelfRequestDto,UserEntity.class);
    }


}
