package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

import java.util.Date;

@Data
public class Friends {

    @NonNull
    private String email_user;
    @NonNull
    private String email_friend;

    public Friends(@NonNull String email_user, @NonNull String email_friend) {
        this.email_user = email_user;
        this.email_friend = email_friend;
    }
}
