package com.nit.exception;

public class SupplierNotFoundException extends RuntimeException{

    public SupplierNotFoundException() {

    }
    public SupplierNotFoundException(String message) {
        super(message);
    }
}
