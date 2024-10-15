package ru.yandex.practicum.filmorate.exceptions;

public class IncorrectCountValueException extends RuntimeException {
    public IncorrectCountValueException(final String message) {
        super(message);
    }
}
