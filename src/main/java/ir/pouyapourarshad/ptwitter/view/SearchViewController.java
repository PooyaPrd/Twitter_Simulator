package ir.pouyapourarshad.ptwitter.view;

import ir.pouyapourarshad.ptwitter.controllers.modelControllers.PostController;
import ir.pouyapourarshad.ptwitter.controllers.modelControllers.AccountController;
import ir.pouyapourarshad.ptwitter.controllers.serviceControllers.SidebarController;
import ir.pouyapourarshad.ptwitter.models.posts.Hashtag;
import ir.pouyapourarshad.ptwitter.models.posts.Post;
import ir.pouyapourarshad.ptwitter.models.users.User;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.util.ArrayList;

import static ir.pouyapourarshad.ptwitter.controllers.pageControllers.SearchController.*;

public class SearchViewController {

    @FXML
    private ChoiceBox<?> searchByChoiceBox;

    @FXML
    private TextField searchField;

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
    private Label userFullName;

    @FXML
    private Circle userProfilePicture;

    @FXML
    private Label userUsername;

    @FXML
    private VBox resultsContainer;

    @FXML
    void initialize(){
        SidebarController.setupSidebarAccount(userFullName, userProfilePicture, userUsername,
                sidebarHomeButton, sidebarSearchButton, sidebarProfileButton, sidebarPostNewButton,
                sidebarLogoutButton);
        loadDefaultResult();
    }

    void loadDefaultResult(){
        resultsContainer.getChildren().clear();
        renderAccountResults("");
    }

    @FXML
    void searchButtonHandler(){
        String searchBy = searchByChoiceBox.getValue().toString();
        String query = searchField.getText();

        resultsContainer.getChildren().clear();

        switch (searchBy) {
            case "Accounts" -> renderAccountResults(query);
            case "Posts" -> renderPostResults(query);
            case "Hashtags" -> renderHashtagResults(query);
        }
    }

    private void renderHashtagResults(String query){
        ArrayList<Hashtag> resultHashtags = getHashtagResult(normalizeQuery(query));
        if (resultHashtags.isEmpty()){
            renderEmptyState("No posts found. The list is sleeping...");
            return;
        }
        for (Hashtag hashtag: resultHashtags){
            ArrayList<Post> posts = getPostByHashtag(hashtag);
            for (Post post: posts){
                resultsContainer.getChildren().add(createPostCard(post));
            }
        }
    }

    private void renderPostResults(String query){
        ArrayList<Post> resultPosts = getPostResult(normalizeQuery(query));
        if (resultPosts.isEmpty()){
            renderEmptyState("No posts found. The list is sleeping...");
            return;
        }
        for (Post post: resultPosts){
            resultsContainer.getChildren().add(createPostCard(post));
        }
    }
    private VBox createPostCard(Post post){
        return PostController.shortPostBoxBuilder(post);
    }

    private void renderEmptyState(String message) {
        VBox emptyBox = new VBox();
        emptyBox.setSpacing(8);
        emptyBox.setPadding(new Insets(24));
        emptyBox.getStyleClass().add("account-result-card");

        Label title = new Label("No Results");
        title.getStyleClass().add("result-name");

        Label description = new Label(message);
        description.getStyleClass().add("result-bio");

        emptyBox.getChildren().addAll(title, description);
        resultsContainer.getChildren().add(emptyBox);
    }

    private void renderAccountResults(String query){
        ArrayList<User> resultUsers = getAccountResult(normalizeQuery(query));
        if (resultUsers.isEmpty()){
            renderEmptyState("No accounts found. The list is having a quiet day!");
            return;
        }
        for (User user:resultUsers){
            resultsContainer.getChildren().add(AccountController.createAccountCard(user));
        }
    }




}
