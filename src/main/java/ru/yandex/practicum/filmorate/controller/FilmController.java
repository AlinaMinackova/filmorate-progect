package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/films")
public class FilmController {

    private final HashMap<String, Film> films = new HashMap<String, Film>();
    private static int ID = 0;

    @GetMapping
    public List<Film> get() {
        //log.info("Количество залогированных юзеров: {}", users.size());
        return new ArrayList<Film>(films.values());
    }

    public void checkExceptions(Film film) throws ParseException {
        if (film.getName().equals("")){
            log.debug("Пустое имя фильма");
            throw new InvalidFilmNameException("InvalidFilmNameException");
        }
        if (film.getDescription().length() > 200){
            log.debug("Описание фильма > 200 символов");
            throw new MaxLengthDescriptionException("MaxLengthDescriptionExceptionMore200");
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = simpleDateFormat.parse("1895-12-28");
        if (film.getReleaseDate().before(date)){
            log.debug("Фильм не может быть выпущен раньше 28 декабря 1895 года");
            throw new InvalidDateException("InvalidDateException");
        }
        if (film.getDuration() <= 0){
            log.debug("Длительность фильма <= 0");
            throw new InvalidDurationException("InvalidDurationException");
        }
    }

    @PostMapping
    public Film create(@RequestBody Film film) throws ParseException {
        //log.info("Добавлен пост: {}", post);
        checkExceptions(film);
        if (films.containsKey(film.getName())){
            throw new FilmAlreadyExistsException("FilmAlreadyExistsException");
        }
        film.setId(ID++);
        films.put(film.getName(), film);
        log.info("Добавлен новый фильм {}", film);
        return film;
    }

    @PutMapping("")
    public Film update(@RequestBody Film film) throws ParseException {
        checkExceptions(film);
        if (films.containsKey(film.getName())) {
            Film film1 = films.get(film.getName());
            film1.setDescription(film.getDescription());
            film1.setReleaseDate(film.getReleaseDate());
            film1.setDuration(film.getDuration());
            log.info("Обновлен фильм {}", film);
            return films.get(film.getName());
        }
        return create(film);
    }




}
