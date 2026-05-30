package ir.pouyapourarshad.ptwitter.models.users.normaluser;

import ir.pouyapourarshad.ptwitter.Main;
import ir.pouyapourarshad.ptwitter.models.media.image.Image;
import ir.pouyapourarshad.ptwitter.models.media.image.ImageTypes;
import ir.pouyapourarshad.ptwitter.models.posts.Post;
import ir.pouyapourarshad.ptwitter.models.users.Account;
import ir.pouyapourarshad.ptwitter.models.users.User;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDate;

public class NormalUser extends User {

    public NormalUser(String username, String password, String fullName, LocalDate birthDay, String email, String phoneNumber, Image profilePicture, LocalDate joinTime, String biography) {
        super(Account.increaseAndGetLastId(),username, password, fullName, birthDay, email, phoneNumber, profilePicture, joinTime, biography);
        setCredit(30);
    }


    @Override
    public boolean decreasePostTokens(String text, boolean hasFile) {
        int totalCost = 0;
        totalCost+=text.length();
        if (hasFile){
            totalCost+=this.NORMAL_USER_FILE_PRICE;
        }
        if (this.getToken() >= totalCost){
            this.setToken(this.getToken() - totalCost);
            return true;
        }
        return false;
    }

}
