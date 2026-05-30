package ir.pouyapourarshad.ptwitter.view.profilepage;

import ir.pouyapourarshad.ptwitter.controllers.modelControllers.UserController;
import ir.pouyapourarshad.ptwitter.controllers.pageControllers.EditInfoController;
import ir.pouyapourarshad.ptwitter.controllers.serviceControllers.AuthController;
import ir.pouyapourarshad.ptwitter.controllers.serviceControllers.SceneManager;
import ir.pouyapourarshad.ptwitter.models.users.User;
import ir.pouyapourarshad.ptwitter.states.RegisterError;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.time.LocalDate;

public class EditInfoViewController {

    @FXML
    private TextArea bioArea;

    @FXML
    private DatePicker birthdayPicker;

    @FXML
    private Button cancelButton;

    @FXML
    private Button chooseProfilePictureButton;

    @FXML
    private TextField emailField;

    @FXML
    private TextField fullnameField;

    @FXML
    private PasswordField passwordField;

    @FXML TextField phoneNumberFeild;

    @FXML
    private Label profileAvatarPlaceholder;

    @FXML
    private Circle profileImage;

    @FXML
    private Button removeProfilePictureButton;

    @FXML
    private TextField confirmPasswordFeild;

    @FXML
    private Label statusMessageLabel;

    @FXML
    private TextField usernameField;

    @FXML
    private void initialize(){
        loadUserInfo();
    }

    private void loadUserInfo(){
        User user = EditInfoController.getUser();
        fullnameField.setText(user.getFullName());
        usernameField.setText(user.getUsername());
        emailField.setText(user.getEmail());
        phoneNumberFeild.setText(user.getPhoneNumber());
        passwordField.setText(user.getPassword());
        birthdayPicker.setValue(user.getBirthDay());
        bioArea.setText(user.getBiography());
        profileImage.setStyle("");
        profileImage.setFill(new ImagePattern(new Image(user.getProfilePicture().getPathUrl())));
    }

    @FXML
    private void backButtonHandler(){
        SceneManager.showOnSameStage("MyProfilePage.fxml", "| Your Account!");
    }

    @FXML
    private void saveChangesButtonHandler(){
        String fullName = fullnameField.getText(),
                username = usernameField.getText(),
                email = emailField.getText(),
                phoneNumber=phoneNumberFeild.getText(),
                password = passwordField.getText(),
                confirmPassword = password,
                biography = bioArea.getText();
        LocalDate birthday = birthdayPicker.getValue();

        RegisterError registerSituatuion =  AuthController.checkInfoForRegisteration(fullName, username, email, phoneNumber,
                password, confirmPassword, birthday);
        if (registerSituatuion == null){
            UserController.setNewInformation(fullName, username, email, phoneNumber,
                    password,biography , birthday);
            statusMessageLabel.setText("Successfully edited! go home...");
            return;
        }
        String text =  switch (registerSituatuion){
            case EMPTY_REQUIRED_FIELDS, RegisterError.BIRTHDAY_REQUIRED -> "you can't let any field be empty.";
            case USERNAME_ALREADY_EXISTS -> "Username is already exist. choose something else...";
            case INVALID_EMAIL -> "Your email is not valid! (we only accept @pouya.ir emails!";
            case INVALID_PHONE_NUMBER -> "Your phone number is incorrect!";
            case WEAK_PASSWORD -> "Your password is weak!";
            case PASSWORDS_DO_NOT_MATCH -> "Passwords do not match.";
        };
        statusMessageLabel.setText(text);
    }

    @FXML
    private void cancelButtonHandler(){
        loadUserInfo();
    }
}
