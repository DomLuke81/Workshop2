package pl.coderslab.workshop2.entity;

import org.mindrot.jbcrypt.BCrypt;
import pl.coderslab.workshop2.DbUtil;

import java.sql.*;

public class UserDao {
    private static final String CREATE_USER_QUERY =
            "INSERT INTO users (username, email, password) VALUES (?, ?, ?)";
    private static final String SELECT_USER_QUERY =
            "SELECT * FROM users WHERE id = ?";
    private static final String UPDATE_USER_QUERY =
            "UPDATE users SET username = ?, email = ?, password = ? WHERE id = ?";
    private static final String DELETE_USER_QUERY =
            "DELETE FROM users WHERE id = ?";

    public String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public User create(User user) {
        try (Connection conn = DbUtil.getConnection()) {
            PreparedStatement statement =
                    conn.prepareStatement(CREATE_USER_QUERY, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getUserName());
            statement.setString(2, user.getEmail());
            statement.setString(3, hashPassword(user.getPassword()));
            statement.executeUpdate();
            //Pobieramy wstawiony do bazy identyfikator, a następnie ustawiamy id obiektu user.
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                user.setId(resultSet.getInt(1));
            }
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public User read(int userId) {
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement statement = conn.prepareStatement(SELECT_USER_QUERY)) {
            statement.setInt(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    User user = new User(
                            resultSet.getInt("id"),
                            resultSet.getString("username"),
                            resultSet.getString("email"),
                            resultSet.getString("password"));
                    return user;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    public void update(User user) {
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement statement = conn.prepareStatement(UPDATE_USER_QUERY)) {
            statement.setInt(4, user.getId());
            statement.setString(1, user.getUserName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPassword());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int userId) {
        try (Connection conn = DbUtil.getConnection();
             PreparedStatement statement = conn.prepareStatement(DELETE_USER_QUERY)) {
            statement.setInt(1,userId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
