package ru.yandex.practicum.filmorate.exceptions;

public class InvalidLoginException extends RuntimeException{
    public InvalidLoginException(final String message) {
        super(message);
    }
}
