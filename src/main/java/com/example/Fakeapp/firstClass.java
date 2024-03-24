package com.example.Fakeapp;

public class firstClass {
    private String myVar;

    public firstClass(String myVar) {
        this.myVar = myVar;
    }

    public String sayHello(){
        return "hello from the my first class: " + myVar;
    }
}
