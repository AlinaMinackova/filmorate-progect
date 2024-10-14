package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/films")
public class FilmController {

    private final InMemoryFilmStorage filmStorage; //класс сервиса

    @Autowired // класс InMemoryFilmStorage зависимость
    public FilmController(InMemoryFilmStorage filmStorage) {
        this.filmStorage = filmStorage;
    }

    @GetMapping
    public List<Film> get() {
        return filmStorage.get();
    }

    @PostMapping
    public Film create(@Valid @RequestBody Film film) throws ParseException {
        return filmStorage.create(film);
    }

    @PutMapping("")
    public Film update(@Valid @RequestBody Film film) throws ParseException {
        return filmStorage.update(film);
    }

}
