package ru.yandex.practicum.filmorate.exceptions;

public class NotFoundUserException extends RuntimeException {
    public NotFoundUserException(final String message) {
        super(message);
    }
}