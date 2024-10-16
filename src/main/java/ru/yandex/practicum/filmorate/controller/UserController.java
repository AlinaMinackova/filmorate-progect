package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
//
//        {
//        "email":"m@yandex.ru",
//        "login":"Nik",
//        "name":"Nik",
//        "birthday":"2020-05-15"
//        }
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService; //класс сервиса

    @Autowired // класс UserService зависимость
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> get() {
        return userService.get();
    }

    @GetMapping("/{id}")
    public User getId(@PathVariable String id) {
        return userService.getId(Integer.parseInt(id));
    }

    @PostMapping
    public User create(@Valid @RequestBody User user) throws ParseException {
        return userService.create(user);
    }

    @PutMapping("")
    public User update(@Valid @RequestBody User user) throws ParseException {
        return userService.update(user);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public void addFriend(@PathVariable String id, @PathVariable String friendId){
        userService.addFriends(Integer.parseInt(id), Integer.parseInt(friendId));
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public void deleteFriend(@PathVariable String id, @PathVariable String friendId){
        userService.deleteFriends(Integer.parseInt(id), Integer.parseInt(friendId));
    }

    @GetMapping("/{id}/friends")
    public List<User> getAllFriends(@PathVariable String id){
        return userService.getAllFriends(Integer.parseInt(id));
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public List<User> getCommonFriends(@PathVariable String id, @PathVariable String otherId){
        return userService.getCommonFriends(Integer.parseInt(id), Integer.parseInt(otherId));
    }


}
