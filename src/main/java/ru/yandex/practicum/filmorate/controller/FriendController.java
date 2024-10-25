package ru.yandex.practicum.filmorate.controller;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.FriendService;

import java.util.List;

@RestController
@RequestMapping("/friend")
public class FriendController {

    private final FriendService friendService;

    public FriendController(FriendService friendService) {
        this.friendService = friendService;
    }

    @GetMapping("/{userEmail}")
    public List<User> getByEmail(@PathVariable String userEmail) {
        return friendService.getByEmail(userEmail);
    }

    @PostMapping("/{userEmail}/add/{friendEmail}")
    public void getByEmail(@PathVariable String userEmail, @PathVariable String friendEmail) {
        friendService.addFriend(userEmail, friendEmail);
    }

    @DeleteMapping("/{userEmail}/delete/{friendEmail}")
    public void deleteByEmail(@PathVariable String userEmail, @PathVariable String friendEmail) {
        friendService.deleteFriend(userEmail, friendEmail);
    }
}
