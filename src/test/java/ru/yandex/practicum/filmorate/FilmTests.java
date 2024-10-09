package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.text.ParseException;
import java.time.Year;
import java.util.Calendar;
import java.util.Date;

@SpringBootTest
class FilmTests {

    @Test
    void emptyNameFilm() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2024, Calendar.MARCH, 5);

        Date date = calendar.getTime();
        Film film = new Film("", "film about water", date, 60.0);

        FilmController filmController = new FilmController();
        try {
            filmController.create(film);
        } catch (Exception e){
            Assertions.assertEquals("InvalidFilmNameException", e.getMessage());
        }
    }

    @Test
    void longDescriptionFilm() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2024, Calendar.MARCH, 5);

        Date date = calendar.getTime();
        Film film = new Film("Water", "film about water____________________________________________" +
                "_________________________________________________________________________________________________" +
                "_________________________________________________________________________________________________" +
                "______________", date, 60.0);

        FilmController filmController = new FilmController();
        try {
            filmController.create(film);
        } catch (Exception e){
            Assertions.assertEquals("MaxLengthDescriptionExceptionMore200", e.getMessage());
        }
    }

    @Test
    void InCorrectDateFilm() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(1895, Calendar.MARCH, 5);

        Date date = calendar.getTime();
        Film film = new Film("Water", "film about water", date, 60.0);

        FilmController filmController = new FilmController();
        try {
            filmController.create(film);
        } catch (Exception e){
            Assertions.assertEquals("InvalidDateException", e.getMessage());
        }
    }

    @Test
    void InCorrectDurationFilm() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2022, Calendar.MARCH, 5);

        Date date = calendar.getTime();
        Film film = new Film("Water", "film about water", date, 0.0);

        FilmController filmController = new FilmController();
        try {
            filmController.create(film);
        } catch (Exception e){
            Assertions.assertEquals("InvalidDurationException", e.getMessage());
        }
    }

    @Test
    void getUser() throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2022, Calendar.MARCH, 5);

        Date date = calendar.getTime();
        Film film = new Film("Water", "film about water", date, 60.0);

        FilmController filmController = new FilmController();
        filmController.create(film);
        Assertions.assertEquals(filmController.get().get(0), film);
    }
    @Test
    void createCorrectUser() throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2022, Calendar.MARCH, 5);

        Date date = calendar.getTime();
        Film film = new Film("Water", "film about water", date, 60.0);

        FilmController filmController = new FilmController();
        filmController.create(film);
        Assertions.assertEquals(filmController.get().get(0), film);
    }

    @Test
    void updateUser() throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2022, Calendar.MARCH, 5);
        Date date = calendar.getTime();

        Calendar calendar2 = Calendar.getInstance();
        calendar2.set(2022, Calendar.MARCH, 5);
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