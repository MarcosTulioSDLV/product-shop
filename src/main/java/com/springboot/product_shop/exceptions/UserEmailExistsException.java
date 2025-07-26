package com.springboot.product_shop.exceptions;

public class UserEmailExistsException extends RuntimeException{

    public UserEmailExistsException(){
    }

    public UserEmailExistsException(String message){
        super(message);
    }

}
