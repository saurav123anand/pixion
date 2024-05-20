package com.geeks.pixion.exceptions;

import com.geeks.pixion.payloads.ApiResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException resourceNotFoundException){
        String message=resourceNotFoundException.getMessage();
        ApiResponse apiResponse=new ApiResponse(message,false, HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(apiResponse,HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException methodArgumentNotValidException){
        Map<String,String> response=new HashMap<>();
        methodArgumentNotValidException.getBindingResult().getAllErrors().forEach(error->{
            String field = ((FieldError) (error)).getField();
            String defaultMessage = error.getDefaultMessage();
            response.put(field,defaultMessage);
        });
        return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);

    }
    @ExceptionHandler({HttpRequestMethodNotSupportedException.class, ConstraintViolationException.class,Exception.class})
    public ResponseEntity<ApiResponse> exceptionHandler(Exception exception){
        String message=exception.getMessage();
        ApiResponse apiResponse=new ApiResponse(message,false, HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(apiResponse,HttpStatus.BAD_REQUEST);
    }
}
