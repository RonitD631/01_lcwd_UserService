package com.user.service.UserService.exception;

import com.user.service.UserService.utill.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> handleResourceNotFoundException(ResourceNotFoundException ex){
       String mesage= ex.getMessage();
       ApiResponse response=ApiResponse.builder().message(mesage).success(true).status(HttpStatus.NOT_FOUND).build();

       return  new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
    }

}
