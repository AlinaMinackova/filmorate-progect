package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.exceptions.InvalidDateException;
import ru.yandex.practicum.filmorate.exceptions.InvalidDurationException;
import ru.yandex.practicum.filmorate.exceptions.InvalidFilmNameException;
import ru.yandex.practicum.filmorate.exceptions.MaxLengthDescriptionException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import javax.naming.InvalidNameException;
import java.text.ParseException;
import java.time.Year;
import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class FilmTests {

    private static final Calendar calendar = Calendar.getInstance();
    static {
        calendar.set(2024, Calendar.MARCH, 5);
    }

    private static Date date = calendar.getTime();

    @Test
    void emptyNameFilm() {
        Film film = new Film("", "film about water", date, 60.0);

        FilmController filmController = new FilmController();
        assertThrows(InvalidFilmNameException.class,
                () -> filmController.create(film));
    }

    @Test
    void longDescriptionFilm() {
        Film film = new Film("Water", "film about water____________________________________________" +
                "_________________________________________________________________________________________________" +
                "_________________________________________________________________________________________________" +
                "______________", date, 60.0);

        FilmController filmController = new FilmController();
        assertThrows(MaxLengthDescriptionException.class,
                () -> filmController.create(film));
    }

    @Test
    void InCorrectDateFilm() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(1895, Calendar.MARCH, 5);

        Date date = calendar.getTime();
        Film film = new Film("Water", "film about water", date, 60.0);

        FilmController filmController = new FilmController();
        assertThrows(InvalidDateException.class,
                () -> filmController.create(film));
    }

    @Test
    void InCorrectDurationFilm() {
        Film film = new Film("Water", "film about water", date, 0.0);

        FilmController filmController = new FilmController();
        assertThrows(InvalidDurationException.class,
                () -> filmController.create(film));
    }

    @Test
    void getUser() throws ParseException {
        Film film = new Film("Water", "film about water", date, 60.0);

        FilmController filmController = new FilmController();
        filmController.create(film);
        Assertions.assertEquals(filmController.get().get(0), film);
    }
    @Test
    void createCorrectUser() throws ParseException {
        Film film = new Film("Water", "film about water", date, 60.0);

        FilmController filmController = new FilmController();
        filmController.create(film);
        Assertions.assertEquals(filmController.get().get(0), film);
    }

    @Test
    void updateUser() throws ParseException {

        Calendar calendar2 = Calendar.getInstance();
        calendar2.set(2024, Calendar.MARCH, 5);
        Date date2 = calendar2.getTime();

        Film film = new Film("Water", "film about water", date, 60.0);
        Film film2 = new Film("Water", "film about waterWater", date2, 120.0);

        FilmController filmController = new FilmController();
        filmController.create(film);
        filmController.update(film2);
        Assertions.assertEquals(filmController.get().get(0).getDescription(), "film about waterWater");
        Assertions.assertEquals(filmController.get().get(0).getReleaseDate(), date2);
        Assertions.assertEquals(filmController.get().get(0).getDuration(), 120);
    }

}