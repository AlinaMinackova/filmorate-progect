package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.NonNull;

@Data
public class LikeFilm {

    @NonNull
    private Integer id_film;
    @NonNull
    private String email_user;

    public LikeFilm(@NonNull Integer id_film, @NonNull String email_user) {
        this.id_film = id_film;
        this.email_user = email_user;
    }
}
