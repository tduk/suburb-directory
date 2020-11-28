package com.tarasduk.auspost.suburb.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.tarasduk.auspost.suburb.model.Error;

@RestControllerAdvice
public class DefaultExceptionHandler {
  @ExceptionHandler(value = {NotFoundException.class})
  protected ResponseEntity<Error> handleConflict(NotFoundException ex) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Error("404", ex.getMessage()));
  }

  @ExceptionHandler(value = {IllegalArgumentException.class})
  protected ResponseEntity<Error> handleConflict(IllegalArgumentException ex) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Error("400", ex.getMessage()));
  }

}
