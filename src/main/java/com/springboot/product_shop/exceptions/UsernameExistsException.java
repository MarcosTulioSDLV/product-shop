package com.springboot.product_shop.exceptions;

public class UsernameExistsException extends RuntimeException{

    public UsernameExistsException(){
    }

    public UsernameExistsException(String message){
        super(message);
    }

}
