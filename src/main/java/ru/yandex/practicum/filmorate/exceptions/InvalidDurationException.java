package ru.yandex.practicum.filmorate.exceptions;

public class InvalidDurationException extends RuntimeException{
    public InvalidDurationException(final String message) {
        super(message);
    }
}
