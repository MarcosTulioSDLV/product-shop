package com.springboot.product_shop.exceptions;

public class UserDocumentExistsException extends RuntimeException{

    public UserDocumentExistsException(){
    }

    public UserDocumentExistsException(String message){
        super(message);
    }

}
