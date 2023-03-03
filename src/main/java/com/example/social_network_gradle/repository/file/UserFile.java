package com.example.social_network_gradle.repository.file;

import com.example.social_network_gradle.domain.Entity;
import com.example.social_network_gradle.domain.user.Address;
import com.example.social_network_gradle.domain.user.User;
import com.example.social_network_gradle.domain.validators.Validator;

import java.util.List;

public class UserFile extends AbstractFileRepository<String, User>{


    public UserFile(String fileName, Validator<User> validator) {
        super(fileName, validator);
    }

    @Override
    public User extractEntity(List<String> users) {
            String userName =  users.get(0);
            String firstName =  users.get(1);
            String lastName =  users.get(2);
            String email = users.get(3);
            String yearOfBirth =  users.get(4);
            String country =  users.get(5);
            String county =  users.get(6);
            String city =  users.get(7);
            String street =  users.get(8);
            String streetNumberS =  users.get(9);
            long streetnumber = Long.parseLong(streetNumberS);

            Address adress = new Address(country, county, city, street, streetnumber);
            return new User(userName, firstName, lastName, email, yearOfBirth, adress);
    }

    @Override
    protected String createEntityAsString(User user) {
        String userName = user.getId();
        String firstName = user.getFirstName();
        String lastName = user.getLastName();
        String email = user.getEmail();
        String yearOfBirth = user.getYearOfBirth();
        String country = user.getAddress().getCountry();
        String county = user.getAddress().getCounty();
        String city = user.getAddress().getCity();
        String street = user.getAddress().getStreet();
        String streetNumber = String.valueOf(user.getAddress().getStreetNumber());

        return userName + ";" + firstName + ";" + lastName + ";" + email + ";" + yearOfBirth + ";" + country + ";" + county + ";" + city + ";" + street + ";" + streetNumber;
    }
}
