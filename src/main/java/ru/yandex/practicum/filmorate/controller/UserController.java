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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final InMemoryUserStorage userStorage; //класс сервиса

    @Autowired // класс InMemoryUserStorage зависимость
    public UserController(InMemoryUserStorage userStorage) {
        this.userStorage = userStorage;
    }

    @GetMapping
    public List<User> get() {
        return userStorage.get();
    }

    @PostMapping
    public User create(@Valid @RequestBody User user) throws ParseException {
        return userStorage.create(user);
    }

    @PutMapping("")
    public User update(@Valid @RequestBody User user) throws ParseException {
        return userStorage.update(user);
    }

}
