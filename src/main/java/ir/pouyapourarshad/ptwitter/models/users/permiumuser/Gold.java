package ir.pouyapourarshad.ptwitter.models.users.permiumuser;

import ir.pouyapourarshad.ptwitter.models.media.image.Image;
import ir.pouyapourarshad.ptwitter.models.posts.Post;
import ir.pouyapourarshad.ptwitter.models.users.Badges;
import ir.pouyapourarshad.ptwitter.models.users.User;

import java.time.LocalDate;

public class Gold extends User {
    public Gold(int id, String username, String password, String fullName, LocalDate birthDay,
                String email, String phoneNumber, Image profilePicture, LocalDate joinTime,
                String biography) {
        super(id, username, password, fullName, birthDay, email, phoneNumber,
                profilePicture, joinTime, biography);
        super.setBadges(Badges.GOLD);
    }

    @Override
    public boolean decreasePostTokens(String text, boolean hasFile) {
        if (this.getToken() >= this.GOLD_USER_CONST_PRICE){
            this.setToken(this.getToken() - GOLD_USER_CONST_PRICE);
            return true;
        }
        return false;
    }

    private Badges badge = Badges.GOLD;
}
