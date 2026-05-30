package ir.pouyapourarshad.ptwitter.controllers.modelControllers;

import ir.pouyapourarshad.ptwitter.Main;
import ir.pouyapourarshad.ptwitter.models.storage.Database;
import ir.pouyapourarshad.ptwitter.models.users.Account;
import ir.pouyapourarshad.ptwitter.models.users.User;
import ir.pouyapourarshad.ptwitter.models.users.permiumuser.Blue;
import ir.pouyapourarshad.ptwitter.models.users.permiumuser.Gold;
import ir.pouyapourarshad.ptwitter.view.AccountPageViewController;
import ir.pouyapourarshad.ptwitter.view.FollowersListViewController;
import ir.pouyapourarshad.ptwitter.view.FollowingsListViewController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

import static ir.pouyapourarshad.ptwitter.controllers.serviceControllers.SceneManager.showError;
import static ir.pouyapourarshad.ptwitter.controllers.serviceControllers.SceneManager.showInfo;

public class AccountController {
    static Database database = Database.getInstance();
    static Account currentAccount = database.getCurrentAccount();

    public static void updateCurrentAccount(){
        currentAccount = database.getCurrentAccount();
    }

    public static boolean getFollowButtonState(User user){
        User me = (User)database.getCurrentAccount();
        for (int id:me.getFollowings()){
            if (database.getUsers().get(id).getUsername().equals(user.getUsername())){
                return true;
            }
        }
        return false;
    }


    public static void followUser(User user){
        User me = (User)database.getCurrentAccount();
        me.addFollowing(user);
        user.addFollower(me);
    }
    public static void unFollowUser(User user){
        User me = (User)database.getCurrentAccount();
        me.removeFromFollowings(user);
        user.removeFromFollowers(me);
    }

    public static boolean isUserMySelf(User user){
        return user.getUsername().equals(currentAccount.getUsername());
    }

    public static void setProfile(Account account, ir.pouyapourarshad.ptwitter.models.media.image.Image image){
        account.setProfilePicture(image);
    }

    public static void openAccountPage(User user){
        Stage stage = new Stage();
        Scene scene;
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("AccountPage.fxml"));

        AccountPageViewController controller = new AccountPageViewController();
        controller.setUser(user);
        loader.setController(controller);
        stage.initModality(Modality.APPLICATION_MODAL);


        try{
            scene = new Scene(loader.load());
        } catch (IOException e){
            e.printStackTrace();
            return;
        }

        stage.setTitle("Account");
        stage.setScene(scene);
        stage.show();
    }

    public static void setupAccountPage(User user, Label displayNameLabel, Label usernameLabel, Label bioLabel,
                                        Button followersButton, Button followingButton, Label balanceLabel,
                                        Circle profilePicture, Rectangle verifiedBadgeLabel, Label currentLabel){
        loadUserData(user, displayNameLabel, usernameLabel, bioLabel, followersButton, followingButton,
                balanceLabel, profilePicture, verifiedBadgeLabel, currentLabel);

    }
    public static void setupAccountPage(Label displayNameLabel, Label usernameLabel, Label bioLabel,
                                        Button followersButton, Button followingButton, Label balanceLabel,
                                        Circle profilePicture, Rectangle verifiedBadgeLabel, Label currentLabel){
        loadUserData((User)database.getCurrentAccount(), displayNameLabel, usernameLabel, bioLabel, followersButton, followingButton,
                balanceLabel, profilePicture, verifiedBadgeLabel, currentLabel);

    }

    public static void loadUserData(User user, Label displayNameLabel, Label usernameLabel, Label bioLabel,
                                    Button followersButton, Button followingButton,
                                    Label balanceLabel, Circle profilePicture, Rectangle verifiedBadgeLabel,  Label currentLabel){
        displayNameLabel.setText(user.getFullName());
        usernameLabel.setText("@"+user.getUsername());
        bioLabel.setText(user.getBiography());
//        joinDateLabel.setText("Member since 2022");
        followersButton.setText(Integer.toString(user.getFollowers().toArray().length) + " Followers");
        followingButton.setText(Integer.toString(user.getFollowings().toArray().length) + " Followings");
        if (balanceLabel != null){
            balanceLabel.setText("$" + Integer.toString(user.getCredit()));
        }
        Image image = new Image(user.getProfilePicture().getPathUrl());
        profilePicture.setStyle("");
        profilePicture.setFill(new ImagePattern(image));
        System.out.println(user.getBadges().getPath());
        verifiedBadgeLabel.setFill(new ImagePattern(new Image(Main.class.getResourceAsStream(user.getBadges().getPath()))));
        if (currentLabel!=null){
            currentLabel.setText("Current token: " + user.getToken());
        }
    }

    public static void loadUserPosts(User user, FlowPane postsFlowPane){
        postsFlowPane.getChildren().clear();

        ArrayList<Integer> postsId = user.getPostsList();

        for (int i = 0; i < postsId.size() ; i++) {
            if (database.getPostById(postsId.get(i)).isDeleted()){
                continue;
            }
            postsFlowPane.getChildren().add(PostController.shortPostBoxBuilder(database.getPostById(postsId.get(i))));
        }
    }
    public static void loadUserPosts(FlowPane postsFlowPane){
       loadUserPosts((User)currentAccount, postsFlowPane);
    }

    public static boolean purchasePlan(String planName, int price) {
        User user  = (User)database.getCurrentAccount();
        int userCredit = user.getCredit();
        if (userCredit >= price) {
            userCredit -= price;
            if (planName.equals("Blue")){
                makeBlue(user);
            }else if(planName.equals("Gold")){
                makeGold(user);
            }
            ((User)database.getCurrentAccount()).setCredit(userCredit);
            showInfo("Purchase Successful", planName + " plan activated successfully.");
            return true;
        } else {
            showError("Insufficient Balance", "Your balance is not enough to buy the " + planName + " plan.");
            return false;
        }
    }

    public static void makeBlue(User user){
        Blue newUser = new Blue(user.getId(), user.getUsername(),
                user.getPassword(), user.getFullName(), user.getBirthDay(),
                user.getEmail(), user.getPhoneNumber(), user.getProfilePicture(),
                user.getJoinTime(), user.getBiography());
        copyUser(user, newUser);
    }
    public static void makeGold(User user){
        Gold newUser = new Gold(user.getId(), user.getUsername(),
                user.getPassword(), user.getFullName(), user.getBirthDay(),
                user.getEmail(), user.getPhoneNumber(), user.getProfilePicture(),
                user.getJoinTime(), user.getBiography());
        copyUser(user, newUser);
    }

    public static void copyUser(User user, User newUser){
        newUser.setPostsList(user.getPostsList());
        newUser.setLikedPosts(user.getLikedPosts());
        newUser.setFollowers(user.getFollowers());
        newUser.setFollowings(user.getFollowings());
        newUser.addFavoriteHashtags(user.getFavoriteHashtags());
        newUser.setCredit(user.getCredit());
        newUser.setToken(user.getToken());
        database.setUser(user.getId(), newUser);
        database.setCurrentAccount(database.getUserById(user.getId()));
        updateCurrentAccount();
    }

    public static int buyCredit(){
        User user = (User)database.getCurrentAccount();
        System.out.println(database.getCurrentAccount().getClass().toString());
        if (user.getCredit() >= 1){
            user.setCredit(user.getCredit() - 1);
            user.setToken(user.getToken()+1000);
            return user.getToken();
        }
        return -1;
    }
    public static int getCredit(){
        return ((User) database.getCurrentAccount()).getCredit();
    }

    public static ArrayList<User> getFollowers(User user){
        ArrayList<User> result = new ArrayList<>();
        ArrayList<Integer> followersId = user.getFollowers();
        for (int id:followersId){
            result.add(database.getUserById(id));
        }
        return result;
    }
    public static ArrayList<User> getFollowings(User user){
        ArrayList<User> result = new ArrayList<>();
        ArrayList<Integer> followersId = user.getFollowings();
        for (int id:followersId){
            result.add(database.getUserById(id));
        }
        return result;
    }

    public static VBox createAccountCard(User user){
        VBox card = new VBox();
        card.setSpacing(14);
        card.getStyleClass().add("account-result-card");

        HBox contentRow = new HBox();
        contentRow.setSpacing(14);

        Circle avatarCircle = new Circle(28);
        Image image = new Image(user.getProfilePicture().getPathUrl());
        try{
            avatarCircle.setFill(new ImagePattern(image));
        } catch (Exception e){
//            e.printStackTrace();
            System.out.println("Error on set people avatar Circle");
        }

        VBox infoBox = new VBox();
        infoBox.setSpacing(6);
        HBox.setHgrow(infoBox, Priority.ALWAYS);

        HBox nameRow = new HBox();
        nameRow.setSpacing(8);

        Label fullNameLabel = new Label(user.getFullName());
        fullNameLabel.getStyleClass().add("result-name");

        Label usernameLabel = new Label("@" + user.getUsername());
        usernameLabel.getStyleClass().add("result-username");

        nameRow.getChildren().addAll(fullNameLabel, usernameLabel);

        Label bioLabel = new Label(user.getBiography());
        bioLabel.setWrapText(true);
        bioLabel.getStyleClass().add("result-bio");

        infoBox.getChildren().addAll(nameRow, bioLabel);
        contentRow.getChildren().addAll(avatarCircle, infoBox);
        card.getChildren().add(contentRow);

        card.setOnMouseClicked(e -> AccountController.openAccountPage(user));
        return card;
    }

    public static void showFollowerPage(User user){
        Stage stage = new Stage();
        Scene scene;
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("FollowersList.fxml"));
        stage.initModality(Modality.APPLICATION_MODAL);
        try{
            scene = new Scene(loader.load());
        } catch (IOException e){
            e.printStackTrace();
            return;
        }
        FollowersListViewController controller = loader.getController();
        controller.renderFollowers(user);

        stage.setTitle("Followers Page");
        stage.setScene(scene);
        stage.show();
    }
    public static void showFollowingsPage(User user){
        Stage stage = new Stage();
        Scene scene;
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("FollowingsList.fxml"));
        stage.initModality(Modality.APPLICATION_MODAL);
        try{
            scene = new Scene(loader.load());
        } catch (IOException e){
            e.printStackTrace();
            return;
        }
        FollowingsListViewController controller = loader.getController();
        controller.renderFollowings(user);

        stage.setTitle("Followings Page");
        stage.setScene(scene);
        stage.show();
    }

}

