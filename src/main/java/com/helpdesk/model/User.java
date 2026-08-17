package com.helpdesk.model;

public abstract class User {

    // com.helpdesk.model.User data and shared ID counter
    private String name;
    private String lastName;
    private String email;
    private String phoneNumber;
    private int id;
    private static int counter;


    // Constructor
    protected User(String name, String lastName, String email, String phoneNumber) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        counter++;
        this.id = counter;
    }

    // Getters
    public String getName() {
        return name;
    }
    public String getLastName() {
        return lastName;
    }
    public String getEmail() {
        return email;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public int getId() {
        return id;
    }

    //  Setters for mutable fields
    public void setName(String name) {
        this.name = name;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    // Returns a string representation of the user
    @Override
    public String toString() {
        return "Name: " + this.name + ", Last Name: " + this.lastName + ", Email: " + this.email + ", Phone Number: " + this.phoneNumber + ", ID: " + this.id;
    }
}
