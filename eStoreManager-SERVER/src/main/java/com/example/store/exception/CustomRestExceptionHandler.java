package com.example.store.exception;

import javax.validation.ConstraintViolationException;

import com.example.store.payload.common.response.ApiResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomRestExceptionHandler extends ResponseEntityExceptionHandler {

    // BindException: This exception is thrown when fatal binding errors occur.
    // MethodArgumentNotValidException: This exception is thrown when argument annotated with @Valid failed validation
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, 
																HttpHeaders headers, 
																HttpStatus status, 
																WebRequest request) {
        String message = "";
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            message = message + error.getField() + " " + error.getDefaultMessage() + " and ";
        }
        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            message = message + error.getObjectName() + " " + error.getDefaultMessage() + " and ";
        }
        message =  message.substring(0, message.length() - 5);
        return new ResponseEntity<>(new ApiResponse(false, "argument_not_valid", message),
                                    HttpStatus.OK);
    }
    
    // MissingServletRequestPartException: This exception is thrown when when the part of a multipart request not found
    // MissingServletRequestParameterException: This exception is thrown when request missing parameter
    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, 
                                                                        HttpStatus status, WebRequest request) {

        return new ResponseEntity<>(new ApiResponse(false, "parameter_is_missing", "parameter is missing"),
                                HttpStatus.OK);
    }

    // ConstrainViolationException: This exception reports the result of constraint violations
    @ExceptionHandler({ ConstraintViolationException.class })
    public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex, WebRequest request) {
        
        return new ResponseEntity<>(new ApiResponse(false, "constrain_violation", "constrain violation"),
                                    HttpStatus.OK);
    }

    // TypeMismatchException: This exception is thrown when try to set bean property with wrong type
    // MethodArgumentTypeMismatchException: This exception is thrown when method argument is not the expected type
    @ExceptionHandler({ MethodArgumentTypeMismatchException.class })
    public ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex, WebRequest request) {

        return new ResponseEntity<>(new ApiResponse(false, "type_mismatch", ex.getName() + " should be of type " + ex.getRequiredType().getName()),
                                    HttpStatus.OK);
    }

    // HttpMediaTypeNotSupportedException – which occurs when the client send a request with unsupported media type
    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex, 
                                                                    HttpHeaders headers, 
                                                                    HttpStatus status, 
                                                                    WebRequest request) {
        
        return new ResponseEntity<>(new ApiResponse(false, "media_type_not_supported", "media type is not supported"),
                                    HttpStatus.OK);
    }

    //  A fall-back handler – a catch-all type of logic that deals with all other exceptions that don’t have specific handlers
    // @ExceptionHandler({ Exception.class })
    // public ResponseEntity<Object> handleAll(Exception ex, WebRequest request) {
    //     return new ResponseEntity<>(new ApiResponse(false, "error_occured", "error occurred"),
    //                                 HttpStatus.OK);
    // }
    
}