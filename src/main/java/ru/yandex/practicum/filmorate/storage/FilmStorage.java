package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;

import java.text.ParseException;
import java.util.List;

public interface FilmStorage {

    public List<Film> get();

    public Film create(Film film)  throws ParseException;

    public Film update(Film film) throws ParseException;
}
