package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.NonNull;

@Data
public class Friend {

    @NonNull
    private String user;
    @NonNull
    private String userFriend;
    private Boolean confirm;

    public Friend(@NonNull String user, @NonNull String userFriend, Boolean confirm) {
        this.user = user;
        this.userFriend = userFriend;
        this.confirm = confirm;
    }
}
