package ru.skypro.homework.exception;

public class NoSuchAdException extends RuntimeException {
    public NoSuchAdException() {
    }

    public NoSuchAdException(String message) {
        super(message);
    }
}
