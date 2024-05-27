package com.geeks.pixion.exceptions;

import com.geeks.pixion.payloads.ApiResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({HttpRequestMethodNotSupportedException.class, ConstraintViolationException.class,InvalidFieldValue.class, EmptyFieldException.class})
    public ResponseEntity<ApiResponse> exceptionHandler(Exception exception){
        String message=exception.getMessage();
        ApiResponse apiResponse=new ApiResponse(message,false, HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(apiResponse,HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException resourceNotFoundException){
        String message=resourceNotFoundException.getMessage();
        ApiResponse apiResponse=new ApiResponse(message,false, HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(apiResponse,HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<ApiResponse> alreadyExistsExceptionHandler(AlreadyExistsException alreadyExistsException){
        String message=alreadyExistsException.getMessage();
        ApiResponse apiResponse=new ApiResponse(message,false, HttpStatus.CONFLICT.value());
        return new ResponseEntity<>(apiResponse,HttpStatus.CONFLICT);
    }
    @ExceptionHandler(InvalidThrowException.class)
    public ResponseEntity<ApiResponse> invalidThrowExceptionHandler(InvalidThrowException invalidThrowException){
        String message=invalidThrowException.getMessage();
        ApiResponse apiResponse=new ApiResponse(message,false, HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ResponseEntity<>(apiResponse,HttpStatus.INTERNAL_SERVER_ERROR);
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
}
