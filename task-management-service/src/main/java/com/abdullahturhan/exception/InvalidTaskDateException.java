package com.abdullahturhan.exception;

public class InvalidTaskDateException extends IllegalArgumentException{
    public InvalidTaskDateException(String message){
        super(message);
    }
}
