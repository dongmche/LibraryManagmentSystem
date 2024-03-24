package com.example.Fakeapp.dao.ReportManager;

public enum Status {
    BORROWED(0),
    RETURNED(1),
    OVERDUE(2);

    private final int value;

    Status(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static Status fromInt(int value) {
        for (Status status : Status.values()) {
            if (status.getValue() == value) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid status value: " + value);
    }
}

