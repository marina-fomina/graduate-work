package ru.skypro.homework.exception;

public class NoSuchCommentException extends RuntimeException {
    public NoSuchCommentException() {
    }

    public NoSuchCommentException(String message) {
        super(message);
    }
}
