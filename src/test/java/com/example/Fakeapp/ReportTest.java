package com.example.Fakeapp;

import com.example.Fakeapp.Conn.DatabaseConnection;
import com.example.Fakeapp.dao.ReportManager.Report;
import com.example.Fakeapp.dao.ReportManager.ReportManagerDao;
import com.example.Fakeapp.dao.ReportManager.ReportManagerSqlDao;
import com.example.Fakeapp.dao.ReportManager.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

public class ReportTest {
    @BeforeEach
    public void createAndCleanTable() {
        // SQL command to drop the table if it exists
        String dropTableSql = "DROP TABLE IF EXISTS reports";
        // SQL command to create a table
        String sql = "CREATE TABLE reports (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "username VARCHAR(255) NOT NULL," +
                "report_date DATE NOT NULL," +
                "status INT NOT NULL" +
                ");";

        try {
            // Establishing a connection to the database
            Connection conn = DatabaseConnection.getConnection();
            // Creating a statement
            Statement stmt = conn.createStatement();

            // Executing the command to drop the table if it exists
            stmt.execute(dropTableSql);

            // Executing the SQL command
            stmt.execute(sql);

            // Closing the statement and connection
            stmt.close();
            conn.close();

        } catch (Exception e) {
//            e.printStackTrace();
        }
    }
    @Test
    public void testReport(){
        Report report = new Report("g", new Date(), Status.RETURNED);
        System.out.println("The report status is: " + report.getStatus()); // Prints: BORROWED

        int statusValue = 1; // Example value
        report.setStatus(Status.fromInt(statusValue));
        System.out.println("The report status is: " + report.getStatus()); // Prints: RETURNED
        System.out.println("The report status is: " + report.getDate()); // Prints: RETURNED
    }

    // bad practice I tested this manually because of time :(
    @Test
    public void testCreateReport(){
        ReportManagerDao reportManagerDao = new ReportManagerSqlDao();


        Report report1 = new Report("g", new Date(), Status.RETURNED);
        Report report2 = new Report("g", new Date(), Status.BORROWED);
        Report report3 = new Report("g", new Date(), Status.OVERDUE);

        assert true == reportManagerDao.report(report1);
        assert true == reportManagerDao.report(report2);
        assert true == reportManagerDao.report(report3);

        ArrayList<Report> reports = reportManagerDao.getAll();
        assert reports.size() == 3;
    }

}
