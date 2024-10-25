package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.dao.FilmStorage;
import ru.yandex.practicum.filmorate.storage.FilmDbStorage;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.*;

@Service
@Slf4j
public class FilmService {

    private final FilmStorage filmStorage;

    @Autowired
    public FilmService(FilmDbStorage filmStorage) {
        this.filmStorage = filmStorage;
    }

    public List<Film> get() {
        return new ArrayList<Film>(filmStorage.get());
    }

    public Film getByName(String name) {
        Film film = filmStorage.getByName(name);
        if (film == null) {
            log.debug("фильм не найден");
            throw new NotFoundFilmException("NotFoundFilm");
        }
        return film;
    }

    public Film create(Film film) throws ParseException {
        checkExceptions(film);
        Film film1 = filmStorage.getByName(film.getName());
        if (film1 != null) {
            log.debug("фильм уже существует");
            throw new FilmAlreadyExistsException("FilmAlreadyExists");
        }
        return filmStorage.create(film);
    }

    public void delete(String name) {
        filmStorage.delete(name);
    }

    public Film update(Film film) throws ParseException {
        checkExceptions(film);
        Film film1 = getByName(film.getName());
        if (film1 == null) {
            log.debug("фильм не найден");
            throw new NotFoundFilmException("NotFoundFilm");
        }
        return filmStorage.update(film);
    }

    public void checkExceptions(Film film) throws ParseException {
        if (film.getName().equals("")) {
            log.debug("Пустое имя фильма");
            throw new InvalidFilmNameException("InvalidFilmNameException");
        }
        if (film.getDescription().length() > 200) {
            log.debug("Описание фильма > 200 символов");
            throw new MaxLengthDescriptionException("MaxLengthDescriptionExceptionMore200");
        }
        LocalDate date = LocalDate.parse("1895-12-28");
        if (film.getReleaseDate().isBefore(date)) {
            log.debug("Фильм не может быть выпущен раньше 28 декабря 1895 года");
            throw new InvalidDateException("InvalidDateException");
        }
        if (film.getDuration() <= 0) {
            log.debug("Длительность фильма <= 0");
            throw new InvalidDurationException("InvalidDurationException");
        }
    }
}
