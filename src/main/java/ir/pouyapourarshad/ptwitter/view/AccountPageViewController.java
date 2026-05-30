package ir.pouyapourarshad.ptwitter.view;

import ir.pouyapourarshad.ptwitter.controllers.modelControllers.AccountController;
import ir.pouyapourarshad.ptwitter.models.users.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class AccountPageViewController {

    @FXML
    private Label bioLabel;

    @FXML
    private Label displayNameLabel;

    @FXML
    private Button followersButton;

    @FXML
    private Button followingButton;

    @FXML
    private Label joinDateLabel;

    @FXML
    private FlowPane postsFlowPane;

    @FXML
    private Circle profilePictureCircle;

    @FXML
    private Label usernameLabel;

    @FXML
    private Rectangle verifiedBadgeCircle;
    @FXML private Button followButton;

    private User user;
    public void setUser(User user){
        this.user = user;
    }

    @FXML
    void initialize(){
        AccountController.setupAccountPage(user, displayNameLabel, usernameLabel, bioLabel, followersButton,
                followingButton, null , profilePictureCircle, verifiedBadgeCircle, null);
        AccountController.loadUserPosts(user, postsFlowPane);
        setupFollowButton();
    }

    private void updateFollowersAndFollowings(){
        followersButton.setText(user.getFollowers().toArray().length + " Followers");
        followingButton.setText(user.getFollowings().toArray().length + " Followings");
    }

    private void setupFollowButton(){
        if (AccountController.isUserMySelf(user)){
            followButton.setDisable(true);
            followButton.setOpacity(0);
            return;
        }
        if(AccountController.getFollowButtonState(user)){
            followButton.setText("Unfollow");
            followButton.getStyleClass().clear();
            followButton.getStyleClass().add("ghost-button");
            followButton.setOnMouseClicked(e->unFollowHandler());
        }else{
            followButton.setText("Follow");
            followButton.getStyleClass().clear();
            followButton.getStyleClass().add("primary-save-button");
            followButton.setOnMouseClicked(e -> followHandler());
        }
    }

    @FXML
    void handleFollowers(ActionEvent event) {
        AccountController.showFollowerPage(user);
    }

    @FXML
    void handleFollowing(ActionEvent event) {
        AccountController.showFollowingsPage(user);
    }

    @FXML
    void reportButtonHandler(){

    }

    private void followHandler(){
        System.out.println("user followed: " + user.getUsername());
        AccountController.followUser(user);
        setupFollowButton();
        updateFollowersAndFollowings();
    }
    private void unFollowHandler(){
        System.out.println("user unfollowed: " + user.getUsername());
        AccountController.unFollowUser(user);
        setupFollowButton();
        updateFollowersAndFollowings();
    }



}
