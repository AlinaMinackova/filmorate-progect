package ru.yandex.practicum.filmorate.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;
import ru.yandex.practicum.filmorate.exceptions.*;

import java.util.Map;

@RestControllerAdvice("ru.yandex.practicum.filmorate.controller")
public class ErrorHandler {

    @ExceptionHandler({
            FilmAlreadyExistsException.class,
            IncorrectCountValueException.class,
            InvalidDateException.class,
            InvalidDurationException.class,
            InvalidEmailException.class,
            InvalidFilmNameException.class,
            InvalidLoginException.class,
            MaxLengthDescriptionException.class,
            UserAlreadyExistsException.class
    })
    //перехватывает ошибку и отправляет сообщение понятное для пользователя
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleExceptionBadRequest(RuntimeException exception) {
        return Map.of("error", exception.getMessage());
    }

    @ExceptionHandler({
            NotFoundFilmException.class,
            NotFoundUserException.class
    })
    //перехватывает ошибку и отправляет сообщение понятное для пользователя
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleExceptionNotFound(RuntimeException exception) {
        return Map.of("error", exception.getMessage());
    }

    @ExceptionHandler()
    //перехватывает ошибку и отправляет сообщение понятное для пользователя
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, String> handleExceptionNotFoundInternalServerException(RuntimeException exception) {
        return Map.of("error", exception.getMessage());
    }
}
