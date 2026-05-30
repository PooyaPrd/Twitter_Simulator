package ir.pouyapourarshad.ptwitter.models.users.permiumuser;

import ir.pouyapourarshad.ptwitter.models.media.image.Image;
import ir.pouyapourarshad.ptwitter.models.users.Badges;
import ir.pouyapourarshad.ptwitter.models.users.User;

import java.time.LocalDate;

public abstract class PremiumUser extends User {
    PremiumUser(int id, String username, String password, String fullName, LocalDate birthDay,
                String email, String phoneNumber, Image profilePicture, LocalDate joinTime,
                String biography){
        super(id, username, password, fullName, birthDay, email, phoneNumber,
                profilePicture, joinTime, biography);
    }

}
