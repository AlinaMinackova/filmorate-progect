package ru.yandex.practicum.filmorate.exceptions;

public class NotFoundFilmException extends RuntimeException {
    public NotFoundFilmException(final String message) {
        super(message);
    }
}
