package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface FriendStorage {

    List<User> getByEmail(String email);

    void addFriend(String emailUser, String emailFriend);

    void deleteFriend(String emailUser, String emailFriend);
}
