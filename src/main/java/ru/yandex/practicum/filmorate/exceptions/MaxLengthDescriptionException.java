package ru.yandex.practicum.filmorate.exceptions;

public class MaxLengthDescriptionException extends RuntimeException {
    public MaxLengthDescriptionException(final String message) {
        super(message);
    }
}
