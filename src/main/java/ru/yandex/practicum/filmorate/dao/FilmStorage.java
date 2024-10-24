package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.model.Film;

import java.text.ParseException;
import java.util.List;

public interface FilmStorage {

    List<Film> get();

    void delete(String name);

    Film getByName(String name);

    Film create(Film film)  throws ParseException;

    Film update(Film film) throws ParseException;
}
