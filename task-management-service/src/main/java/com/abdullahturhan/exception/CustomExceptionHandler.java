package com.abdullahturhan.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(TaskNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String,String> handleTaskNotFoundEx(TaskNotFoundException e){
        Map<String,String> errorMap = new HashMap<>();
        errorMap.put("Message" , e.getMessage());
        return errorMap;
    }
    @ExceptionHandler(InvalidTaskDateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String,String> handleInvalidTaskDateException(InvalidTaskDateException e){
        Map<String,String> errorMap = new HashMap<>();
        errorMap.put("Message" , e.getMessage());
        return errorMap;
    }
    @ExceptionHandler(UserNotFoundOrUserRoleIsNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String,String> handleUserNotFoundOrUserRoleIsNotValidException(UserNotFoundOrUserRoleIsNotValidException e){
        Map<String,String> errorMap = new HashMap<>();
        errorMap.put("Message" , e.getMessage());
        return errorMap;
    }
}
