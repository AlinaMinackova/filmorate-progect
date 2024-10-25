package ru.yandex.practicum.filmorate.storage;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dao.UserStorage;
import ru.yandex.practicum.filmorate.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@Component
public class UserDbStorage implements UserStorage {

    private final JdbcTemplate jdbcTemplate;

    public UserDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<User> get() {
        String sql = "select * from users";
        return jdbcTemplate.query(sql, new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                String email = rs.getString("email");
                String login = rs.getString("login");
                ;
                String name = rs.getString("name");
                LocalDate birthday = LocalDate.parse(rs.getString("birthday"));
                return new User(email, login, name, birthday);
            }
        });
    }

    @Override
    public User create(User user) {
        String sql = "insert into users (email, login, name, birthday) values (?, ?, ?, ?)";
        jdbcTemplate.update(sql, user.getEmail(), user.getLogin(), user.getName(), user.getBirthday());
        return user;
    }

    @Override
    public User update(User user) {
        String sql = "update users set (login, name, birthday) = (?, ?, ?) where email = ?";
        jdbcTemplate.update(sql, user.getLogin(), user.getName(), user.getBirthday(), user.getEmail());
        return user;
    }

    @Override
    public User getByEmail(String email) {
        SqlRowSet userRow = jdbcTemplate.queryForRowSet("select * from users where email = ?", email);
        if (userRow.next()) {
            return new User(
                    userRow.getString("email"),
                    userRow.getString("login"),
                    userRow.getString("name"),
                    LocalDate.parse(userRow.getString("birthday")));
        }
        return null;
    }

    @Override
    public void delete(String email) {
        String sql = "delete from users where email = ?";
        jdbcTemplate.update(sql, email);
    }
}
