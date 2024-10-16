package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.text.ParseException;
import java.util.*;

@Service
@Slf4j
public class FilmService {

    private final FilmStorage filmStorage;

    @Autowired
    public FilmService(FilmStorage filmStorage) {
        this.filmStorage = filmStorage;
    }

    public List<Film> getTopFilms(int count){
        if (count <= 0){
            throw new IncorrectCountValueException("IncorrectCountValueException");
        }
        return filmStorage.get().stream()
                .sorted(Film::compareTo)
                // сортировка по возрастанию
                //можно переопределить метод compareTo в классе Film
                //и просто вызвать sorted(Film::compareTo)
                //.sorted(Collections.reverseOrder())
                //переворачиваем
                .limit(count)
                .toList();

    }

    public void addLike(int id, int userId){
        log.info("Добавлен лайк к фильму {} пользователем {}", id, userId);
        Film film1 = getId(id);
        Set<Integer> set = film1.getLikes();
        set.add(userId);
    }

    public void deleteLike(int id, int userId){
        log.info("{} удалил лайк фильму {}", userId, id);
        Film film1 = getId(id);
        Set<Integer> set = film1.getLikes();
        try {
            set.remove(userId);
        } catch (Exception e){
            throw new NotFoundUserException("NotFoundUserException");
        }


    }

    public List<Film> get() {
        return new ArrayList<Film>(filmStorage.get());
    }

    public Film getId(int id) {
        List<Integer> filmsId = filmStorage.get().stream().map(x -> x.getId()).toList();
        if (id <= 0 || !filmsId.contains(id)){
            throw new NotFoundFilmException("NotFoundFilmException");
        }
        return filmStorage.get().stream().filter(x -> x.getId() == id).toList().get(0);
    }

    public Film create(Film film) throws ParseException {
        return filmStorage.create(film);
    }

    public Film update(Film film) throws ParseException {
        return filmStorage.update(film);
    }


}
