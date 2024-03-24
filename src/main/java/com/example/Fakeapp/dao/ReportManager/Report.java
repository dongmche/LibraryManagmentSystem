package com.example.Fakeapp.dao.ReportManager;



import java.util.Date;

public class Report {
    private String user;
    private Date date;
    private Status status; // Use enum type

    public Report(String user, Date date, Status status) {
        this.user = user;
        this.date = date;
        this.status = status;
    }

    // Getters and setters
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
