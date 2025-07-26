package com.springboot.product_shop.services;

import com.springboot.product_shop.dtos.LoginUserRequestDto;
import com.springboot.product_shop.dtos.UserSimplifiedRequestDto;
import com.springboot.product_shop.dtos.UserRequestDto;
import com.springboot.product_shop.dtos.UserResponseDto;
import com.springboot.product_shop.models.UserEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface UserService {

    Page<UserResponseDto> getAllUsers(Pageable pageable);

    UserResponseDto getUserByIdForSelf();

    UserResponseDto getUserById(UUID id);

    UserEntity findUserById(UUID id);

    UserResponseDto getUserByDocument(String document);

    UserResponseDto getUserByUsername(String username);

    Page<UserResponseDto> getUsersByUsernameContaining(String username, Pageable pageable);

    Page<UserResponseDto> getUsersByFullNameContaining(String fullName, Pageable pageable);

    String loginUser(LoginUserRequestDto loginUserRequestDto);

    @Transactional
    UserResponseDto registerUserCustomerForSelf(UserSimplifiedRequestDto userSimplifiedRequestDto);

    @Transactional
    UserResponseDto registerUser(UserRequestDto userRequestDto);

    @Transactional
    UserResponseDto updateUserForSelf(UserSimplifiedRequestDto userSimplifiedRequestDto);

    @Transactional
    UserResponseDto updateUser(UUID id, UserRequestDto userRequestDto);

    /*@Transactional
    void deleteUser(UUID id);*/
}
