package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.model.User;

import java.text.ParseException;
import java.util.List;

public interface UserStorage {

    List<User> get();

    User create(User user) throws ParseException;

    User update(User user) throws ParseException;

    User getByEmail(String email);

    void delete(String email);


}
