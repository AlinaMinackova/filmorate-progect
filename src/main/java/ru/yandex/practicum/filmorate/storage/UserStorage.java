package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.text.ParseException;
import java.util.List;

public interface UserStorage {

    public List<User> get();

    public User create(User user) throws ParseException;

    public User update(User user) throws ParseException;
}
