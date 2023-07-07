package ru.skypro.homework.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(NoSuchAdException.class)
    public void handleNoSuchAdException(NoSuchAdException e) {
        System.out.println("Such ad is not found");
    }

    @org.springframework.web.bind.annotation.ExceptionHandler
    public void handleNoSuchCommentException(NoSuchCommentException e) {
        System.out.println("Such comment is not found");
    }
}
