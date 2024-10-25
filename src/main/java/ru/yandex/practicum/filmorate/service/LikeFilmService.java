package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.LikeFilmStorage;
import ru.yandex.practicum.filmorate.exceptions.NotFoundFilmException;
import ru.yandex.practicum.filmorate.exceptions.NotFoundUserException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

@Service
@Slf4j
public class LikeFilmService {

    private final LikeFilmStorage likeFilmStorage;
    private final UserService userService;
    private final FilmService filmService;

    @Autowired
    public LikeFilmService(LikeFilmStorage likeFilmStorage, UserService userService, FilmService filmService) {
        this.likeFilmStorage = likeFilmStorage;
        this.userService = userService;
        this.filmService = filmService;
    }

    public List<Film> get(String email) {
        User user = userService.getByEmail(email);
        if (user == null) {
            throw new NotFoundUserException("NotFoundUser");
        }
        return likeFilmStorage.get(user);
    }

    public List<Film> getTop(int count) {
        return likeFilmStorage.getTop(count);
    }

    public void delete(String email, String name) {
        User user = userService.getByEmail(email);
        Film film = filmService.getByName(name);
        if (film == null) {
            throw new NotFoundFilmException("NotFoundFilm");
        }
        if (user == null) {
            throw new NotFoundUserException("NotFoundUser");
        }
        likeFilmStorage.delete(email, name);
    }

    public void like(String email, String name) {
        User user = userService.getByEmail(email);
        Film film = filmService.getByName(name);
        if (film == null) {
            throw new NotFoundFilmException("NotFoundFilm");
        }
        if (user == null) {
            throw new NotFoundUserException("NotFoundUser");
        }
        likeFilmStorage.like(email, name);
    }
}
