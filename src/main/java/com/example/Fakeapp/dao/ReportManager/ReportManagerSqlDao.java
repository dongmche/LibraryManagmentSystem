package com.example.Fakeapp.dao.ReportManager;

import com.example.Fakeapp.Conn.DatabaseConnection;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;


@Component
public class ReportManagerSqlDao implements ReportManagerDao{
    @Override
    public boolean report(Report report) {
        try {
            Connection connection = DatabaseConnection.getConnection();
            if (connection == null) {
                return false;
            }
            java.lang.String sql = "INSERT INTO reports (username, report_date, status) VALUES (?, ?, ?);";
            try {
                PreparedStatement insert = connection.prepareStatement(sql);
                insert.setString(1, report.getUser());
                insert.setDate(2, new java.sql.Date(report.getDate().getTime()));
                insert.setInt(3, report.getStatus().getValue());
                insert.executeUpdate();
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
                connection.rollback(); // Rollback in case of an exception
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public ArrayList<Report> getAll() {
        ArrayList<Report> reports = new ArrayList<>();
        String sql = "SELECT username, report_date, status FROM reports;";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String username = resultSet.getString("username");

                Date date = new Date(resultSet.getDate("report_date").getTime()); // Convert java.sql.Date to java.util.Date if necessary
                int status = resultSet.getInt("status");
                Report report = new Report(username, date, Status.fromInt(status));
                reports.add(report);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Consider proper logging
            return null; // Consider returning an empty list instead of null to avoid NullPointerException in the calling code.
        }

        return reports;
    }



}
