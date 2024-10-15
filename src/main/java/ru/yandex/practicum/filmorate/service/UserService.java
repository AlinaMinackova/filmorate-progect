package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class UserService {

    private final UserStorage userStorage;

    @Autowired
    public UserService(InMemoryUserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public List<User> getAllFriends(int userId){
        User user1 = getId(userId);
        Set<Integer> friends = user1.getFriends();
        return userStorage.get()
                .stream()
                .filter(x -> friends.contains(x.getId()))
                .toList();

    }

    public List<User> getCommonFriends(int userId, int userIdOther){
        List<User> friends = getAllFriends(userId);
        List<User> friendsOther = getAllFriends(userIdOther);

        return friends.stream()
                .filter(friendsOther::contains).toList();
    }

    public void addFriends(Integer userAim, Integer userFriend){
        log.info("{} добавлен в друзья к {}", userFriend, userAim);
        User user1 = getId(userAim);
        Set<Integer> friends = user1.getFriends();
        friends.add(userFriend);

        User user2 = getId(userFriend);
        Set<Integer> friends2 = user2.getFriends();
        friends2.add(userAim);
    }

    public void deleteFriends(Integer userAim, Integer userFriend){
        log.info("{} удален из друзей {}", userFriend, userAim);
        User user1 = getId(userAim);
        Set<Integer> friends = user1.getFriends();
        friends.remove(userFriend);

        User user2 = getId(userFriend);
        Set<Integer> friends2 = user2.getFriends();
        friends2.remove(userAim);
    }

    public List<User> get() {
        return new ArrayList<User>(userStorage.get());
    }

    public User getId(int id) {
        List<Integer> usersId = userStorage.get().stream().map(x -> x.getId()).toList();
        if (id <= 0 || !usersId.contains(id)){
            throw new NotFoundUserException("NotFoundUserException");
        }
        return userStorage.get().stream().filter(x -> x.getId() == id).toList().get(0);
    }

    public User create(User user) throws ParseException {
        return userStorage.create(user);
    }

    public User update(User user) throws ParseException {
        return userStorage.update(user);
    }
}
