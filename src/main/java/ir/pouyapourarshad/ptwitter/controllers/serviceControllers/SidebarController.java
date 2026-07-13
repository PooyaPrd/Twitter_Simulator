package ir.pouyapourarshad.ptwitter.controllers.serviceControllers;

import ir.pouyapourarshad.ptwitter.models.storage.Database;
import ir.pouyapourarshad.ptwitter.models.users.Account;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

public class SidebarController {
    static Database database = Database.getInstance();
    static Account currentAccount = database.getCurrentAccount();

    public static void setupSidebarAccount(Label userFullName, Circle userProfilePicture,
    Label userUsername,Button handleHomeButton,
    Button handleSearchButton, Button handleProfileButton, Button handlePostNewButton
    , Button handleLogOut){

        userFullName.setText(currentAccount.getFullName());
        userUsername.setText(currentAccount.getUsername());
        Image image = new Image(currentAccount.getProfilePicture().getPathUrl());
        userProfilePicture.setStyle("");
        userProfilePicture.setFill(new ImagePattern(image));

        handleHomeButton.setOnAction(e -> handleHomeButton());
        handleProfileButton.setOnAction(e -> handleProfileButton());
        handlePostNewButton.setOnAction(e -> handlePostNewButton());
        handleSearchButton.setOnAction(e -> handleSearchButton());
        handleLogOut.setOnAction(e -> handleLogOut());
    }

    public static void handleHomeButton(){
        SceneManager.showOnSameStage("Home.fxml", "");
    }
    public static void handleProfileButton(){
        SceneManager.showOnSameStage("MyProfilePage.fxml", "| Account Management");
    }
    public static void handleLogOut(){
        AuthController.handleLogOut();
    }

    public static void handlePostNewButton(){
        SceneManager.showOnSameStage("PostNew.fxml", "| What do you think?");
    }
    public static void handleSearchButton(){
        SceneManager.showOnSameStage("Search.fxml", "| Search!");
    }
}
