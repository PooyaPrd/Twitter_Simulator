package ir.pouyapourarshad.ptwitter.view;

import ir.pouyapourarshad.ptwitter.controllers.modelControllers.AccountController;
import ir.pouyapourarshad.ptwitter.controllers.pageControllers.SearchController;
import ir.pouyapourarshad.ptwitter.controllers.serviceControllers.AuthController;
import ir.pouyapourarshad.ptwitter.models.users.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import javax.swing.*;
import java.util.ArrayList;

public class UsersPageViewController {
    @FXML private VBox resultsContainer;

    @FXML
    public void initialize (){
        ArrayList<User> users =  SearchController.getAccountResult("");
        resultsContainer.getChildren().clear();
        Button logout = new Button("LogOut");
        logout.setOnMouseClicked(e -> handleLogout());
        resultsContainer.getChildren().add(logout);
        for (User user: users){
            VBox card = AccountController.createAccountCard(user);
            Button reportButton = new Button();
            reportButton.setText("Block");
            reportButton.getStyleClass().add("ghost-button");
            reportButton.setOnMouseClicked(e -> blockUser(user));
            ((HBox)card.getChildren().get(0)).getChildren().add(reportButton);
            resultsContainer.getChildren().add(card);
        }
    }
    private void blockUser(User user){
        user.setIsLock(true);
        System.out.println();
    }
    private void handleLogout(){
        AuthController.handleLogOut();
    }
}
