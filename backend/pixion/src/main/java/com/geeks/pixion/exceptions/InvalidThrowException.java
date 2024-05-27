package com.geeks.pixion.exceptions;

public class InvalidThrowException extends Exception {
    public InvalidThrowException(String invalidUrlPath) {
        super(invalidUrlPath);
    }
}
