package com.springboot.product_shop.exceptions;

public class OrderProductNotFoundException extends RuntimeException{

    public OrderProductNotFoundException(){
    }

    public OrderProductNotFoundException(String message){
        super(message);
    }

}
