package com.springboot.product_shop.exceptions;

public class CategoryNameExistsException extends RuntimeException{

    public CategoryNameExistsException(){
    }

    public CategoryNameExistsException(String message){
        super(message);
    }

}
