package ru.yandex.practicum.filmorate.controller;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private HashMap<String, User> users =new HashMap<String, User>();
    private static int ID = 0;

    @GetMapping
    public List<User> get() {
        //log.info("Количество залогированных юзеров: {}", users.size());
        return (List<User>) users.values();
    }

    public void checkExceptions(User user) throws ParseException {
        if (user.getEmail().equals("") || !user.getEmail().contains("@")){
            throw new InvalidEmailException("InvalidEmailException");
        }
        if (user.getLogin().equals("") || !user.getLogin().contains(" ")){
            throw new InvalidLoginException("InvalidLoginException");
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-mm-dd");
        Date date = simpleDateFormat.parse(String.valueOf(LocalDate.now()));
        if (user.getBirthday().after(date)){
            throw new InvalidDateException("InvalidDateException");
        }
        if (user.getName().equals("")){
            user.setName(user.getLogin());
        }
    }

    @PostMapping
    public User create(@RequestBody User user) throws ParseException {
        checkExceptions(user);
        //log.info("Добавлен пост: {}", post);
        if (users.containsKey(user.getEmail())){
            throw new UserAlreadyExistsException("UserAlreadyExistsException");
        }
        user.setId(ID++);
        users.put(user.getEmail(), user);
        return user;
    }

    @PutMapping("")
    public User update(@RequestBody User user) throws ParseException {
        checkExceptions(user);
        if (!users.containsKey(user.getEmail())) {
            create(user);
        } else {
            User user1 = users.get(user.getEmail());
            user1.setBirthday(user.getBirthday());
            user1.setName(user.getName());
            user1.setLogin(user.getLogin());
        }
        return users.get(user.getEmail());
    }


}
