package com.springboot.product_shop.exceptions;

public class NotEnoughProductsInStockException extends RuntimeException{

    public NotEnoughProductsInStockException(){
    }

    public NotEnoughProductsInStockException(String message){
        super(message);
    }

}
