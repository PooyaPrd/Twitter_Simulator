package ir.pouyapourarshad.ptwitter.view;

import ir.pouyapourarshad.ptwitter.controllers.modelControllers.AccountController;
import ir.pouyapourarshad.ptwitter.controllers.modelControllers.PostController;
import ir.pouyapourarshad.ptwitter.controllers.serviceControllers.SidebarController;
import ir.pouyapourarshad.ptwitter.models.media.image.Image;
import ir.pouyapourarshad.ptwitter.models.media.image.ImageTypes;
import ir.pouyapourarshad.ptwitter.models.media.video.Video;
import ir.pouyapourarshad.ptwitter.models.media.video.VideoQualities;
import ir.pouyapourarshad.ptwitter.models.media.video.VideoTypes;
import ir.pouyapourarshad.ptwitter.models.posts.Post;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.VideoTrack;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class PostNewViewController {

    @FXML private Button homeNavButton;
    @FXML private Button searchNavButton;
    @FXML private Button profileNavButton;
    @FXML private Button postNewNavButton;
    @FXML private Button logoutButton;

    @FXML private Circle sidebarUserProfilePicture;
    @FXML private Label sidebarUserFullName;
    @FXML private Label sidebarUsername;

    @FXML private Circle authorAvatarCircle;
    @FXML private Label authorAvatarText;
    @FXML private Label authorDisplayNameLabel;
    @FXML private Label authorUsernameLabel;

    @FXML private TextArea postContentArea;
    @FXML private Button selectMediaButton;
    @FXML private Button removeMediaButton;
    @FXML private Label selectedFileNameLabel;
    @FXML private Label selectedFileTypeLabel;
    @FXML private Label characterCountLabel;
    @FXML private Button clearPostButton;
    @FXML private Button publishPostButton;
    @FXML private Label postStatusMessageLabel;
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


    private File selectedMediaFile;

    private int parentId = 0;

    @FXML
    private VBox mainContainer;

    @FXML
    public void initialize() {
        if (sidebarUserFullName != null){
            SidebarController.setupSidebarAccount(sidebarUserFullName, sidebarUserProfilePicture, sidebarUsername,
                    sidebarHomeButton,sidebarSearchButton, sidebarProfileButton, sidebarPostNewButton, sidebarLogoutButton);
        }
        initializeCharacterCounter();
        initializeButtons();
        resetFileSelectionUI();
    }

    public void loadReplyPage(Post post){
        parentId = post.getId();
        VBox postBox =  PostController.shortPostBoxBuilder(post);
        ((HBox)postBox.getChildren().get(2)).getChildren().get(1).setOnMouseClicked(e -> {});
        mainContainer.getChildren().add(0,postBox);
    }

    private void initializeCharacterCounter() {
        updateCharacterCount();

        postContentArea.textProperty().addListener((observable, oldValue, newValue) -> {
            updateCharacterCount();
            clearStatusMessage();
        });
    }

    private void initializeButtons() {
        selectMediaButton.setOnAction(event -> handleSelectMedia());
        removeMediaButton.setOnAction(event -> handleRemoveMedia());
        clearPostButton.setOnAction(event -> handleClearPost());
        publishPostButton.setOnAction(event -> handlePublishPost());
    }

    private void handleSelectMedia() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Photo or Video");

        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter(
                        "Media Files", "*.png", "*.jpg", "*.jpeg","*.webp", "*.svg", "*.mp4", "*.mov", "*.mkv", "*.wmv"
                ),
                new FileChooser.ExtensionFilter(
                        "Image Files", "*.png", "*.jpg", "*.jpeg", "*.webp", "*.svg"
                ),
                new FileChooser.ExtensionFilter(
                        "Video Files", "*.mp4", "*.mov", "*.mkv", "*.wmv"
                )
        );

        Stage stage = (Stage) selectMediaButton.getScene().getWindow();
        File chosenFile = fileChooser.showOpenDialog(stage);

        if (chosenFile != null) {
            selectedMediaFile = chosenFile;
            selectedFileNameLabel.setText(chosenFile.getName());
            selectedFileTypeLabel.setText(detectFileType(chosenFile));
            showInfoMessage("Media selected successfully.");
        }
    }

    private void handleRemoveMedia() {
        selectedMediaFile = null;
        resetFileSelectionUI();
        showInfoMessage("Selected media removed.");
    }

    private void handleClearPost() {
        postContentArea.clear();
        selectedMediaFile = null;
        resetFileSelectionUI();
        updateCharacterCount();
        showInfoMessage("Post form cleared.");
    }

    private ir.pouyapourarshad.ptwitter.models.media.File getFile(File file){
        String name = file.getName().toLowerCase();
        if (name.endsWith(".mp4") || name.endsWith(".mov") ||
                name.endsWith(".mkv")|| name.endsWith(".wmv")) {
            return new Video(file.getPath(), VideoQualities.Q1080, VideoTypes.valueOf(name.substring(name.length() - 3).toUpperCase()), 0);
        }
//         JPEG, WEBP, PNG, SVG, JPG
        else if (name.endsWith(".png") || name.endsWith(".jpg")
        || name.endsWith(".svg") || name.endsWith(".webp")) {
            return new Image(file.getPath(), ImageTypes.valueOf(name.substring(name.length() - 3).toUpperCase()));
        }
        else if (name.endsWith(".jpeg")){
            return new Image(file.getPath(), ImageTypes.valueOf("JPEG"));
        }
        return null;
    }

    private void handlePublishPost() {
        String content = postContentArea.getText() != null ? postContentArea.getText().trim() : "";

        if (content.isEmpty() && selectedMediaFile == null) {
            showErrorMessage("Post cannot be empty. Write something or attach media.");
            return;
        }
        ir.pouyapourarshad.ptwitter.models.media.File postedMedia = null;
        if (selectedMediaFile != null){
            postedMedia = getFile(selectedMediaFile);
        }

        if (!PostController.createPost(content, postedMedia, parentId)){
            showErrorMessage("You dont have enough token. pls charge it!");
            return;
        }

        showSuccessMessage("Post published successfully.");

        postContentArea.clear();
        selectedMediaFile = null;
        resetFileSelectionUI();
        updateCharacterCount();


    }

    private void updateCharacterCount() {
        String text = postContentArea.getText();
        int count = text == null ? 0 : text.length();
        characterCountLabel.setText(count + " characters");

    }

    private String detectFileType(File file) {
        String name = file.getName().toLowerCase();

        if (name.endsWith(".png") || name.endsWith(".jpg") || name.endsWith(".jpeg")) {
            return "Image file";
        }

        if (name.endsWith(".mp4") || name.endsWith(".mov")) {
            return "Video file";
        }

        return "Unknown file type";
    }

    private void resetFileSelectionUI() {
        selectedFileNameLabel.setText("No file selected");
        selectedFileTypeLabel.setText("");
    }

    private void clearStatusMessage() {
        postStatusMessageLabel.setText("");
        postStatusMessageLabel.setStyle("");
    }

    private void showInfoMessage(String message) {
        postStatusMessageLabel.setText(message);
        postStatusMessageLabel.setStyle("-fx-text-fill: #93c5fd; -fx-font-size: 13px; -fx-font-weight: bold;");
    }

    private void showSuccessMessage(String message) {
        postStatusMessageLabel.setText(message);
        postStatusMessageLabel.setStyle("-fx-text-fill: #22c55e; -fx-font-size: 13px; -fx-font-weight: bold;");
    }

    private void showErrorMessage(String message) {
        postStatusMessageLabel.setText(message);
        postStatusMessageLabel.setStyle("-fx-text-fill: #ef4444; -fx-font-size: 13px; -fx-font-weight: bold;");
    }

}
