package com.msoft.gateways.exception;

public class AddressExistsException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public AddressExistsException(String message) {
        super(message);
    }
    
}
