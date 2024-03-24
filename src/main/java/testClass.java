import com.example.Fakeapp.func.Encoder;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class testClass {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/library"; // Replace "mydatabase" with your database name
        String user = "root"; // Your database username
        String password = "a2a3b2b3"; // Your database password

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            if (conn != null) {
                System.out.println("Connected to the database!");
                // You can now interact with the database
            }
        } catch (SQLException e) {
            System.out.println("Unable to connect to the database");
            e.printStackTrace();
            // Handle any SQL errors here
        }
        Encoder encoder = new Encoder();
        System.out.println(encoder.hash("root"));
    }
}
