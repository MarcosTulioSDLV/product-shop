package com.springboot.product_shop.exceptions;

public class RoleEnumNotFoundException extends RuntimeException{

    public RoleEnumNotFoundException(){
    }

    public RoleEnumNotFoundException(String message){
        super(message);
    }

}
