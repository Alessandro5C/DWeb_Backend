package com.neoadventura.handler;

import com.neoadventura.exceptions.FormatException;
import com.neoadventura.exceptions.InternalServerErrorException;
import com.neoadventura.exceptions.NotFoundException;
import com.neoadventura.exceptions.UnauthorizedException;
import com.neoadventura.responses.ExceptionResponse;
import com.neoadventura.responses.NeoAdventuraResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
@RestController
public class ServiceExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleResourceNotFound(NotFoundException exception, WebRequest request ){
        ExceptionResponse response = new ExceptionResponse(exception.getMessage(), request.getDescription(false), HttpStatus.NOT_FOUND, LocalDateTime.now());
        return new ResponseEntity<>(response, response.getStatus());
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<Object> handleUnauthorizedException(UnauthorizedException exception, WebRequest request ){
        ExceptionResponse response = new ExceptionResponse(exception.getMessage(), request.getDescription(false), HttpStatus.UNAUTHORIZED, LocalDateTime.now());
        return new ResponseEntity<>(response, response.getStatus());
    }

    @ExceptionHandler(FormatException.class)
    public ResponseEntity<Object> handleFormatException(FormatException exception, WebRequest request ){
        ExceptionResponse response = new ExceptionResponse(exception.getMessage(), request.getDescription(false), HttpStatus.NOT_MODIFIED, LocalDateTime.now());
        return new ResponseEntity<>(response, response.getStatus());
    }

    @ExceptionHandler(InternalServerErrorException.class)
    public ResponseEntity<Object> handleInternalServerErrorException(InternalServerErrorException exception, WebRequest request ){
        ExceptionResponse response = new ExceptionResponse(exception.getMessage(), request.getDescription(false), HttpStatus.INTERNAL_SERVER_ERROR, LocalDateTime.now());
        return new ResponseEntity<>(response, response.getStatus());
    }


}
