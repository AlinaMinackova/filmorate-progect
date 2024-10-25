package ru.yandex.practicum.filmorate.storage;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dao.FriendStorage;
import ru.yandex.practicum.filmorate.model.Friend;
import ru.yandex.practicum.filmorate.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class FriendsStorage implements FriendStorage {

    private final JdbcTemplate jdbcTemplate;
    private final UserDbStorage userDbStorage;

    public FriendsStorage(JdbcTemplate jdbcTemplate, UserDbStorage userDbStorage) {
        this.jdbcTemplate = jdbcTemplate;
        this.userDbStorage = userDbStorage;
    }

    @Override
    public List<User> getByEmail(String email) {
        String sql = "select * from friends where id_user = ?";
        List<Friend> friends = jdbcTemplate.query(sql, this::makeFriends, email);

        return friends.stream()
                .map(x -> userDbStorage.getByEmail(x.getUserFriend()))
                .toList();
    }

    @Override
    public void addFriend(String emailUser, String emailFriend) {
        String sql = "select * from friends where id_user = ?";
        List<Friend> friends = jdbcTemplate.query(sql, this::makeFriends, emailFriend);
        if (!friends.isEmpty()) {
            Friend friend = friends.stream().filter(x -> x.getUserFriend().equals(emailUser)).toList().get(0);
            String sql2 = "insert into friends (id_user, id_friend, confirm) values (?, ?, ?)";
            if (friend == null) {
                jdbcTemplate.update(sql2, emailUser, emailFriend, false);
            } else {
                jdbcTemplate.update(sql2, emailUser, emailFriend, true);
                String sql3 = "update friends set confirm = ? where id_user = ? and id_friend = ?";
                jdbcTemplate.update(sql3, true, emailFriend, emailUser);
            }
        } else {
            String sql2 = "insert into friends (id_user, id_friend, confirm) values (?, ?, ?)";
            jdbcTemplate.update(sql2, emailUser, emailFriend, false);
        }
    }

    @Override
    public void deleteFriend(String emailUser, String emailFriend) {
        String sql = "select * from friends where id_user = ?";
        List<Friend> friends = jdbcTemplate.query(sql, this::makeFriends, emailUser);
        Friend friend = friends.stream().filter(x -> x.getUserFriend().equals(emailFriend)).toList().get(0);
        if (friend != null) {
            String sql2 = "delete from friends where id_user = ? and id_friend = ?";
            jdbcTemplate.update(sql2, emailUser, emailFriend);
            if (friend.getConfirm()) {
                String sql3 = "update friends set confirm = ? where id_user = ? and id_friend = ?";
                jdbcTemplate.update(sql3, false, emailFriend, emailUser);
            }
        }
    }

    public Friend makeFriends(ResultSet rs, int rowNum) throws SQLException {
        String id_user = rs.getString("id_user");
        String id_friend = rs.getString("id_friend");
        String confirm = rs.getString("confirm");
        if (confirm.equals("t")) {
            return new Friend(id_user, id_friend, true);
        }
        return new Friend(id_user, id_friend, false);
    }
}
