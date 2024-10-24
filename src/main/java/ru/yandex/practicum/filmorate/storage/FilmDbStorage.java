package ru.yandex.practicum.filmorate.storage;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dao.FilmStorage;
import ru.yandex.practicum.filmorate.exceptions.FilmAlreadyExistsException;
import ru.yandex.practicum.filmorate.exceptions.NotFoundFilmException;
import ru.yandex.practicum.filmorate.model.Film;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;

@Component
public class FilmDbStorage implements FilmStorage {

    private final JdbcTemplate jdbcTemplate;

    public FilmDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Film> get() {
        String sql = "select * from films";
        return jdbcTemplate.query(sql, new RowMapper<Film>() {
            @Override
            public Film mapRow(ResultSet rs, int rowNum) throws SQLException {
                String name = rs.getString("name");
                String description = rs.getString("description");;
                LocalDate releaseDate = LocalDate.parse(rs.getString("release_date"));
                Double duration = Double.valueOf(rs.getString("duration"));
                return new Film(name, description, releaseDate, duration);
            }});
    }

    @Override
    public void delete(String name) {
        String sql = "delete from films where name = ?";
        jdbcTemplate.update(sql, name);
    }

    @Override
    public Film getByName(String name) {
        SqlRowSet filmRow = jdbcTemplate.queryForRowSet( "select * from films where name = ?", name);
        if (filmRow.next()){
            return new Film(
                    filmRow.getString("name"),
                    filmRow.getString("description"),
                    LocalDate.parse(filmRow.getString("release_date")),
                    Double.valueOf(filmRow.getString("duration")));
        }
        return null;
    }

    @Override
    public Film create(Film film) throws ParseException {
        Film film1 = getByName(film.getName());
        if(film1 != null){
            throw new FilmAlreadyExistsException("FilmAlreadyExists");
        } String sql = "insert into films (name, description, release_date, duration) values (?, ?, ?, ?)";
        jdbcTemplate.update(sql, film.getName(), film.getDescription(), film.getReleaseDate(), film.getDuration());
        return film;
    }

    @Override
    public Film update(Film film) throws ParseException {
        Film film1 = getByName(film.getName());
        if(film1 == null){
            throw new NotFoundFilmException("NotFoundFilm");
        } String sql = "update films set (description, release_date, duration) = (?, ?, ?) where name = ?";
        jdbcTemplate.update(sql, film.getDescription(), film.getReleaseDate(), film.getDuration(), film.getName());
        return film;
    }
}
