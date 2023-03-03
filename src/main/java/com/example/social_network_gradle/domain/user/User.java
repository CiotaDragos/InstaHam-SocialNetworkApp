package com.example.social_network_gradle.domain.user;

import com.example.social_network_gradle.domain.Entity;

import java.util.Objects;

import java.time.LocalDate;
import java.util.Locale;

public class User extends Entity<String> {
    private String firstName;
    private String lastName;
    private String email;
    private String yearOfBirth;
    private Address address;

    /**
     * Copy constructor
     * @param anotherUser, type User
     */
    public User(User anotherUser) {
        this.firstName = anotherUser.firstName;
        this.lastName = anotherUser.lastName;
        this.email = anotherUser.email;
        this.yearOfBirth = anotherUser.yearOfBirth;
        this.address = anotherUser.address;
        super.setId(anotherUser.getId());
    }

    /**
     * User's basic constructor withoud ID
     * @param firstName, String
     * @param lastName, String
     */
    public User(String firstName, String lastName, String email, String yearOfBirth, Address address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email.toLowerCase(Locale.ROOT);
        this.yearOfBirth = yearOfBirth;
        this.address = address;
    }

    /**
     * User's constructor with ID
     * @param firstName, String
     * @param lastName, String
     * @param userName, String
     */
    public User(String userName, String firstName, String lastName, String email, String yearOfBirth, Address address) {
        this(firstName, lastName, email, yearOfBirth, address);
        super.setId(userName);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getYearOfBirth() {
        return yearOfBirth;
    }

    public void setYearOfBirth(String yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        User user = (User) o;
        return firstName.equals(user.firstName) && lastName.equals(user.lastName) && email.equals(user.email) && yearOfBirth.equals(user.yearOfBirth) && address.equals(user.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), firstName, lastName, email, yearOfBirth, address);
    }

    @Override
    public String toString() {
        return "User{\n" +
                "\tuserName  ='" + super.getId() + "'\n" +
                "\tfirstName ='" + firstName + "'\n" +
                "\tlastName  ='" + lastName + "'\n" +
                "\temail     ='" + email + "'\n" +
                "\tbirthDate ='" + yearOfBirth + "'\n"+
                "\taddress   ='" + address.toString() + "'\n" +
                '}';
    }
}