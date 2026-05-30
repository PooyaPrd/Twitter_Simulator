package ir.pouyapourarshad.ptwitter.controllers.modelControllers;

import ir.pouyapourarshad.ptwitter.models.media.image.Image;
import ir.pouyapourarshad.ptwitter.models.storage.Database;
import ir.pouyapourarshad.ptwitter.models.users.Account;
import ir.pouyapourarshad.ptwitter.models.users.User;

import java.time.LocalDate;

public class UserController {
    private static final Database database = Database.getInstance();
    static Account currentAccount = database.getCurrentAccount();

    public static void setBiography(User user, String biography){
        user.setBiography(biography);
    }
    public static void addFavoriteHashtags(User user, int id){
        user.addFavoriteHashtags(id);
    }
    public static void setNewInformation(String fullName, String username, String email,
                                    String phoneNumber, String password, String biography,
                                    LocalDate birthday){
        User currentUser = (User)currentAccount;
        currentUser.setFullName(fullName);
        currentUser.setUsername(username);
        currentUser.setEmail(email);
        currentUser.setPhoneNumber(phoneNumber);
        currentUser.setPassword(password);
        currentUser.setBiography(biography);
        currentUser.setBirthDay(birthday);
    }
}
