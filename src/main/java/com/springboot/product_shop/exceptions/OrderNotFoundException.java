package com.springboot.product_shop.exceptions;

public class OrderNotFoundException extends RuntimeException{

    public OrderNotFoundException(){
    }

    public OrderNotFoundException(String message){
        super(message);
    }

}
