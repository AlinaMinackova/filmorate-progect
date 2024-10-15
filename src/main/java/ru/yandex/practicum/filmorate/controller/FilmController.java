package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;

import java.text.ParseException;
import java.util.List;

//        {
//        "name":"Water",
//        "description":"Water",
//        "releaseDate":"2020-02-15",
//        "duration":"60"
//        }

@RestController
@RequestMapping("/films")
public class FilmController {

    private final FilmService filmService; //класс сервиса

    @Autowired // класс FilmService зависимость
    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping
    public List<Film> get() {
        return filmService.get();
    }

    @GetMapping("/{id}")
    public Film getId(@PathVariable String id) {
        return filmService.getId(Integer.parseInt(id));
    }

    @PutMapping("/{id}/like/{userId}")
    public void addLike(@PathVariable String id, @PathVariable String userId) {
        filmService.addLike(Integer.parseInt(id), Integer.parseInt(userId));
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void deleteLike(@PathVariable String id, @PathVariable String userId) {
        filmService.deleteLike(Integer.parseInt(id), Integer.parseInt(userId));
    }

    @GetMapping("/popular")
    public List<Film> getTopFilms(@RequestParam(value="count", defaultValue="10") String count) {
        return filmService.getTopFilms(Integer.parseInt(count));
    }

    @PostMapping
    public Film create(@Valid @RequestBody Film film) throws ParseException {
        return filmService.create(film);
    }

    @PutMapping("")
    public Film update(@Valid @RequestBody Film film) throws ParseException {
        return filmService.update(film);
    }

}
