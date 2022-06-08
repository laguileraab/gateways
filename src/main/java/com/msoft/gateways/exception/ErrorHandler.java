package com.msoft.gateways.exception;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RestControllerAdvice
public class ErrorHandler extends ResponseEntityExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<MessageResponseError> noSuchElementExceptionsHandler(Exception ex, WebRequest request) {
        MessageResponseError errorDetails = new MessageResponseError
                (HttpStatus.NOT_FOUND, ex.getMessage(), "Error_in_" + request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({
        MethodArgumentTypeMismatchException.class,
        DataIntegrityViolationException.class,
        SQLIntegrityConstraintViolationException.class,
        IllegalArgumentException.class
    })
    public ResponseEntity<MessageResponseError> fieldsNotValidsExceptionsHandler(Exception ex, WebRequest request) {
        MessageResponseError errorDetails = new MessageResponseError
                (HttpStatus.BAD_REQUEST,
                        "Fields are not valid",
                        "Error_in_" + request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }
    
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                  HttpHeaders headers, HttpStatus status, WebRequest request) {
           String errorMessage = ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage();
        //    List<String> validationList = ex.getBindingResult().getFieldErrors().stream().map(fieldError->fieldError.getDefaultMessage()).collect(Collectors.toList());
           //ApiErrorVO apiErrorVO = new ApiErrorVO(errorMessage);
           //apiErrorVO.setErrorList(validationList);
           MessageResponseError errorDetails = new MessageResponseError
                (HttpStatus.BAD_REQUEST,
                errorMessage,
                        "Error_in_" + request.getDescription(false));
           return new ResponseEntity<>(errorDetails, status);
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(FoundElementException.class)
    public ResponseEntity<MessageResponseError> foundElementExceptionsHandler(Exception ex, WebRequest request) {
        MessageResponseError errorDetails = new MessageResponseError
                (HttpStatus.CONFLICT,
                        ex.getMessage(),
                        "Error_in_" + request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<MessageResponseError> globalExceptionsHandler(Exception ex, WebRequest request) {
        MessageResponseError errorDetails = new MessageResponseError
                (HttpStatus.INTERNAL_SERVER_ERROR,
                        "There's been an error",
                        "Error_in_" + request.getDescription(false));
        ErrorHandler.log.error(HttpStatus.INTERNAL_SERVER_ERROR + " " + ex.getMessage()
                + "Stacktrace" + ExceptionUtils.getStackTrace(ex));
        System.out.println(ExceptionUtils.getStackTrace(ex));
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}