package ir.pouyapourarshad.ptwitter.models.users.permiumuser;

import ir.pouyapourarshad.ptwitter.models.media.image.Image;
import ir.pouyapourarshad.ptwitter.models.posts.Post;
import ir.pouyapourarshad.ptwitter.models.users.Badges;

import java.time.LocalDate;

public class Blue extends PremiumUser{
    public Blue(int id, String username, String password, String fullName, LocalDate birthDay,
                String email, String phoneNumber, Image profilePicture, LocalDate joinTime,
                String biography){
        super(id, username, password, fullName, birthDay, email, phoneNumber,
                profilePicture, joinTime, biography);
        super.setBadges(Badges.BLUE);
    }

    @Override
    public boolean decreasePostTokens(String text, boolean hasFile) {
        int totalCost = 0;
        totalCost+=(int)(text.length() /2);
        if (hasFile){
            totalCost+=this.BLUE_USER_FILE_PRICE;
        }
        if (this.getToken() >= totalCost){
            this.setToken(this.getToken() - totalCost);
            return true;
        }
        return false;
    }

}
