package com.springboot.product_shop.infra;

import com.springboot.product_shop.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class RestExceptionHandler {

    //For Category class
    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<Object> handleCategoryNotFoundException(CategoryNotFoundException e){
        return handleCustomException(e,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CategoryNameExistsException.class)
    public ResponseEntity<Object> handleCategoryNameExistsException(CategoryNameExistsException e){
        return handleCustomException(e,HttpStatus.BAD_REQUEST);
    }

    //-----
    //For Product class

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<Object> handleProductNotFoundException(ProductNotFoundException e){
        return handleCustomException(e,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ProductNameExistsException.class)
    public ResponseEntity<Object> handleProductNameExistsException(ProductNameExistsException e){
        return handleCustomException(e,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ProductNameNotFoundException.class)
    public ResponseEntity<Object> handleProductNameNotFoundException(ProductNameNotFoundException e){
        return handleCustomException(e,HttpStatus.NOT_FOUND);
    }

    //-----
    //For Role class

    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<Object> handleRoleNotFoundException(RoleNotFoundException e){
        return handleCustomException(e,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RoleEnumNotFoundException.class)
    public ResponseEntity<Object> handleRoleEnumNotFoundException(RoleEnumNotFoundException e){
        return handleCustomException(e,HttpStatus.BAD_REQUEST);
    }

    //-----
    //For UserEntity class

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException e){
        return handleCustomException(e,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserDocumentNotFoundException.class)
    public ResponseEntity<Object> handleUserDocumentNotFoundException(UserDocumentNotFoundException e){
        return handleCustomException(e,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<Object> handleUsernameNotFoundException(UsernameNotFoundException e){
        return handleCustomException(e,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserDocumentExistsException.class)
    public ResponseEntity<Object> handleUserDocumentExistsException(UserDocumentExistsException e){
        return handleCustomException(e,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UsernameExistsException.class)
    public ResponseEntity<Object> handleUsernameExistsException(UsernameExistsException e){
        return handleCustomException(e,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserEmailExistsException.class)
    public ResponseEntity<Object> handleUserEmailExistsException(UserEmailExistsException e){
        return handleCustomException(e,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidRoleException.class)
    public ResponseEntity<Object> handleInvalidRoleException(InvalidRoleException e){
        return handleCustomException(e,HttpStatus.BAD_REQUEST);
    }
    //-----
    //For Order class

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<Object> handleOrderNotFoundException(OrderNotFoundException e){
        return handleCustomException(e,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NotEnoughProductsInStockException.class)
    public ResponseEntity<Object> handleNotEnoughProductsInStockException(NotEnoughProductsInStockException e){
        return handleCustomException(e,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(OwnershipException.class)
    public ResponseEntity<Object> handleOwnershipException(OwnershipException e){
        return handleCustomException(e,HttpStatus.BAD_REQUEST);
    }

    //-----
    //For OrderProduct class

    @ExceptionHandler(OrderProductNotFoundException.class)
    public ResponseEntity<Object> handleOrderProductNotFoundException(OrderProductNotFoundException e){
        return handleCustomException(e,HttpStatus.NOT_FOUND);
    }

    //-----
    private ResponseEntity<Object> handleCustomException(RuntimeException e, HttpStatus httpStatus) {
        Map<String,Object> responseMessage= new LinkedHashMap<>();
        responseMessage.put("message",e.getMessage());
        responseMessage.put("status",httpStatus.value());
        return new ResponseEntity<>(responseMessage, httpStatus);
    }

}
