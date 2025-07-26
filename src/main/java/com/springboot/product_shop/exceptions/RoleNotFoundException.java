package com.springboot.product_shop.exceptions;

public class RoleNotFoundException extends RuntimeException{

    public RoleNotFoundException(){
    }

    public RoleNotFoundException(String message){
        super(message);
    }

}
