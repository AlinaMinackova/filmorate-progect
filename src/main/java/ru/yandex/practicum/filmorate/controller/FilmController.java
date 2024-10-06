package ru.yandex.practicum.filmorate.controller;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.*;
import ru.yandex.practicum.filmorate.model.Film;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/films")
public class FilmController {

    private HashMap<String, Film> films = new HashMap<String, Film>();
    private static int ID = 0;

    @GetMapping
    public List<Film> get() {
        //log.info("Количество залогированных юзеров: {}", users.size());
        return (List<Film>) films.values();
    }

    public void checkExceptions(Film film) throws ParseException {
        if (film.getName().equals("")){
            throw new InvalidFilmNameException("InvalidFilmNameException");
        }
        if (film.getDescription().length() > 200){
            throw new MaxLengthDescriptionException("MaxLengthDescriptionExceptionMore200");
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-mm-dd");
        Date date = simpleDateFormat.parse("1895-12-28");
        if (film.getReleaseDate().before(date)){
            throw new InvalidDateException("InvalidDateException");
        }
        if (film.getDuration() <= 0){
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
        return film;
    }

    @PutMapping("")
    public Film update(@RequestBody Film film) throws ParseException {
        checkExceptions(film);
        if (!films.containsKey(film.getName())) {
            create(film);
        } else {
            Film film1 = films.get(film.getName());
            film1.setDescription(film.getDescription());
            film1.setReleaseDate(film.getReleaseDate());
            film1.setDuration(film.getDuration());
        }
        return films.get(film.getName());
    }




}
