package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
//@AllArgsConstructor
public class User implements Comparable<User>{

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
    private LocalDate birthday;

    public User(@NonNull String email, @NonNull String login, @NonNull String name, @NonNull LocalDate birthday) {
        this.email = email;
        this.login = login;
        this.name = name;
        this.birthday = birthday;
    }

    @Override
    public int compareTo(User o) {
        return 0;
    }
}
