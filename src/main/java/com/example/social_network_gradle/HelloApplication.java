package com.example.social_network_gradle;

import com.example.social_network_gradle.domain.friendship.Friendship;
import com.example.social_network_gradle.domain.friendship.StringPair;
import com.example.social_network_gradle.domain.user.User;
import com.example.social_network_gradle.domain.validators.FriendshipValidator;
import com.example.social_network_gradle.domain.validators.UserValidator;
import com.example.social_network_gradle.domain.validators.Validator;
import com.example.social_network_gradle.repository.Repository;
import com.example.social_network_gradle.repository.database.FriendshipDatabaseRepository;
import com.example.social_network_gradle.repository.database.UserDatabaseRepository;
import com.example.social_network_gradle.service.Service;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;

public class HelloApplication extends Application {


    private static Stage stage;
    @Override
    public void start(Stage primaryStage) throws IOException, SQLException {
        //conexuni
        String url = "jdbc:postgresql://localhost:5432/socialnetworkdatabase";
        String userName = "postgres";
        String password = "postgres";

        final Connection connection= DriverManager.getConnection(url,userName,password);
        Validator<User> userValidator = new UserValidator();
        Validator<Friendship> friendshipValidator = new FriendshipValidator();
        Repository<String, User> usersDatabaseRepository = new UserDatabaseRepository( userValidator, connection);
        Repository<StringPair, Friendship> friendshipDatabaseRepository = new FriendshipDatabaseRepository(friendshipValidator, connection);
        Service service = Service.getInstance(usersDatabaseRepository, friendshipDatabaseRepository);


        //app
        stage = primaryStage;
        primaryStage.setResizable(false);
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("hello-view.fxml")));
        primaryStage.setTitle("InstaHam");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


    public void changeScene(String fxml) throws IOException{
        Parent pane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxml)));
        stage.getScene().setRoot(pane);

    }

    public static void main(String[] args) {
        launch(args);
    }
}