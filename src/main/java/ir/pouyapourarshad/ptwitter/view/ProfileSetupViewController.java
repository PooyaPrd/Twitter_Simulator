package ir.pouyapourarshad.ptwitter.view;

import ir.pouyapourarshad.ptwitter.controllers.serviceControllers.AuthController;
import ir.pouyapourarshad.ptwitter.controllers.serviceControllers.SceneManager;
import ir.pouyapourarshad.ptwitter.models.users.Account;
import ir.pouyapourarshad.ptwitter.states.SetupProfileStates;
import ir.pouyapourarshad.ptwitter.models.posts.Hashtag;
import ir.pouyapourarshad.ptwitter.models.storage.Database;
import ir.pouyapourarshad.ptwitter.models.users.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.HashSet;

public class ProfileSetupViewController {

    private static final Database database = Database.getInstance();
    @FXML
    private TextField fullNameField;

    @FXML
    private TextArea bioArea;

    @FXML
    private Label profileMessageLabel;

    @FXML
    private FlowPane hashtagsFlowPane;

    @FXML
    private Circle profileCircle;

    @FXML
    private Label plusOnProfile;

    private final HashSet<String> selectedHashtags = new HashSet<>();
    private File selectedProfile;

    @FXML
    public void initialize() {
        String fullName = database.getCurrentAccount().getFullName();
        if (fullName == null){
            fullName = "P User!";
        }
        fullNameField.setText(database.getCurrentAccount().getFullName());
        loadHashtagChips();
    }

    private void loadHashtagChips() {
        hashtagsFlowPane.getChildren().clear();
        for (Hashtag hashtag : database.getHashtags()) {
            Button chip = new Button(hashtag.getTitle());
            chip.getStyleClass().add("hashtag-chip");
            chip.setOnAction(event -> toggleHashtag(chip, hashtag.getTitle()));

            hashtagsFlowPane.getChildren().add(chip);
        }
    }

    private void toggleHashtag(Button chip, String hashtagText) {
        if (selectedHashtags.contains(hashtagText)) {
            selectedHashtags.remove(hashtagText);
            chip.getStyleClass().remove("hashtag-chip-selected");
        } else {
            selectedHashtags.add(hashtagText);
            if (!chip.getStyleClass().contains("hashtag-chip-selected")) {
                chip.getStyleClass().add("hashtag-chip-selected");
            }
        }
    }

    @FXML
    private void handleChoosePhoto() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Profile Photo");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );

        selectedProfile = fileChooser.showOpenDialog(null);

        if (selectedProfile != null) {
            profileMessageLabel.setStyle("-fx-text-fill: #8b949e;");
            profileMessageLabel.setText("Selected photo: " + selectedProfile.getName());
            Image image = new Image(selectedProfile.toURI().toString());
            profileCircle.setFill(new ImagePattern(image));
            plusOnProfile.setOpacity(0);
        }
    }

    @FXML
    private void handleSkip() {
        profileMessageLabel.setText("Sorry but at P you cant skip anything!");
        System.out.println("Skipped profile setup.");
    }

    @FXML
    private void handleSaveAndContinue() {
        String biography = bioArea.getText().trim();

        Account currentUser = database.getCurrentAccount();

        SetupProfileStates setupProfileStates =  AuthController.setupProfile((User) currentUser, biography, selectedProfile, selectedHashtags);

        switch (setupProfileStates){
            case EMPTY_BIOGRAPHY -> {
                profileMessageLabel.setStyle("-fx-text-fill: #ff7b72;");
                profileMessageLabel.setText("Please tell us about yourself!");
            }
            case EMPTY_HASHTAGS -> {
                profileMessageLabel.setStyle("-fx-text-fill: #ff7b72;");
                profileMessageLabel.setText("Please select at least 1 hashtag");
            }
            case null -> {
                profileMessageLabel.setStyle("-fx-text-fill: #3fb950;");
                profileMessageLabel.setText("Profile saved successfully. Selected hashtags: " + selectedHashtags.size());
            }
        }

        if (setupProfileStates != null){
            return;
        }
        SceneManager.showOnSameStage("Home.fxml","");
        System.out.println("Bio: " + biography);
        System.out.println("Selected Hashtags: " + selectedHashtags);
    }
}
