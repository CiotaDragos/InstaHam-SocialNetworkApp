package com.example.social_network_gradle.service;

import com.example.social_network_gradle.domain.friendship.Friendship;
import com.example.social_network_gradle.domain.friendship.StringPair;
import com.example.social_network_gradle.domain.user.Address;
import com.example.social_network_gradle.domain.user.User;
import com.example.social_network_gradle.domain.validators.ValidationException;
import com.example.social_network_gradle.repository.Repository;
import com.example.social_network_gradle.repository.RepositoryException;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class Service {

    private static Service service = null;

    private final Repository<String, User> userRepository;

    private final Repository<StringPair, Friendship> friendshipRepository;


    private Service(Repository<String, User> userRepository, Repository<StringPair, Friendship> friendshipRepository) {
        this.userRepository = userRepository;
        this.friendshipRepository = friendshipRepository;
    }

    public static synchronized Service getInstance(Repository<String, User> userRepository, Repository<StringPair, Friendship> friendshipRepository){
        if(service == null){
            service = new Service(userRepository, friendshipRepository);
        }
        return service;
    }
    public void addUser(String userName, String firstName, String lastName, String email, String birthDate, String country, String county, String city, String street, String streetNumber) throws ValidationException, RepositoryException, ServiceException {
        Address adress = new Address(country, county, city, street, Long.parseLong(streetNumber));
        User newUser = new User(userName, firstName, lastName, email, birthDate, adress);
        if (userRepository.save(newUser) != null) {
            throw new ServiceException("The user with this ID already exists");
        }
    }

    public void deleteUser(String userName) throws RepositoryException, SQLException {
        userRepository.delete(userName);
        Iterable<Friendship> friendships = friendshipRepository.findAll();
        for(Friendship friendship : friendships){
            if(Objects.equals(friendship.getId().getFirst(), userName)){
                friendshipRepository.delete(friendship.getId());
            }
        }
        for(Friendship friendship : friendships){
            if(Objects.equals(friendship.getId().getSecond(), userName)){
                friendshipRepository.delete(friendship.getId());
            }
        }

    }

    public Iterable<User> getUsers() throws SQLException {
        return userRepository.findAll();
    }

    public void addFriendship(String userName1, String userName2) throws ValidationException, RepositoryException {
        Friendship newFriendship = new Friendship(userName1, userName2);
        friendshipRepository.save(newFriendship);
    }

    public void deleteFriendship(String userName1, String userName2) throws RepositoryException {
        Friendship newFriendship = new Friendship(userName1, userName2);
        friendshipRepository.delete(newFriendship.getId());
    }

    public Iterable<Friendship> getFriendships() throws SQLException {
        return friendshipRepository.findAll();
    }

    public void increaseStatus(String a , String b) throws SQLException {
        friendshipRepository.increaseStatus(a,b);
    }

}
