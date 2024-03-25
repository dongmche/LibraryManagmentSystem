package com.example.Fakeapp;

import com.example.Fakeapp.Conn.DatabaseConnection;
import com.example.Fakeapp.dao.UserManager.User;
import com.example.Fakeapp.dao.UserManager.UserDao;
import com.example.Fakeapp.dao.UserManager.UserSqlDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;

public class testUserDao {

    @BeforeEach
    public void createNewTable() {
        // SQL command to drop the table if it exists
        java.lang.String dropTableSql = "DROP TABLE IF EXISTS users";
        // SQL command to create a table
        java.lang.String sql = "CREATE TABLE users (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "username VARCHAR(50) NOT NULL UNIQUE, " +
                "password VARCHAR(100) NOT NULL, " +
                "gmail VARCHAR(50) NOT NULL UNIQUE, " +
                "UNIQUE (gmail)" +
                ");";
        String  alterTable = "ALTER TABLE users AUTO_INCREMENT = 1000;";

        try {
            // Establishing a connection to the database
            Connection conn = DatabaseConnection.getConnection();
            // Creating a statement
            Statement stmt = conn.createStatement();

            // Executing the command to drop the table if it exists
            stmt.execute(dropTableSql);

            // Executing the SQL command
            stmt.execute(sql);
            stmt.execute(alterTable);

            // Closing the statement and connection
            stmt.close();
            conn.close();

        } catch (Exception e) {
        }
    }
    private UserDao userDao = new UserSqlDao();
    @Test
    public void assertCreateTable() {

        User user1 = new User("giorgi", "mche", "gio@gmail.com");
        User user2 = new User("giorgi1", "mche", "gio@gmail.com");
        User user3 = new User("giorgi", "mche", "gio1@gmail.com");
        assert true == userDao.create(user1);
        assert false == userDao.create(user2);
        assert false == userDao.create(user3);

        User user = userDao.findByUsername("giorgi");
        assert user.getName().equals("giorgi");
        assert user.getPassword().equals("mche");
        assert user.getGmail().equals("gio@gmail.com");

        assert true == userDao.exists("giorgi");

        assert true == userDao.existsByUsernameOrGmail("giorgi", "na");
        assert true == userDao.existsByUsernameOrGmail("giodsa", "gio@gmail.com");
        assert false == userDao.existsByUsernameOrGmail("giodsa", "gi3o@gmail.com");
        assert false == userDao.existsByUsernameOrGmail("giodsa", "gi1o@gmail.com");

    }

    @Test
    public void getAll() {

        User user1 = new User("giorgi", "mche", "gio@gmail.com");
        User user2 = new User("giogi3", "mche", "gio2@gmail.com");
        User user3 = new User("giorg2", "mche", "gio3@gmail.com");

        user1.secure();
        user2.secure();
        user3.secure();

        assert true == userDao.create(user1);
        assert true == userDao.create(user2);
        assert true == userDao.create(user3);

        ArrayList<User> user = userDao.getAll();
        assert user.size() == 3;
    }

    @Test
    public void delete() {

        User user1 = new User("giorgi1", "mche", "gio@gmail.com");
        User user2 = new User("giogi2", "mche", "gio2@gmail.com");
        User user3 = new User("giorg3", "mche", "gio3@gmail.com");

        user1.secure();
        user2.secure();
        user3.secure();

        assert true == userDao.create(user1);
        assert true == userDao.create(user2);
        assert true == userDao.create(user3);

        assert userDao.getAll().size() == 3;

        assert true == userDao.exists(user1.getName());
        assert true == userDao.delete(user1.getName());
        assert false == userDao.exists(user1.getName());
    }

    @Test
    public void edit() {
        User user1 = new User("giorgi1", "mche", "gio@gmail.com");
        User user2 = new User("giogi2", "mche", "gio2@gmail.com");
        User user3 = new User("giorg3", "mche", "gio3@gmail.com");

        user1.secure();
        user2.secure();
        user3.secure();

        assert true == userDao.create(user1);
        assert true == userDao.create(user2);
        assert true == userDao.create(user3);

        User user = userDao.findByUsername(user1.getName());
        assert user != null;


        user.setGmail("other@gmail.com");
        user.setPassword("otherPassword");

        assert true == userDao.edit(user.getName(), user);

        User newuser = userDao.findByUsername(user1.getName());
        assert newuser != null;
        assert newuser.equals(user);
    }

    @Test
    public void testFindByIdAndSearch(){
        User user1 = new User("giorgi1", "mche", "gio@gmail.com");
        User user2 = new User("giogi2", "mche", "gio2@gmail.com");
        User user3 = new User("giorg3", "mche", "gio3@gmail.com");

        user1.secure();
        user2.secure();
        user3.secure();

        assert true == userDao.create(user1);
        assert true == userDao.create(user2);
        assert true == userDao.create(user3);

        user1 = userDao.findByUsername(user1.getName());
        user2 = userDao.findByUsername(user2.getName());
        user3 = userDao.findByUsername(user3.getName());

        assert user1 != null;
        assert user2 != null;
        assert user3 != null;


        // search test
        ArrayList<User> users = userDao.search("g");
        assert users.size() == 3;
        assert 1 == userDao.search("gio@gmail.com").size();
        assert 1 == userDao.search("gio@").size();
        assert 1 == userDao.search("gio2@").size();


        User one = userDao.findById(user1.getId());
        User two = userDao.findById(user2.getId());
        User three = userDao.findById(user3.getId());

        assert one != null;
        assert two != null;
        assert three != null;

        assert one.getName().equals(user1.getName());
        assert two.getGmail().equals(user2.getGmail());
        assert three.getPassword().equals(user3.getPassword());

    }

    @Test
    public void testHash(){

        User user = new User("root", "root", "root@gmail.com");
        user.secure();
        System.out.println(user.getPassword());
    }


}





