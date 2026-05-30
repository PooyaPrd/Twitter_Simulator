package ir.pouyapourarshad.ptwitter.models.users;

import java.time.LocalDate;
import ir.pouyapourarshad.ptwitter.models.media.image.Image;

public abstract class Account {
    private static int lastId = 0;
    private int id;
    private String username;
    private String password;
    private String fullName;
    private LocalDate birthDay;
    private String email;
    private String phoneNumber;
    private Image profilePicture;
    private LocalDate joinTime;

    public Account(int id, String username, String password, String fullName,
                   LocalDate birthDay, String email, String phoneNumber,
                   Image profilePicture, LocalDate joinTime) {

        this.id = id;
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.birthDay = birthDay;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.profilePicture = profilePicture;
        this.joinTime = joinTime;
    }

//    Getters & Setters
    public int getId() {
        return id;
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public String getFullName() {
        return fullName;
    }
    public LocalDate getBirthDay() {
        return birthDay;
    }
    public String getEmail() {
        return email;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public Image getProfilePicture() {
        return profilePicture;
    }
    public LocalDate getJoinTime() {
        return joinTime;
    }

    public void setFullName(String fullName){
        this.fullName = fullName;
    }
    public void setProfilePicture(Image profilePicture){
        this.profilePicture = profilePicture;
    }
    public void setUsername(String username){
        this.username = username;
    }
    public void setPassword(String password){
        this.password = password;
    }
    public void setEmail(String email){
        this.email = email;
    }
    public void setPhoneNumber(String phoneNumber){
        this.phoneNumber = phoneNumber;
    }
    public void setBirthDay(LocalDate birthDay){
        this.birthDay = birthDay;
    }

    public static int increaseAndGetLastId(){
        return lastId++;
    }
}
