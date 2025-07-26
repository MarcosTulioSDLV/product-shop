package com.springboot.product_shop.controllers;

import com.springboot.product_shop.dtos.LoginUserRequestDto;
import com.springboot.product_shop.dtos.UserSimplifiedRequestDto;
import com.springboot.product_shop.dtos.UserRequestDto;
import com.springboot.product_shop.dtos.UserResponseDto;
import com.springboot.product_shop.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "Users", description = "Operations related to user management")
@RestController
@RequestMapping(value = "/api/v1/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Get all users", description = "Returns a paginated list of all users. Accessible by ADMIN only.")
    @GetMapping
    public ResponseEntity<Page<UserResponseDto>> getAllUsers(@PageableDefault(size = 10) Pageable pageable){
        return ResponseEntity.ok(userService.getAllUsers(pageable));
    }

    @Operation(summary = "Get the current logged user", description = "Retrieves the current logged user.")
    @GetMapping(value = "/self")
    public ResponseEntity<UserResponseDto> getUserByIdFoSelf(){
        return ResponseEntity.ok(userService.getUserByIdForSelf());
    }

    @Operation(summary = "Get user by ID", description = "Returns the user with the specified ID. Accessible by ADMIN only.")
    @GetMapping(value = "/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable UUID id){
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @Operation(summary = "Get user by document", description = "Returns the user with the specified document. Accessible by ADMIN only.")
    @GetMapping(value = "/by-document")
    public ResponseEntity<UserResponseDto> getUserByDocument(@RequestParam String document){
        return ResponseEntity.ok(userService.getUserByDocument(document));
    }

    @Operation(summary = "Get user by username", description = "Returns the user with the specified username. Accessible by ADMIN only.")
    @GetMapping(value = "/by-username")
    public ResponseEntity<UserResponseDto> getUserByUsername(@RequestParam String username){
        return ResponseEntity.ok(userService.getUserByUsername(username));
    }

    @Operation(summary = "Search users by username", description = "Returns a paginated list of users whose usernames contain the given string. Accessible by ADMIN only.")
    @GetMapping(value = "/by-username-containing")
    public ResponseEntity<Page<UserResponseDto>> getUsersByUsernameContaining(@RequestParam String username,
                                                                              @PageableDefault(size = 10) Pageable pageable){
       return ResponseEntity.ok(userService.getUsersByUsernameContaining(username,pageable));
    }

    @Operation(summary = "Search users by full name", description = "Returns a paginated list of users whose full names contain the given string. Accessible by ADMIN only.")
    @GetMapping(value = "/by-full-name-containing")
    public ResponseEntity<Page<UserResponseDto>> getUsersByFullNameContaining(@RequestParam String fullName,
                                                                              @PageableDefault(size = 10) Pageable pageable){
        return ResponseEntity.ok(userService.getUsersByFullNameContaining(fullName,pageable));
    }

    @Operation(summary = "Login user", description = "Authenticates a user using their credentials and returns a JWT token.")
    @PostMapping(value = "/login")
    public ResponseEntity<String> loginUser(@RequestBody @Valid LoginUserRequestDto loginUserRequestDto){
        //return ResponseEntity.ok("User logged in successfully!");
        return ResponseEntity.ok(userService.loginUser(loginUserRequestDto));
    }

    @Operation(summary = "Register new customer", description = "Allows a user to register themselves as a customer. No authentication required.")
    @PostMapping(value = "/customer/self")
    public ResponseEntity<UserResponseDto> registerUserCustomerForSelf(@RequestBody @Valid UserSimplifiedRequestDto userSimplifiedRequestDto){
        return new ResponseEntity<>(userService.registerUserCustomerForSelf(userSimplifiedRequestDto),HttpStatus.CREATED);
    }

    @Operation(summary = "Register new user", description = "Registers a new user of any role (e.g., ADMIN, CUSTOMER, or MANAGER). Accessible by ADMIN only.")
    @PostMapping
    public ResponseEntity<UserResponseDto> registerUser(@RequestBody @Valid UserRequestDto userRequestDto){
        return new ResponseEntity<>(userService.registerUser(userRequestDto), HttpStatus.CREATED);
    }

    @Operation(summary = "Update self user", description = "Updates the information of the currently authenticated user.")
    @PutMapping(value = "/self")
    public ResponseEntity<UserResponseDto> updateUserForSelf(@RequestBody @Valid UserSimplifiedRequestDto userSimplifiedRequestDto){
        return ResponseEntity.ok(userService.updateUserForSelf(userSimplifiedRequestDto));
    }

    @Operation(summary = "Update user by ID", description = "Updates the user with the specified ID. Accessible by ADMIN only.")
    @PutMapping(value = "/{id}")
    public ResponseEntity<UserResponseDto> updateUser(@PathVariable UUID id,
                                                      @RequestBody @Valid UserRequestDto userRequestDto){
        return ResponseEntity.ok(userService.updateUser(id,userRequestDto));
    }

    /*@DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable UUID id){
        userService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.OK).body("User with id: "+id+" deleted successfully!");
    }*/

}
