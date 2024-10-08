package com.oioioihi.ootd.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.oioioihi.util.Messages.BAD_REQUEST;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleNotFoundException(RuntimeException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.createNotFound(exception.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleAllUncaughtException(Exception exception) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ApiResponse.createServerError(exception.getMessage()));
    }

    @ExceptionHandler(value = {ProductAlreadyExistException.class,
            BrandAlreadyExistException.class,
            CategoryAlreadyExistException.class})
    public ResponseEntity<ApiResponse<?>> handleAlreadyExistException(Exception exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.createClientError(exception.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> handleValidationException(BindingResult bindingResult) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.createFail(bindingResult));
    }
    @ExceptionHandler(value = {IllegalArgumentException.class,
            HttpMessageNotReadableException.class})
    public ResponseEntity<ApiResponse<?>> handleParsingException(Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ApiResponse.createClientError(BAD_REQUEST));
    }
}
