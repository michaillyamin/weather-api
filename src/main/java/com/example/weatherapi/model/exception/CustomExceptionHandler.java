package com.example.weatherapi.model.exception;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(WeatherDataNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ExceptionBody> handleWeatherDataNotFound(WeatherDataNotFoundException e) {
        ExceptionBody exceptionBody = new ExceptionBody(e.getMessage());
        log.error(e.getMessage(), e);

        return new ResponseEntity<>(exceptionBody, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CannotCalculateAverageWeatherDataException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ExceptionBody> handleCannotCalculateAverageWeatherData(CannotCalculateAverageWeatherDataException e) {
        ExceptionBody exceptionBody = new ExceptionBody(e.getMessage());
        log.error(e.getMessage(), e);

        return new ResponseEntity<>(exceptionBody, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ExceptionBody> handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
        ExceptionBody exceptionBody = new ExceptionBody("Validation failed");
        log.error(e.getMessage(), e);

        List<FieldError> errors = e.getBindingResult().getFieldErrors();

        exceptionBody.setErrors(errors.stream()
                .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage)));

        return new ResponseEntity<>(exceptionBody, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ExceptionBody> handleConstraintViolation(ConstraintViolationException e) {
        ExceptionBody exceptionBody = new ExceptionBody("Validation failed");
        log.error(e.getMessage(), e);

        exceptionBody.setErrors(e.getConstraintViolations().stream()
                .collect(Collectors.toMap(
                        violation -> violation.getPropertyPath().toString(),
                        ConstraintViolation::getMessage
                )));

        return new ResponseEntity<>(exceptionBody, HttpStatus.BAD_REQUEST);
    }

}
