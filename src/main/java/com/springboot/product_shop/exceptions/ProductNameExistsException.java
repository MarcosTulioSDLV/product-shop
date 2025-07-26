package com.springboot.product_shop.exceptions;

public class ProductNameExistsException extends RuntimeException{

    public ProductNameExistsException(){
    }

    public ProductNameExistsException(String message){
        super(message);
    }

}
