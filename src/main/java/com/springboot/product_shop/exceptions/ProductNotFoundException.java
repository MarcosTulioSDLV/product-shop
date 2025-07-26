package com.springboot.product_shop.exceptions;

public class ProductNotFoundException extends RuntimeException{

    public ProductNotFoundException(){
    }

    public ProductNotFoundException(String message){
        super(message);
    }

}
