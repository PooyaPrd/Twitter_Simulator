package ir.pouyapourarshad.ptwitter.controllers.serviceControllers;

import ir.pouyapourarshad.ptwitter.Main;
import ir.pouyapourarshad.ptwitter.controllers.modelControllers.AccountController;
import ir.pouyapourarshad.ptwitter.controllers.modelControllers.UserController;
import ir.pouyapourarshad.ptwitter.models.users.Account;
import ir.pouyapourarshad.ptwitter.models.users.Admin;
import ir.pouyapourarshad.ptwitter.states.LoginError;
import ir.pouyapourarshad.ptwitter.states.RegisterError;
import ir.pouyapourarshad.ptwitter.states.SetupProfileStates;
import ir.pouyapourarshad.ptwitter.models.media.image.Image;
import ir.pouyapourarshad.ptwitter.models.media.image.ImageTypes;
import ir.pouyapourarshad.ptwitter.models.storage.Database;
import ir.pouyapourarshad.ptwitter.models.users.User;
import ir.pouyapourarshad.ptwitter.models.users.normaluser.NormalUser;

import java.io.File;
import java.time.LocalDate;
import java.util.HashSet;

public class AuthController {
    private static final Database database = Database.getInstance();
    static Account currentAccount = database.getCurrentAccount();

    public static RegisterError registerUser(String fullName, String username, String email,
                                         String phoneNumber, String password, String confirmPassword,
                                         LocalDate birthday){

        RegisterError checkSituation = checkInfoForRegisteration(fullName, username, email, phoneNumber,
                password, confirmPassword, birthday);

       if (checkSituation != null){
           return checkSituation;
       }

        NormalUser user = new NormalUser(username, password, fullName, birthday, email, phoneNumber, null,
                LocalDate.now(), "User of P!");

        database.addUser(user);
        database.setCurrentAccount(user);

        System.out.println("Log! : user with username: "+username+" registered!");

        return null;
    }

    public static RegisterError checkInfoForRegisteration(String fullName, String username, String email,
                                                          String phoneNumber, String password, String confirmPassword,
                                                          LocalDate birthday){
        if (isThereAnyEmpty(fullName, username, email, phoneNumber, password, confirmPassword)){
            return RegisterError.EMPTY_REQUIRED_FIELDS;
        }

        if (database.isUsernameExist(username)){
            return RegisterError.USERNAME_ALREADY_EXISTS;
        }

        if(!isEmailValid(email)){
            return RegisterError.INVALID_EMAIL;
        }

        if(!isPhoneNumberValid(phoneNumber)){
            return RegisterError.INVALID_PHONE_NUMBER;
        }

        if(!isPasswordValid(password)){
            return RegisterError.WEAK_PASSWORD;
        }

        if(!confirmPassword.equals(password)){
            return RegisterError.PASSWORDS_DO_NOT_MATCH;
        }

        if(birthday == null){
            return RegisterError.BIRTHDAY_REQUIRED;
        }
        return null;
    }

    private static boolean isAdmin(String username, String password){
        Admin admin = Admin.getInstance();
        if (admin.getUsername().equals(username) && admin.getPassword().equals(password)){
            return true;
        }
        return false;
    }

    public static LoginError loginUser(String username, String password){
        if (database.isValidUsernameAndPassword(username, password)){
            System.out.println("User with username: " + username + "Logged in.");
            database.setCurrentAccount(database.getUserByUsername(username));
            return null;
        }else if(isAdmin(username, password)){
            System.out.println("Admin just logged in.");
            database.setCurrentAccount(Admin.getInstance());
            return null;
        } else {
            return LoginError.INCORRECT_USERNAME_OR_PASSWORD;
        }
    }

    public static SetupProfileStates setupProfile(User user, String biography, File profilePicture, HashSet<String> selectedHashtags){

        if (biography.isEmpty() || biography.equals(" ")){
            return SetupProfileStates.EMPTY_BIOGRAPHY;
        }

        if (selectedHashtags.isEmpty()){
            return SetupProfileStates.EMPTY_HASHTAGS;
        }


        if (profilePicture == null){
            profilePicture = new File(Main.class.getResource("assets/images/default_profile.jpg").toExternalForm());
        }

//        Set Profile Picture
        ImageTypes imageType = Image.getImageTypeFromName(profilePicture.getName());
        AccountController.setProfile(user,
                new ir.pouyapourarshad.ptwitter.models.media.image.Image(profilePicture.getPath(), imageType));

//        Set Favorite Hashtags
        for (String hashtagTitle : selectedHashtags){
            UserController.addFavoriteHashtags(user, database.getHashtagByTitle(hashtagTitle).getId());
        }

//        Set Biography:
        UserController.setBiography(user, biography);

        return null;
    }

    public static boolean isThereAnyEmpty(String... texts){
        for(String s:texts){
            if(s.isEmpty()){
                return true;
            }
        }
        return false;
    }
    public static boolean isEmailValid(String email){
        String emailRegex = "^[A-Za-z0-9_%+-]+(\\.[A-Za-z0-9_%+-]+)*@(?i:pouya\\.ir)$";
        return email.matches(emailRegex);
//        return true;
    }
    public static boolean isPasswordValid(String password){
        return true;
//        String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!])\\S{8,}$";
//        return password.matches(passwordRegex);
    }
    public static boolean isPhoneNumberValid(String phoneNumber){
//        return true;
        String phoneRegex = "^09\\d{9}$";
        return phoneNumber.matches(phoneRegex);
    }

}
