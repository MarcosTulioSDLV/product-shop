package com.springboot.product_shop.exceptions;

public class UserDocumentNotFoundException extends RuntimeException{

    public UserDocumentNotFoundException(){
    }

    public UserDocumentNotFoundException(String message){
        super(message);
    }

}
