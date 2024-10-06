package ru.yandex.practicum.filmorate.exceptions;

public class InvalidFilmNameException extends RuntimeException{

    public InvalidFilmNameException(final String message) {
        super(message);
    }
}
