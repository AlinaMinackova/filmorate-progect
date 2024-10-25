package ru.yandex.practicum.filmorate.controller;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.LikeFilmService;

import java.util.List;

@RestController
@RequestMapping("/favourite")
public class LikeFilmController {

    private final LikeFilmService likeFilmService;

    public LikeFilmController(LikeFilmService likeFilmService) {
        this.likeFilmService = likeFilmService;
    }

    @GetMapping("/{user}")
    public List<Film> get(@PathVariable String user) {
        return likeFilmService.get(user);
    }

    @GetMapping("")
    public List<Film> getTop(@RequestParam(value = "count", defaultValue = "2") int count) {
        return likeFilmService.getTop(count);
    }

    @PostMapping("/{userId}/like/{id}")
    public void like(@PathVariable String userId, @PathVariable String id) {
        likeFilmService.like(userId, id);
    }

    @DeleteMapping("/{userId}/like/{id}")
    public void delete(@PathVariable String userId, @PathVariable String id) {
        likeFilmService.delete(userId, id);
    }
}
