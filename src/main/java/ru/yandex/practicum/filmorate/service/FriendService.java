package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.FriendStorage;
import ru.yandex.practicum.filmorate.exceptions.NotFoundUserException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

@Service
@Slf4j
public class FriendService {

    private final FriendStorage friendStorage;
    private final UserService userService;

    @Autowired
    public FriendService(FriendStorage friendStorage, UserService userService) {
        this.friendStorage = friendStorage;
        this.userService = userService;
    }

    public List<User> getByEmail(String email) {
        User user = userService.getByEmail(email);
        if (user == null) {
            throw new NotFoundUserException("NotFoundUser");
        }
        return friendStorage.getByEmail(email);
    }

    public void addFriend(String emailUser, String emailFriend) {
        User user = userService.getByEmail(emailUser);
        User userFriend = userService.getByEmail(emailUser);
        if (user == null) {
            throw new NotFoundUserException("NotFoundUser");
        }
        if (userFriend == null) {
            throw new NotFoundUserException("NotFoundUserFriend");
        }
        friendStorage.addFriend(emailUser, emailFriend);
    }

    public void deleteFriend(String emailUser, String emailFriend) {
        User user = userService.getByEmail(emailUser);
        User userFriend = userService.getByEmail(emailUser);
        if (user == null) {
            throw new NotFoundUserException("NotFoundUser");
        }
        if (userFriend == null) {
            throw new NotFoundUserException("NotFoundUserFriend");
        }
        friendStorage.deleteFriend(emailUser, emailFriend);
    }
}
