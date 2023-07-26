package ru.skypro.homework.exception;

public class IllegalPasswordLengthException extends RuntimeException {
    public IllegalPasswordLengthException() {
    }

    public IllegalPasswordLengthException(String message) {
        super(message);
    }
}
