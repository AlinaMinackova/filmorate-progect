package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
//@AllArgsConstructor
public class Film implements Comparable<Film>{

    @NonNull
    @EqualsAndHashCode.Exclude
    private int id;
    @NonNull
    @NotBlank
    private String name;
    @NonNull
    @EqualsAndHashCode.Exclude
    private String description;
    @NonNull
    @EqualsAndHashCode.Exclude
    private Date releaseDate;
    @NonNull
    @EqualsAndHashCode.Exclude
    private Double duration;
    @EqualsAndHashCode.Exclude
    private Set<Integer> likes;

    public Film(@NonNull String name, @NonNull String description, @NonNull Date releaseDate, @NonNull Double duration) {
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.likes = new HashSet<>();
    }

    @Override
    public int compareTo(Film o) {
        return this.likes.size() >= o.likes.size() ? -1 : 1;
    }
}
