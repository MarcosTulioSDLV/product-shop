package com.springboot.product_shop.exceptions;

public class OwnershipException extends RuntimeException{

    public OwnershipException(){
    }

    public OwnershipException(String message){
        super(message);
    }

}
