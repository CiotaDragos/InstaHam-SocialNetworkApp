package com.example.social_network_gradle;

import com.example.social_network_gradle.domain.Entity;
import com.example.social_network_gradle.domain.friendship.Friendship;
import com.example.social_network_gradle.domain.friendship.StringPair;
import com.example.social_network_gradle.domain.user.User;
import com.example.social_network_gradle.domain.validators.FriendshipValidator;
import com.example.social_network_gradle.domain.validators.UserValidator;
import com.example.social_network_gradle.domain.validators.Validator;
import com.example.social_network_gradle.repository.Repository;

import com.example.social_network_gradle.repository.database.FriendshipDatabaseRepository;
import com.example.social_network_gradle.repository.database.UserDatabaseRepository;
import com.example.social_network_gradle.repository.file.AbstractFileRepository;
import com.example.social_network_gradle.repository.file.FriendshipFile;
import com.example.social_network_gradle.repository.file.UserFile;
import com.example.social_network_gradle.repository.memory.InMemoryFriendshipRepository;
import com.example.social_network_gradle.repository.memory.InMemoryRepository;
import com.example.social_network_gradle.service.Service;
import com.example.social_network_gradle.ui.ConsoleInterface;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;


public class Main {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/socialnetworkdatabase";
        String userName = "postgres";
        String password = "postgres";

        try {
            final Connection connection= DriverManager.getConnection(url,userName,password);
            Validator<User> userValidator = new UserValidator();
            Validator<Friendship> friendshipValidator = new FriendshipValidator();
            Repository usersDatabaseRepository = new UserDatabaseRepository( userValidator, connection);
            Repository friendshipDatabaseRepository = new FriendshipDatabaseRepository(friendshipValidator, connection);
            //Service service = new Service(usersDatabaseRepository, friendshipDatabaseRepository);

            //ConsoleInterface ui = new ConsoleInterface(service);
            //ui.run();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        //initial repo
        //Repository<String, User> usersRepository = new InMemoryRepository<>(userValidator);
        //Repository<StringPair, Friendship> friendshipRepository = new InMemoryFriendshipRepository<>(friendshipValidator);

        //file repo
        //Validator<User> userValidator = new UserValidator();
        //Validator<Friendship> friendshipValidator = new FriendshipValidator();

        //Repository<String, User> usersFileRepository = new UserFile("D:\\School\\Anul 2 Semestrul 1\\MAP\\Social Network File\\Social Network\\src\\repository\\file\\Users.txt", userValidator);
        //Repository<StringPair, Friendship> friendshipFileRepository = new FriendshipFile("D:\\School\\Anul 2 Semestrul 1\\MAP\\Social Network File\\Social Network\\src\\repository\\file\\Friendships.txt", friendshipValidator);
        //Service service = new Service(usersFileRepository, friendshipFileRepository);

        //ConsoleInterface ui = new ConsoleInterface(service);
        //ui.run();
    }
}