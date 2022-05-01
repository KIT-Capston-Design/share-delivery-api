package com.kitcd.share_delivery_api.controller.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Iterator;
import java.util.ListIterator;


// @ControllerAdvice : 전역의 컨트롤러에서 동일하게 발생할 수 있는 익셉션 처리 //패키지 경로 입력하여 범위제한 가능
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = ConstraintViolationException.class)
    protected ResponseEntity<?> handleException(ConstraintViolationException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(getResultMessage(exception.getConstraintViolations().iterator()));
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    protected ResponseEntity<?> handleException(MethodArgumentNotValidException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(getResultMessage(exception.getBindingResult().getFieldErrors().listIterator()));
    }


    protected String getResultMessage(final Iterator<ConstraintViolation<?>> violationIterator) {

        final StringBuilder resultMessageBuilder = new StringBuilder();

        while (violationIterator.hasNext()) {
            final ConstraintViolation<?> constraintViolation = violationIterator.next();
            resultMessageBuilder
                    .append("['")
                    .append(getPropertyName(constraintViolation.getPropertyPath().toString())) // 유효성 검사가 실패한 속성
                    .append("' is '")
                    .append(constraintViolation.getInvalidValue()) // 유효하지 않은 값
                    .append("'. ")
                    .append(constraintViolation.getMessage()) // 유효성 검사 실패 시 메시지
                    .append("]");

            if (violationIterator.hasNext()) {
                resultMessageBuilder.append(", ");
            }
        }

        return resultMessageBuilder.toString();
    }

    protected String getPropertyName(String propertyPath) {
        return propertyPath.substring(propertyPath.lastIndexOf('.') + 1); // 전체 속성 경로에서 속성 이름만 가져온다.
    }

    protected String getResultMessage(final ListIterator<FieldError> violationIterator) {
        final StringBuilder resultMessageBuilder = new StringBuilder();

        while (violationIterator.hasNext()) {
            final FieldError fieldError = violationIterator.next();
            resultMessageBuilder
                    .append("['")
                    .append(fieldError.getField()) // 유효성 검사가 실패한 속성
                    .append("' is '")
                    .append(fieldError.getRejectedValue()) // 유효하지 않은 값
                    .append("'. ")
                    .append(fieldError.getDefaultMessage()) // 유효성 검사 실패 시 메시지
                    .append("]");

            if (violationIterator.hasNext()) {
                resultMessageBuilder.append(", ");
            }
        }

        return resultMessageBuilder.toString();
    }

}
