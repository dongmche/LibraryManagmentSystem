package com.example.Fakeapp.dao.ReportManager;

import java.util.ArrayList;

public interface ReportManagerDao {
    public boolean report(Report report);

    public ArrayList<Report> getAll();
}
