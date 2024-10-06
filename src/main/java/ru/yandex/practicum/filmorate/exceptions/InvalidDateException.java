package ru.yandex.practicum.filmorate.exceptions;

public class InvalidDateException extends RuntimeException{
    public InvalidDateException(final String message) {
        super(message);
    }
}
