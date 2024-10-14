package ru.yandex.practicum.filmorate.storage;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.filmorate.exceptions.InvalidDateException;
import ru.yandex.practicum.filmorate.exceptions.InvalidEmailException;
import ru.yandex.practicum.filmorate.exceptions.InvalidLoginException;
import ru.yandex.practicum.filmorate.exceptions.UserAlreadyExistsException;
import ru.yandex.practicum.filmorate.model.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Component//тоже самое @Service
public class InMemoryUserStorage implements UserStorage{

    private final HashMap<String, User> users =new HashMap<String, User>();
    private static int ID = 0;

    public List<User> get() {
        return new ArrayList<User>(users.values());
    }

    public void checkExceptions(User user) throws ParseException {
        if (user.getEmail().equals("") || !user.getEmail().contains("@")){
            log.debug("Неправильный или пустой email");
            throw new InvalidEmailException("InvalidEmailException");
        }
        if (user.getLogin().equals("") || user.getLogin().contains(" ")){
            log.debug("Пустой логин");
            throw new InvalidLoginException("InvalidLoginException");
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = simpleDateFormat.parse(String.valueOf(LocalDate.now()));
        if (user.getBirthday().after(date)){
            log.debug("Неправильная дата");
            throw new InvalidDateException("InvalidDateException");
        }
        if (user.getName().equals("")){
            log.info("Не указано имя");
            user.setName(user.getLogin());
        }
    }

    public User create(User user) throws ParseException {
        checkExceptions(user);
        //log.info("Добавлен пост: {}", post);
        if (users.containsKey(user.getEmail())){
            throw new UserAlreadyExistsException("UserAlreadyExistsException");
        }
        user.setId(ID++);
        users.put(user.getEmail(), user);
        log.info("Добавлен новый user {}", user);
        return user;
    }

    public User update(User user) throws ParseException {
        checkExceptions(user);
        if (users.containsKey(user.getEmail())) {
            User user1 = users.get(user.getEmail());
            user1.setBirthday(user.getBirthday());
            user1.setName(user.getName());
            user1.setLogin(user.getLogin());
            log.info("Обновлен user {}", user);
            return users.get(user.getEmail());
        }
        return create(user);
    }
}
