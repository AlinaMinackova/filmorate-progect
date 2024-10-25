package ru.yandex.practicum.filmorate.storage;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dao.FilmStorage;
import ru.yandex.practicum.filmorate.dao.LikeFilmStorage;
import ru.yandex.practicum.filmorate.exceptions.LikeAlreadyExistsException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.LikeFilm;
import ru.yandex.practicum.filmorate.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class LikeFilmDbStorage implements LikeFilmStorage {

    private final JdbcTemplate jdbcTemplate;
    private final FilmStorage filmStorage;

    public LikeFilmDbStorage(JdbcTemplate jdbcTemplate, FilmStorage filmStorage) {
        this.jdbcTemplate = jdbcTemplate;
        this.filmStorage = filmStorage;
    }

    @Override
    public List<Film> get(User user) {
        String sql = "select * from likefilms where id_user = ?";
        List<LikeFilm> likeFilms = jdbcTemplate.query(sql, this::makeLikeFilm, user.getEmail());
        return likeFilms.stream()
                .map(x -> filmStorage.getByName(x.getFilm()))
                .toList();

    }

    @Override
    public List<Film> getTop(int count) {
        String sql = "select id_film, id_user from likefilms where id_film in (" +
                "select id_film from likefilms group by id_film order by count(id_user) desc limit ?" +
                ")";
        List<LikeFilm> likeFilms = jdbcTemplate.query(sql, this::makeLikeFilm, count);

        return likeFilms.stream()
                .map(x -> filmStorage.getByName(x.getFilm()))
                .distinct()
                .toList();
    }

    @Override
    public void delete(String email, String name) {
        String sql = "delete from likefilms where id_film = ? and id_user = ?";
        jdbcTemplate.update(sql, name, email);
    }

    @Override
    public void like(String email, String name) {
        String sql = "select id_film, id_user from likefilms where id_film = ? and id_user = ?";
        List<LikeFilm> likeFilms = jdbcTemplate.query(sql, this::makeLikeFilm, name, email);
        if (likeFilms.isEmpty()) {
            String sql2 = "insert into likefilms (id_film, id_user) values (?, ?)";
            jdbcTemplate.update(sql2, name, email);
        } else {
            throw new LikeAlreadyExistsException("LikeAlreadyExists");
        }
    }


    public LikeFilm makeLikeFilm(ResultSet rs, int rowNum) throws SQLException {
        String id_film = rs.getString("id_film");
        String id_user = rs.getString("id_user");
        return new LikeFilm(id_film, id_user);
    }
}
