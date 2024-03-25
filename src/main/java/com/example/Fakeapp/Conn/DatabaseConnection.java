package com.example.Fakeapp.Conn;

import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static com.example.Fakeapp.Statics.Statics.*;

@Component
public class DatabaseConnection {

    public static Connection getConnection() {
        try {
            Connection conn = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
            if (conn != null) {
                // System.out.println("Connected to the database!");
                return conn;
            }

        } catch (SQLException e) {
            System.out.println("Unable to connect to the database");
            e.printStackTrace();
            // Handle any SQL errors here
            return null;
        }
        return null;
    }
}

//@Component
//public class DatabaseConnection {
//
//    private final DataSource dataSource;
//
//    @Autowired
//    public DatabaseConnection(DataSource dataSource) {
//        this.dataSource = dataSource;
//    }
//
//    public Connection getConnection() {
//        try {
//            Connection conn = dataSource.getConnection();
//            // Optional: Log or handle the successful connection
//            return conn;
//        } catch (SQLException e) {
//            // Log or handle the exception
//            e.printStackTrace();
//            return null;
//        }
//    }
//}



