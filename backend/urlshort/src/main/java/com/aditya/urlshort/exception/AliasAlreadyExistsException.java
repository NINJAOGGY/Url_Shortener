package com.aditya.urlshort.exception;

public class AliasAlreadyExistsException
        extends RuntimeException {

    public AliasAlreadyExistsException() {

        super(
            "Alias already exists"
        );
    }
}