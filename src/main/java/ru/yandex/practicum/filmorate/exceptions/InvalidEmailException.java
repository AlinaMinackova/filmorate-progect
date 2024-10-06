package ru.yandex.practicum.filmorate.exceptions;

public class InvalidEmailException extends RuntimeException{
    public InvalidEmailException(final String message) {
        super(message);
    }
}
