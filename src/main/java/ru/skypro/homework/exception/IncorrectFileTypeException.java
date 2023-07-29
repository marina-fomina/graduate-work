package ru.skypro.homework.exception;

public class IncorrectFileTypeException extends RuntimeException {
    public IncorrectFileTypeException() {
    }

    public IncorrectFileTypeException(String message) {
        super(message);
    }
}
