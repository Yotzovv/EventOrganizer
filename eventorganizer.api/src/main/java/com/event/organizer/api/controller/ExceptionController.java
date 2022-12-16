package com.event.organizer.api.controller;

import com.event.organizer.api.exception.EventOrganizerException;
import org.springdoc.api.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.ServletException;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(value = {EventOrganizerException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorMessage badRequest(final EventOrganizerException ex) {
        final String message = ex.getMessage();
        return new ErrorMessage(message);
    }

    @ExceptionHandler(value = {ServletException.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    public ErrorMessage forbidden(final ServletException ex) {
        final String message = ex.getMessage();
        return new ErrorMessage(message);
    }

    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorMessage exception(final Exception ex) {
        final String message = ex.getMessage();
        return new ErrorMessage(message);
    }
}
