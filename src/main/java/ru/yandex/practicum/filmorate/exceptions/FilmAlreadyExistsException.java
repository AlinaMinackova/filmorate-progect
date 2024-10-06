package ru.yandex.practicum.filmorate.exceptions;

public class FilmAlreadyExistsException extends RuntimeException{

    public FilmAlreadyExistsException(final String message) {
        super(message);
    }
}
