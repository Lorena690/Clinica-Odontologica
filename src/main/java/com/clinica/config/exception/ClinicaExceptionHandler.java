package com.clinica.config.exception;

import com.clinica.model.dto.ErrorResponse;
import lombok.extern.log4j.Log4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.concurrent.ConcurrentHashMap;

@Log4j
@ControllerAdvice
public class ClinicaExceptionHandler extends ResponseEntityExceptionHandler {

    private static final ConcurrentHashMap<String, Integer> EXCEPTIONS = new ConcurrentHashMap<>();

    public static final String INTERNAL_ERROR = "Ocurrio un error inesperado";
    public static final String DATE_ERROR = "Ocurrio un error convirtiendo la fecha, recuerda usar el formato yyyy-MM-dd";
    public static final String MESSAGE_TEMPLATE = "%s : %s";


    public ClinicaExceptionHandler() {
        EXCEPTIONS.put(ServiceException.class.getSimpleName(), HttpStatus.BAD_REQUEST.value());
        EXCEPTIONS.put(HttpMessageNotReadableException.class.getSimpleName(),HttpStatus.BAD_REQUEST.value());
        EXCEPTIONS.put(MethodArgumentNotValidException.class.getSimpleName(),HttpStatus.BAD_REQUEST.value());
        EXCEPTIONS.put(ClinicaNotFoundException.class.getSimpleName(),HttpStatus.NOT_FOUND.value());
        EXCEPTIONS.put(HttpMessageNotReadableException.class.getSimpleName(),HttpStatus.BAD_REQUEST.value());
    }

    @Override
    public ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return clinicaExceptionHandler(ex);
    }

    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return clinicaExceptionHandler(ex);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception ex) {
        return clinicaExceptionHandler(ex);
    }

    private ResponseEntity<Object> clinicaExceptionHandler(Exception ex){
        log.error("Ocurrió un error: " , ex);
        ResponseEntity<Object> response;

        String exceptionName = ex.getClass().getSimpleName();
        String message = getErrorMessage(ex);
        Integer code = EXCEPTIONS.get(exceptionName);

        if (code != null) {
            ErrorResponse error = new ErrorResponse(message);
            response = new ResponseEntity<>(error, HttpStatus.valueOf(code));
        } else {
            ErrorResponse error = new ErrorResponse(INTERNAL_ERROR);
            response = new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    private String getErrorMessage(Exception ex) {
        if (ex instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException exception = (MethodArgumentNotValidException) ex;
            FieldError fieldError = exception.getBindingResult().getFieldError();
            if (!ObjectUtils.isEmpty(fieldError)) {
                return String.format(MESSAGE_TEMPLATE, fieldError.getField(), fieldError.getDefaultMessage());
            } else {
                return INTERNAL_ERROR;
            }
        } else if (ex instanceof ClinicaNotFoundException) {
            return ex.getMessage();
        } else if (ex instanceof ServiceException) {
            return ex.getMessage();
        } else if (ex instanceof HttpMessageNotReadableException) {
            return DATE_ERROR;
        } else {
            return INTERNAL_ERROR;
        }
    }
}
