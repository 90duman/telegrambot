package com.example.testbot.model;

import lombok.Data;

@Data
public class Customer {

    private String firstName;
    private String lastname;
    private String modileNumber;
    private String email;

    public Customer(String firstName, String lastname, String modileNumber, String email) {
        this.firstName = firstName;
        this.lastname = lastname;
        this.modileNumber = modileNumber;
        this.email = email;
    }
}
