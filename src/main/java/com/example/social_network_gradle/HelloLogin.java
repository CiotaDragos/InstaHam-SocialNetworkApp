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
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import org.controlsfx.control.action.Action;

import com.example.social_network_gradle.HelloApplication;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class HelloLogin {

    //conexuni
    String url = "jdbc:postgresql://localhost:5432/socialnetworkdatabase";
    String userName = "postgres";
    String password = "postgres";

    final Connection connection = DriverManager.getConnection(url, userName, password);
    Validator<User> userValidator = new UserValidator();
    Validator<Friendship> friendshipValidator = new FriendshipValidator();
    Repository<String, User> usersDatabaseRepository = new UserDatabaseRepository(userValidator, connection);
    Repository<StringPair, Friendship> friendshipDatabaseRepository = new FriendshipDatabaseRepository(friendshipValidator, connection);
    Service service = Service.getInstance(usersDatabaseRepository, friendshipDatabaseRepository);


    @FXML
    private Button signUpButton;
    @FXML
    private Button loginButton;
    @FXML
    private Text errorMessage;
    @FXML
    private ImageView imageViewLeft;
    @FXML
    private ImageView imageViewRight;
    @FXML
    private TextField username;
    @FXML
    private TextField email;

    public HelloLogin() throws SQLException {
    }

    @FXML
    private void initialize() throws SQLException {
        //app
        Image imageLeft = new Image("D:\\School\\Anul 2 Semestrul 1\\MAP\\Social_Network_Gradle\\src\\images\\2641099.gif");
        imageViewLeft.setImage(imageLeft);
        Image imageRight = new Image("D:\\School\\Anul 2 Semestrul 1\\MAP\\Social_Network_Gradle\\src\\images\\cane).gif");
        imageViewRight.setImage(imageRight);
        errorMessage.setOpacity(0);
    }

    public void goToRegister(ActionEvent event) throws IOException {
        HelloApplication m = new HelloApplication();
        m.changeScene("register.fxml");
    }

    public void logIn(ActionEvent event) throws IOException, SQLException {
        DataSingleton data = DataSingleton.getInstance();
        int ok = 0;
        for (User user : service.getUsers()) {
            if (username.getText().equals(user.getId()) && email.getText().equals(user.getEmail())){
                errorMessage.setOpacity(0);
                ok = 1;
                data.setUser(username.getText());
                HelloApplication m = new HelloApplication();
                m.changeScene("user.fxml");
            }
        }
        if(ok == 0){
            errorMessage.setOpacity(1);
            username.setText(null);
            email.setText(null);
        }
    }
}