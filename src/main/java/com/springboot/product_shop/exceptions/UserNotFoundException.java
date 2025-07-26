package com.springboot.product_shop.exceptions;

public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException(){
    }

    public UserNotFoundException(String message){
        super(message);
    }

}
