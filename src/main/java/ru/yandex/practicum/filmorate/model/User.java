package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
//@AllArgsConstructor
public class User {

    @NonNull
    @EqualsAndHashCode.Exclude
    private int id;
    @NonNull
    private String email;
    @NonNull
    @EqualsAndHashCode.Exclude
    @NotBlank
    private String login;
    @NonNull
    @EqualsAndHashCode.Exclude
    private String name;
    @NonNull
    @EqualsAndHashCode.Exclude
    private Date birthday;
    @EqualsAndHashCode.Exclude
    private Set<Integer> friends;

    public User(@NonNull String email, @NonNull String login, @NonNull String name, @NonNull Date birthday) {
        this.email = email;
        this.login = login;
        this.name = name;
        this.birthday = birthday;
        this.friends = new HashSet<>();
    }
}
