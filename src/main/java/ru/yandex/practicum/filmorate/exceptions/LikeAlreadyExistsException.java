package ru.yandex.practicum.filmorate.exceptions;

public class LikeAlreadyExistsException extends RuntimeException {
    public LikeAlreadyExistsException(final String message) {
        super(message);
    }
}
