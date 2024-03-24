package com.example.Fakeapp.func;

import java.util.Calendar;

public class CalcDate {
    public static java.sql.Date calcDueDate(){
        java.util.Date utilDate = new java.util.Date(); // This captures the current date and time

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(utilDate);
        calendar.add(Calendar.DAY_OF_MONTH, 30); // Adding 30 days

        java.util.Date updatedUtilDate = calendar.getTime();
        java.sql.Date dueDate = new java.sql.Date(updatedUtilDate.getTime());

        return dueDate;
    }

    public static java.sql.Date today(){
        java.util.Date utilDate = new java.util.Date(); // This captures the current date and time

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(utilDate);

        java.util.Date updatedUtilDate = calendar.getTime();
        java.sql.Date today = new java.sql.Date(updatedUtilDate.getTime());

        return today;
    }
}