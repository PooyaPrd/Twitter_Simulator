package ir.pouyapourarshad.ptwitter.view;

import ir.pouyapourarshad.ptwitter.models.users.Admin;
import ir.pouyapourarshad.ptwitter.models.users.User;
import ir.pouyapourarshad.ptwitter.states.LoginError;
import ir.pouyapourarshad.ptwitter.states.RegisterError;
import ir.pouyapourarshad.ptwitter.models.storage.Database;
import ir.pouyapourarshad.ptwitter.controllers.serviceControllers.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import static ir.pouyapourarshad.ptwitter.controllers.serviceControllers.AuthController.*;

import java.time.LocalDate;


public class AuthViewController {

    private static final Database database = Database.getInstance();

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private TextField confirmPasswordVisibleField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField fullNameField;

    @FXML
    private Button loginButton;

    @FXML
    private Label loginMessageLabel;

    @FXML
    private PasswordField loginPasswordField;

    @FXML
    private TextField loginUsernameOrEmailField;

    @FXML
    private TextField phoneField;

    @FXML
    private Button registerButton;

    @FXML
    private Label registerMessageLabel;

    @FXML
    private PasswordField registerPasswordField;

    @FXML
    private TextField registerPasswordVisibleField;

    @FXML
    private Button toggleConfirmPasswordButton;

    @FXML
    private Button togglePasswordButton;

    @FXML
    private TextField usernameField;

    @FXML
    private DatePicker birthdayField;

    @FXML
    void handleLogin(){
        String username = loginUsernameOrEmailField.getText();
        String password = loginPasswordField.getText();

        LoginError login = loginUser(username, password);
        String text = switch (login){
            case INCORRECT_USERNAME_OR_PASSWORD -> "Your username or password is incorrect.";
            case LOCKED_ACCOUNT -> "You are blocked!";
            case null -> "logged in successfully";
        };
        loginMessageLabel.setText(text);
        if (login != null){
            return;
        }

        if (database.getCurrentAccount() instanceof Admin){
            SceneManager.showOnSameStage("Admin.fxml", "");
            return;
        }
        SceneManager.showOnSameStage("Home.fxml", "");

    }
    @FXML
    void handleRegister(){
        String fullName = fullNameField.getText();
        String username = usernameField.getText();
        String email = emailField.getText();
        String phoneNumber = phoneField.getText();
        String password = registerPasswordField.getText();
        String confirmPassword = confirmPasswordField.getText();
        LocalDate birthday = birthdayField.getValue();

        RegisterError registeration =  registerUser(fullName, username, email, phoneNumber, password,
                confirmPassword, birthday);

        String text =  switch (registeration){
            case EMPTY_REQUIRED_FIELDS, RegisterError.BIRTHDAY_REQUIRED -> "you can't let any field be empty.";
            case USERNAME_ALREADY_EXISTS -> "Username is already exist. choose something else...";
            case INVALID_EMAIL -> "Your email is not valid! (we only accept @pouya.ir emails!";
            case INVALID_PHONE_NUMBER -> "Your phone number is incorrect!";
            case WEAK_PASSWORD -> "Your password is weak!";
            case PASSWORDS_DO_NOT_MATCH -> "Passwords do not match.";
            case null -> "Successfully registered!";
        };

        registerMessageLabel.setText(text);
        if (registeration != null){
            return;
        }
        SceneManager.showOnSameStage("ProfileSetup.fxml", "Setup your profile...");
    }




}
