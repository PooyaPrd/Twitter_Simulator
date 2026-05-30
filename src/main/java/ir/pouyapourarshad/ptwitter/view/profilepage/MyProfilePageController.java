package ir.pouyapourarshad.ptwitter.view.profilepage;

import ir.pouyapourarshad.ptwitter.Main;
import ir.pouyapourarshad.ptwitter.controllers.modelControllers.AccountController;
import ir.pouyapourarshad.ptwitter.controllers.serviceControllers.SceneManager;
import ir.pouyapourarshad.ptwitter.controllers.serviceControllers.SidebarController;
import ir.pouyapourarshad.ptwitter.models.storage.Database;
import ir.pouyapourarshad.ptwitter.models.users.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import static ir.pouyapourarshad.ptwitter.controllers.modelControllers.AccountController.purchasePlan;
import static ir.pouyapourarshad.ptwitter.controllers.modelControllers.AccountController.updateCurrentAccount;
import static ir.pouyapourarshad.ptwitter.controllers.serviceControllers.SceneManager.showError;

public class MyProfilePageController {
    private static final Database database = Database.getInstance();

    @FXML private Circle profilePictureCircle;
    @FXML private Label displayNameLabel;
    @FXML private Label usernameLabel;
    @FXML private Label bioLabel;
    @FXML private Label joinDateLabel;
    @FXML private Label balanceValueLabel;
    @FXML private Label sidebarUserFullName;
    @FXML private Label sidebarUsername;
    @FXML private Label TokenBuyStatement;
    @FXML private Circle sidebarUserProfilePicture;

    @FXML private Button followersButton;
    @FXML private Button followingButton;

    @FXML private FlowPane postsFlowPane;
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
    private Rectangle verifiedBadgeCircle;
    @FXML
    private Label currentTokenLabel;

    @FXML
    public void initialize() {
        updateCurrentAccount();
        AccountController.setupAccountPage(displayNameLabel,
                usernameLabel, bioLabel, followersButton, followingButton,
                balanceValueLabel, profilePictureCircle, verifiedBadgeCircle, currentTokenLabel);
        SidebarController.setupSidebarAccount(sidebarUserFullName,
                sidebarUserProfilePicture, sidebarUsername,  sidebarHomeButton,sidebarSearchButton
                ,sidebarProfileButton,sidebarPostNewButton,sidebarLogoutButton);
        AccountController.loadUserPosts(postsFlowPane);
    }
    @FXML
    private void editProfileHandle(){
        SceneManager.showOnSameStage("EditInfo.fxml", "| Edit Profile.");
    }

    @FXML
    private void handleFollowers() {
        AccountController.showFollowerPage((User) database.getCurrentAccount());
    }

    @FXML
    private void handleFollowing() {
        AccountController.showFollowingsPage((User) database.getCurrentAccount());
    }

    @FXML
    private void buyBluePlanButton() {
        boolean success = purchasePlan("Blue", 9);
        if (success) {
            balanceValueLabel.setText("$" + AccountController.getCredit());
            verifiedBadgeCircle.setFill(new ImagePattern(new Image(Main.class.getResourceAsStream("assets/images/B.png"))));
        }

    }

    @FXML
    private void buyGoldPlanButton() {
        boolean success = purchasePlan("Gold", 19);
        if (success) {
            balanceValueLabel.setText("$" + AccountController.getCredit());
            verifiedBadgeCircle.setFill(new ImagePattern(new Image(Main.class.getResourceAsStream("assets/images/G.png"))));
        }

    }

    @FXML
    void buyTokenHandle(){
        int res = AccountController.buyCredit();
        if (res > 0){
            currentTokenLabel.setText("Current Token: " + res);
            balanceValueLabel.setText("$" + AccountController.getCredit());
            TokenBuyStatement.setText("You've bought token.");
        } else {
            TokenBuyStatement.setText("You dont have even 1$ credit!");
        }
    }





}
