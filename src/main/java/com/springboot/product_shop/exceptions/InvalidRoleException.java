package com.springboot.product_shop.exceptions;

public class InvalidRoleException extends RuntimeException{

    public InvalidRoleException(){
    }

    public InvalidRoleException(String message){
        super(message);
    }

}
