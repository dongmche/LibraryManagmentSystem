package com.example.Fakeapp.dao.UserManager;

import com.example.Fakeapp.Conn.DatabaseConnection;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


@Component
public class UserSqlDao implements UserDao {
//    public boolean oldCreate(java.lang.String username, java.lang.String password, java.lang.String gmail) {
//        try {
//            Connection connection = dataBaseConnection.getConnection();
//            if (connection == null) {
//                return false;
//            }
//            java.lang.String sql = "INSERT INTO users (username, password, gmail) VALUES (?, ?, ?);";
//            try (PreparedStatement insert = connection.prepareStatement(sql)) {
//                insert.setString(1, username);
//                insert.setString(2, password); // Assuming password hashing is handled elsewhere
//                insert.setString(3, gmail);
//                insert.executeUpdate();
//                return true;
//            } catch (SQLException e) {
//                connection.rollback(); // Rollback in case of an exception
//                return false;
//            }
//        } catch (SQLException e) {
//            return false;
//        }
//    }

    @Override
    public boolean create(User user) {
        try {
            Connection connection = DatabaseConnection.getConnection();
            if (connection == null) {
                return false;
            }
            java.lang.String sql = "INSERT INTO users (username, password, gmail) VALUES (?, ?, ?);";
            try (PreparedStatement insert = connection.prepareStatement(sql)) {
                insert.setString(1, user.getName());
                insert.setString(2, user.getPassword()); // Assuming password hashing is handled elsewhere
                insert.setString(3, user.getGmail());
                insert.executeUpdate();
                return true;
            } catch (SQLException e) {
                connection.rollback(); // Rollback in case of an exception
                return false;
            }
        } catch (SQLException e) {
            return false;
        }
    }


    @Override
    public User findByUsername(java.lang.String username) {
        User user = null;
        java.lang.String sql = "SELECT id, username, password, gmail FROM users WHERE username = ?";

        try {

            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                long id = resultSet.getLong("id");
                java.lang.String foundUsername = resultSet.getString("username");
                java.lang.String password = resultSet.getString("password");
                java.lang.String gmail = resultSet.getString("gmail");
                user = new User(id, foundUsername, password,gmail);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return user;
    }

    @Override
    public User findById(Long id) {
        User user = null;
        java.lang.String sql = "SELECT id, username, password, gmail FROM users WHERE id = ?";

        try {

            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                long Id = resultSet.getLong("id");
                java.lang.String foundUsername = resultSet.getString("username");
                java.lang.String password = resultSet.getString("password");
                java.lang.String gmail = resultSet.getString("gmail");
                user = new User(Id, foundUsername, password,gmail);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return user;
    }

//    @Override
//    public User find(long id) {
//        return null;
//    }

    public boolean exists(java.lang.String username) {
        java.lang.String sql = "SELECT EXISTS(SELECT 1 FROM users WHERE username = ?) AS userExists";

        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, username);
            try (ResultSet resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("userExists") == 1;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    public boolean existsByUsernameOrGmail(java.lang.String username, java.lang.String gmail) {
        java.lang.String sql = "SELECT EXISTS(SELECT 1 FROM users WHERE username = ? OR gmail = ?) AS 'exists'";

        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, gmail);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                // 'exists' column will have 1 if either username or gmail exists, otherwise 0
                return resultSet.getInt("exists") > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return true;
        }
        return false; // Returns false if there was an error or if the username/gmail does not exist
    }

    @Override
    public boolean delete(java.lang.String username) {
            java.lang.String sql = "DELETE FROM users WHERE username = ?";

            try (
                    Connection conn = DatabaseConnection.getConnection();
                    PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setString(1, username);
                int rowsAffected = stmt.executeUpdate();

                return rowsAffected > 0;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }



    @Override
    public boolean edit(String username, User user) {
            String sql = "UPDATE users SET id = ?, password = ?, gmail = ?, username = ? WHERE username = ?";

            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {


                stmt.setLong(1, user.getId());
                stmt.setString(2, user.getPassword());
                stmt.setString(3, user.getGmail());
                stmt.setString(4, user.getName());
                stmt.setString(5, username);

                int rowsAffected = stmt.executeUpdate();

                return rowsAffected > 0;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }



    @Override
    public ArrayList<User> getAll() {
        ArrayList<User> users = new ArrayList<>();
        java.lang.String sql = "SELECT * FROM users WHERE id >= 1000";

        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);

            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                // Assuming User class has a constructor that matches the order and types of these fields
                User user = new User(
                        resultSet.getLong("id"),
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getString("gmail")
                );
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null; // might handle this differently in future
        }
        return users;
    }

}
