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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class HelloUser {

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

    public HelloUser() throws SQLException {
    }

    @FXML
    private Button addFriendButton;

    @FXML
    private Text userUsername;

    @FXML
    private TextField username;

    @FXML
    private ListView<String> listOfFriendships;

    @FXML
    private ListView<String> listOfFriendRequests;

    @FXML
    private Text errorMessage;

    @FXML
    private Text errorMessageFriendship;

    @FXML
    private Text successMessage;

    @FXML
    private Button logOutButton;

    @FXML
    private TextField friendRequestField;

    @FXML
    private Button add;

    @FXML
    private Button deny;

    @FXML
    private Text errorMessageAdd;

    @FXML
    private Text successMessageAdd;

    @FXML
    private TextField removeTextField;

    @FXML
    private Button removeFriend;

    @FXML
    private Text succesRemoveFriend;

    @FXML
    private Text errorRemoveFriend;

    @FXML
    private void initialize() throws SQLException {
        //app
        DataSingleton data = DataSingleton.getInstance();
        userUsername.setText(data.getUser());
        userUsername.setTextAlignment(TextAlignment.CENTER);
        setOpacities();
        service.getFriendships().forEach( item -> {
            if(item.getStatus() == 1){
                if(Objects.equals(item.getId().getFirst(), userUsername.getText())){
                    listOfFriendships.getItems().add(item.getId().getSecond());
                }
                if(Objects.equals(item.getId().getSecond(), userUsername.getText())){
                    listOfFriendships.getItems().add(item.getId().getFirst());
                }
            }
        });
        service.getFriendships().forEach( item -> {
            if(item.getStatus() == 0){
                if(Objects.equals(userUsername.getText(), item.getId().getFirst()) || Objects.equals(userUsername.getText(), item.getId().getSecond())){
                    if(Objects.equals(userUsername.getText(), item.getId().getFirst())){
                        listOfFriendRequests.getItems().add(item.getId().getSecond());
                    }
                    else{
                        listOfFriendRequests.getItems().add(item.getId().getFirst());
                    }
                }

            }
        });


    }

    public void setOpacities(){
        errorMessageAdd.setOpacity(0);
        errorMessage.setOpacity(0);
        errorMessageFriendship.setOpacity(0);
        successMessageAdd.setOpacity(0);
        successMessage.setOpacity(0);
        succesRemoveFriend.setOpacity(0);
        errorRemoveFriend.setOpacity(0);
    }

    public void deleteFriend(ActionEvent event) throws SQLException {
        setOpacities();
        service.getFriendships().forEach( item -> {
            if(item.getStatus() == 1){
                if(Objects.equals(userUsername.getText(), item.getId().getFirst()) && Objects.equals(removeTextField.getText(), item.getId().getSecond())){
                    try {
                        service.deleteFriendship(userUsername.getText(), removeTextField.getText());
                        succesRemoveFriend.setOpacity(1);
                    } catch (RepositoryException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            if(item.getStatus() == 1){
                if(Objects.equals(userUsername.getText(), item.getId().getSecond()) && Objects.equals(removeTextField.getText(), item.getId().getFirst())){
                    try {
                        service.deleteFriendship(removeTextField.getText(), userUsername.getText());
                        succesRemoveFriend.setOpacity(1);
                    } catch (RepositoryException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
        if(succesRemoveFriend.getOpacity() == 0){
            errorRemoveFriend.setOpacity(1);
        }
    }
    public void acceptFriendRequest(ActionEvent event) throws IOException, SQLException {
        setOpacities();
        service.getFriendships().forEach( item -> {
            if(item.getStatus() == 0) {
                if(Objects.equals(userUsername.getText(), item.getId().getFirst()) && Objects.equals(friendRequestField.getText(), item.getId().getSecond())){
                    try {
                        service.increaseStatus(userUsername.getText(), friendRequestField.getText());
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    errorMessageAdd.setOpacity(0);
                    successMessageAdd.setOpacity(1);
                }
                if(Objects.equals(userUsername.getText(), item.getId().getSecond()) && Objects.equals(friendRequestField.getText(), item.getId().getFirst())){
                    try {
                        service.increaseStatus(friendRequestField.getText(), userUsername.getText());
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    errorMessageAdd.setOpacity(0);
                    successMessageAdd.setOpacity(1);
                }
            }
        });
        if(successMessageAdd.getOpacity() == 0){
            errorMessageAdd.setOpacity(1);
        }
    }

    public void denyFriendRequest(ActionEvent event) throws IOException, SQLException {
        setOpacities();
        service.getFriendships().forEach( item -> {
            if(item.getStatus() == 0) {
                if((Objects.equals(userUsername.getText(), item.getId().getFirst()) && Objects.equals(friendRequestField.getText(), item.getId().getSecond())) ||
                        (Objects.equals(userUsername.getText(), item.getId().getSecond()) && Objects.equals(friendRequestField.getText(), item.getId().getFirst()))){

                    String secondUser;
                    if(Objects.equals(userUsername.getText(), item.getId().getFirst())){
                        secondUser = item.getId().getSecond();
                    }
                    else{
                        secondUser = item.getId().getFirst();
                    }
                    try {
                        service.deleteFriendship(userUsername.getText(), secondUser);
                    } catch (RepositoryException e) {
                        throw new RuntimeException(e);
                    }
                    errorMessageAdd.setOpacity(0);
                    successMessageAdd.setOpacity(1);
                }
            }
        });
        if(successMessageAdd.getOpacity() == 0){
            errorMessageAdd.setOpacity(1);
        }
    }



    public void goToLogin(ActionEvent event) throws IOException {
        HelloApplication m = new HelloApplication();
        m.changeScene("hello-view.fxml");
    }

    public void addFriend(ActionEvent event) throws IOException, ValidationException, RepositoryException, InterruptedException, SQLException {
        setOpacities();
        int ok = 1;
        for(Friendship friendship : service.getFriendships()){
            if((Objects.equals(userUsername.getText(), friendship.getId().getFirst()) && Objects.equals(username.getText(), friendship.getId().getSecond()))
                    || (Objects.equals(userUsername.getText(), friendship.getId().getSecond()) && Objects.equals(username.getText(), friendship.getId().getFirst()))){
                errorMessageFriendship.setOpacity(0);
                errorMessage.setOpacity(0);
                successMessage.setOpacity(0);
                errorMessageFriendship.setOpacity(1);
                ok = 0;
            }
        }
        int k = 0;
        if(ok == 1){
            for (User user : service.getUsers()) {
                if(Objects.equals(username.getText(), user.getId())){
                    k = 1;
                }

                if(k == 1){
                    service.addFriendship(userUsername.getText(), username.getText());
                    errorMessageFriendship.setOpacity(0);
                    errorMessage.setOpacity(0);
                    successMessage.setOpacity(0);
                    successMessage.setOpacity(1);
                }
                else{
                    errorMessageFriendship.setOpacity(0);
                    errorMessage.setOpacity(0);
                    successMessage.setOpacity(0);
                    errorMessage.setOpacity(1);
                }

            }
        }
    }

}
