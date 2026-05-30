package ir.pouyapourarshad.ptwitter.view;

import ir.pouyapourarshad.ptwitter.controllers.modelControllers.PostController;
import ir.pouyapourarshad.ptwitter.controllers.pageControllers.HomeController;
import ir.pouyapourarshad.ptwitter.controllers.serviceControllers.SidebarController;
import ir.pouyapourarshad.ptwitter.models.posts.Post;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.FocusModel;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;

import java.util.ArrayList;

public class HomeViewController {

    @FXML
    private Label userFullName;

    @FXML
    private Circle userProfilePicture;

    @FXML
    private Label userUsername;

    @FXML
    private Button sidebarHomeButton;

    @FXML
    private Button sidebarLogoutButton;

    @FXML
    private Button sidebarPostNewButton;

    @FXML
    private Button sidebarProfileButton;

    @FXML
    private Button sidebarSearchButton;

    @FXML
    private VBox postsContainer;


    @FXML
    private void initialize(){
        SidebarController.setupSidebarAccount(userFullName, userProfilePicture, userUsername,sidebarHomeButton,
                sidebarSearchButton, sidebarProfileButton, sidebarPostNewButton, sidebarLogoutButton);
        renderPosts();
    }

    private void renderPosts(){
        ArrayList<Post> posts = HomeController.getPostsByInterest();
        postsContainer.getChildren().clear();

        if (posts.isEmpty()){
            renderEmptyState();
            return;
        }

        for (Post post : posts){
            postsContainer.getChildren().add(PostController.longPostBoxBuilder(post));
        }
    }

    private void renderEmptyState(){
        Label label = new Label("What an empty house! Maybe it’s time to get married!");
        label.setFont(Font.font(16));
        postsContainer.getChildren().add(label);
    }

}
