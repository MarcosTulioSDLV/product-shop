package com.springboot.product_shop.exceptions;


public class ProductNameNotFoundException extends RuntimeException{

    public ProductNameNotFoundException(){
    }

    public ProductNameNotFoundException(String message){
        super(message);
    }

}
