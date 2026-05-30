package ir.pouyapourarshad.ptwitter.view;

import ir.pouyapourarshad.ptwitter.controllers.modelControllers.AccountController;
import ir.pouyapourarshad.ptwitter.models.users.User;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class FollowersListViewController {
    @FXML
    private VBox resultsContainer;
    @FXML
    private Label title;

    public void renderFollowers(User user){
        title.setText("Followers of " + user.getFullName());
        resultsContainer.getChildren().clear();
        ArrayList<User> users =  AccountController.getFollowers(user);
        for (User user_t: users){
            resultsContainer.getChildren().add(AccountController.createAccountCard(user_t));
        }
    }
}
