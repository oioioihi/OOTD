package com.oioioihi.ootd.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ApiResponse<T> {

    private static final String SUCCESS_STATUS = "200";
    private static final String FAIL_STATUS = "4xx";
    private static final String ERROR_STATUS = "5xx";

    private HttpStatus status;
    private String message;
    private T data;


    /*
     200대 에러
    */
    public static <T> ApiResponse<T> createSuccess(T data) {
        return new ApiResponse<>(HttpStatus.OK, "", data);
    }

    public static ApiResponse<?> createSuccessWithNoContent() {
        return new ApiResponse<>(HttpStatus.OK, "", null);
    }

    /*
       400대 에러
    */
    public static ApiResponse<?> createFail(BindingResult bindingResult) {
        Map<String, String> errors = new HashMap<>();

        List<ObjectError> allErrors = bindingResult.getAllErrors();
        for (ObjectError error : allErrors) {
            if (error instanceof FieldError) {
                errors.put(((FieldError) error).getField(), error.getDefaultMessage());
            } else {
                errors.put(error.getObjectName(), error.getDefaultMessage());
            }
        }
        return new ApiResponse<>(HttpStatus.BAD_REQUEST, null, errors);
    }

    public static ApiResponse<?> createClientError(String message) {
        return new ApiResponse<>(HttpStatus.BAD_REQUEST, message, null);
    }

    public static ApiResponse<?> createNotFound(String message) {
        return new ApiResponse<>(HttpStatus.NOT_FOUND, message, null);
    }


    /*
       500대 에러
    */
    public static ApiResponse<?> createServerError(String message) {
        return new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR, message, null);
    }


}
