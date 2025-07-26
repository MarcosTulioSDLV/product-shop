package com.springboot.product_shop.exceptions;

public class CategoryNotFoundException extends RuntimeException{

    public CategoryNotFoundException(){
    }

    public CategoryNotFoundException(String message){
        super(message);
    }

}
