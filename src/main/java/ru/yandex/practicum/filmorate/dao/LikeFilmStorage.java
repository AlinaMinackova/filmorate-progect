package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface LikeFilmStorage {

    List<Film> get(User user);

    List<Film> getTop(int count);

    void delete(String email, String name);

    void like(String email, String name);
}
