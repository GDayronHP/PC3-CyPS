package com.tecsup.petclinic.exception;

public class VetNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    public VetNotFoundException(String message) {
        super(message);
        // TODO Auto-generated constructor stub
    }
}