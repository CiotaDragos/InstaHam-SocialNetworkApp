package com.example.social_network_gradle;

import com.example.social_network_gradle.domain.friendship.Friendship;
import com.example.social_network_gradle.domain.friendship.StringPair;
import com.example.social_network_gradle.domain.user.User;
import com.example.social_network_gradle.domain.validators.FriendshipValidator;
import com.example.social_network_gradle.domain.validators.UserValidator;
import com.example.social_network_gradle.domain.validators.ValidationException;
import com.example.social_network_gradle.domain.validators.Validator;
import com.example.social_network_gradle.repository.Repository;
import com.example.social_network_gradle.repository.RepositoryException;
import com.example.social_network_gradle.repository.database.FriendshipDatabaseRepository;
import com.example.social_network_gradle.repository.database.UserDatabaseRepository;
import com.example.social_network_gradle.service.Service;
import com.example.social_network_gradle.service.ServiceException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class HelloRegister {

    //service
    String url = "jdbc:postgresql://localhost:5432/socialnetworkdatabase";
    String userName = "postgres";
    String password = "postgres";

    final Connection connection= DriverManager.getConnection(url,userName,password);
    Validator<User> userValidator = new UserValidator();
    Validator<Friendship> friendshipValidator = new FriendshipValidator();
    Repository<String, User> usersDatabaseRepository = new UserDatabaseRepository( userValidator, connection);
    Repository<StringPair, Friendship> friendshipDatabaseRepository = new FriendshipDatabaseRepository(friendshipValidator, connection);
    Service service = Service.getInstance(usersDatabaseRepository, friendshipDatabaseRepository);


    //buttons
    @FXML
    private Button goBackButton;
    @FXML
    private ImageView imageViewLeft;
    @FXML
    private ImageView imageViewRight;
    @FXML
    private Label wrongLogIn;
    @FXML
    private TextField firstName;
    @FXML
    private TextField lastName;
    @FXML
    private TextField email;
    @FXML
    private TextField yearOfBirth;
    @FXML
    private TextField username;
    @FXML
    private Text errorMessage;
    @FXML
    private Text successMessage;
    @FXML
    private Button signInButton;

    public HelloRegister() throws SQLException {
    }

    @FXML
    private void initialize() throws SQLException {
        //app
        Image imageLeft = new Image("D:\\School\\Anul 2 Semestrul 1\\MAP\\Social_Network_Gradle\\src\\images\\2641099.gif");
        imageViewLeft.setImage(imageLeft);
        Image imageRight = new Image("D:\\School\\Anul 2 Semestrul 1\\MAP\\Social_Network_Gradle\\src\\images\\cane).gif");
        imageViewRight.setImage(imageRight);
        errorMessage.setOpacity(0);
        successMessage.setOpacity(0);
    }

    public void goToLogin(ActionEvent event) throws IOException {
        HelloApplication m = new HelloApplication();
        m.changeScene("hello-view.fxml");
    }

    public void signUp(ActionEvent event) throws IOException, ValidationException, ServiceException, RepositoryException, InterruptedException, SQLException {
        int ok = 1;
        for (User user : service.getUsers()){
            if(username.getText().equals(user.getId())){
                //exista deja
                ok = 0;
                username.setText(null);
                firstName.setText(null);
                lastName.setText(null);
                email.setText(null);
                yearOfBirth.setText(null);

                successMessage.setOpacity(0);
                errorMessage.setOpacity(1);
            }
        }
        //adaugam user-ul nou
        if(ok == 1) {
            errorMessage.setOpacity(0);
            successMessage.setOpacity(1);
            service.addUser(username.getText(), firstName.getText(), lastName.getText(), email.getText(), yearOfBirth.getText(), "Romania", "Cluj", "ClujNapoca", "Alunita", "21");

            username.setText(null);
            firstName.setText(null);
            lastName.setText(null);
            email.setText(null);
            yearOfBirth.setText(null);

            Thread.sleep(3000);

            successMessage.setOpacity(0);
        }
    }
}
