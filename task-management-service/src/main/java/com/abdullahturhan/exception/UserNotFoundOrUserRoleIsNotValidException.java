package com.abdullahturhan.exception;

public class UserNotFoundOrUserRoleIsNotValidException extends Exception {
    public UserNotFoundOrUserRoleIsNotValidException(String message){
        super(message);
    }
}
