package ir.pouyapourarshad.ptwitter.models.users;

import ir.pouyapourarshad.ptwitter.models.media.image.Image;

import java.time.LocalDate;

public class Admin extends Account{
    private static Admin admin;
    public static Admin getInstance(){
        if (admin == null){
            admin = new Admin("a", "1", "Admin", LocalDate.now(),
                    "Admin@pouya.ir", "09011231234", null, LocalDate.now());
        }
        return admin;
    }
    private Admin(String username, String password, String fullName, LocalDate birthDay, String email, String phoneNumber, Image profilePicture, LocalDate joinTime) {
        super(-1,username, password, fullName, birthDay, email, phoneNumber, profilePicture, joinTime);
    }
}
