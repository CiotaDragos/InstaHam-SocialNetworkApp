package com.example.social_network_gradle.ui;

import com.example.social_network_gradle.domain.friendship.Friendship;
import com.example.social_network_gradle.domain.user.User;
import com.example.social_network_gradle.domain.validators.ValidationException;
import com.example.social_network_gradle.repository.RepositoryException;
import com.example.social_network_gradle.service.Service;
import com.example.social_network_gradle.service.ServiceException;

import java.sql.SQLException;
import java.util.Scanner;

public class ConsoleInterface {
    private final Service service;

    public ConsoleInterface(Service service) {
        this.service = service;
    }

    private final Scanner in = new Scanner(System.in);

    public void run() throws SQLException {
        menu();
        while (true) {
            System.out.println(">>>");
            String input = in.nextLine();
            switch (input) {
                case "99" -> menu();
                case "0" -> System.exit(0);
                case "1" -> addUser();
                case "2" -> deleteUser();
                case "3" -> showUsers();
                case "4" -> addFriendship();
                case "5" -> deleteFriendship();
                case "6" -> showFriendships();
            }
        }
    }

    public void menu() {
        System.out.println("0 - Exit");
        System.out.println("1 - Add user");
        System.out.println("2 - Delete user");
        System.out.println("3 - Show all users");
        System.out.println("4 - Add friend");
        System.out.println("5 - Delete friend");
        System.out.println("6 - Show all the friendships");
    }

    public void addUser(){
        System.out.print("UserName: ");
        String userName = in.nextLine();
        System.out.print("First Name: ");
        String firstName = in.nextLine();
        System.out.print("Last Name: ");
        String lastName = in.nextLine();
        System.out.print("Email: ");
        String email = in.nextLine();
        System.out.print("BirthDate: ");
        String yearOfBirth = in.nextLine();
        System.out.println("Adress");
        System.out.print("Country: ");
        String country = in.nextLine();
        System.out.print("County: ");
        String county = in.nextLine();
        System.out.print("City: ");
        String city = in.nextLine();
        System.out.print("Street: ");
        String street = in.nextLine();
        System.out.print("Street number: ");
        String streetNumber = in.nextLine();
        try {
            service.addUser(userName, firstName, lastName, email, yearOfBirth, country, county, city, street, streetNumber);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }


    private void deleteUser() throws SQLException {
        showUsers();
        System.out.print("Input the ID of the user you want to remove: ");
        String userName = in.nextLine();

        try {
            service.deleteUser(userName);
            System.out.println("User successfully removed!");
        }
        catch (Exception exception){
            System.out.println(exception.getMessage());
        }
    }
    private void showUsers() throws SQLException {
        for (User user : service.getUsers())
            System.out.println(user);
    }
    private void addFriendship() throws SQLException {
        showUsers();
        System.out.println("Select the id's of the users you want to create a friendship with: ");
        System.out.println("User 1 Id: ");
        String userName1 = in.nextLine();
        System.out.println("User 2 Id: ");
        String userName2 = in.nextLine();

        try{
            service.addFriendship(userName1,userName2);
            System.out.println("Friendship added successfully!");
        }
        catch (Exception exception){
            System.out.println(exception.getMessage());
        }
    }

    private void deleteFriendship(){
        System.out.println("Select the id's of the users to remove their friendship: ");
        System.out.println("User 1 Id: ");
        String userName1 = in.nextLine();
        System.out.println("User 2 Id: ");
        String userName2 = in.nextLine();
        try{
            service.deleteFriendship(userName1,userName2);
            System.out.println("Friendship successfully removed!");

        }
        catch (Exception exception){
            System.out.println(exception.getMessage());
        }
    }

    private void showFriendships() throws SQLException {
        for (Friendship friendship : service.getFriendships())
            System.out.println(friendship);
    }
}

