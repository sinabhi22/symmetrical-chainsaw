package com.nokia.exception;
public class StackIsEmptyException extends RuntimeException {

    public StackIsEmptyException() {
        super("Stack is empty" );
    }

}