package ru.yandex.practicum.filmorate.exceptions;

public class UserAlreadyExistsException extends RuntimeException{

    public UserAlreadyExistsException(final String message) {
        super(message);
    }
}
