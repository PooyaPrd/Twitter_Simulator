package ir.pouyapourarshad.ptwitter.models.users;

import ir.pouyapourarshad.ptwitter.models.media.image.Image;
import ir.pouyapourarshad.ptwitter.models.posts.Hashtag;
import ir.pouyapourarshad.ptwitter.models.posts.Post;
import ir.pouyapourarshad.ptwitter.models.storage.Database;
import java.time.LocalDate;
import java.util.ArrayList;

public abstract class User extends Account{

    protected int NORMAL_USER_FILE_PRICE = 10;
    protected int BLUE_USER_FILE_PRICE = 5;
    protected int GOLD_USER_CONST_PRICE = 5;

    private int credit;
    private int token;
    private String biography;
    private ArrayList<Integer> postsList;
    private ArrayList<Integer> followers;
    private ArrayList<Integer> followings;
    private ArrayList<Integer> likedPosts;
    private ArrayList<Integer> favoriteHashtags;
    private Badges badges;
    private boolean isLocked;
    Database database = Database.getInstance();

    public User(int id, String username, String password,
                String fullName, LocalDate birthDay,
                String email, String phoneNumber,
                Image profilePicture, LocalDate joinTime, String biography) {
        super(id, username, password, fullName, birthDay, email, phoneNumber,
                profilePicture, joinTime);
                postsList = new ArrayList<>();
                followers = new ArrayList<>();
                followings = new ArrayList<>();
                likedPosts = new ArrayList<>();
                favoriteHashtags = new ArrayList<>();
                this.credit = 0;
                this.token = 0;
                badges = Badges.NORMAL;
                isLocked = false;
                this.biography = biography;
    }

//    Getters & Setters
    public int getCredit() {
        return credit;
    }
    public int getToken() {
        return token;
    }
    public String getBiography() {
        return biography;
    }
    public ArrayList<Integer> getPostsList() {
        return new ArrayList<>(postsList);
    }
    public void addPost(int id){
        this.postsList.add(id);
    }
    public ArrayList<Integer> getFollowers() {
        return new ArrayList<>(followers);
    }
    public ArrayList<Integer> getFollowings() {
        return new ArrayList<>(followings);
    }
    public ArrayList<Integer> getLikedPosts() {
        return new ArrayList<>(likedPosts);
    }
    public ArrayList<Integer> getFavoriteHashtags(){
        return new ArrayList<>(favoriteHashtags);
    }
    public Badges getBadges() {
        return badges;
    }
    public boolean getIsLocked() {
        return isLocked;
    }
    public void setBiography(String biography){
        this.biography = biography;
    }
    public void addFavoriteHashtags(int id){
        favoriteHashtags.add(id);
    }
    public void addFavoriteHashtags(ArrayList<Integer> id){
        favoriteHashtags.addAll(id);
    }
    public void setToken(int token){
        this.token = token;
    }
    public void setCredit(int credit){
        this.credit = credit;
    }
    protected void setBadges(Badges badges){
        this.badges = badges;
    }
    public void setPostsList(ArrayList<Integer> postsList) {
        this.postsList = postsList;
    }
    public void setIsLock(boolean isLocked){
        this.isLocked = isLocked;
    }
    public void setFollowers(ArrayList<Integer> followers) {
        this.followers = followers;
    }
    public void setFollowings(ArrayList<Integer> followings) {
        this.followings = followings;
    }
    public void setLikedPosts(ArrayList<Integer> likedPosts) {
        this.likedPosts = likedPosts;
    }
    public void addFollower(User user){
        followers.add(user.getId());
    }
    public void addFollowing(User user){
        followings.add(user.getId());
    }
    public void removeFromFollowers(User user){
        for (int i =0 ;i<followers.size(); i++){
            if (followers.get(i) == user.getId()){
                followers.remove(i);
                return;
            }
        }
    }
    public void removeFromFollowings(User user){
        for (int i =0 ;i<followings.size(); i++){
            if (followings.get(i) == user.getId()){
                followings.remove(i);
                return;
            }
        }
    }
    public void addLikedPost(Post post){
        likedPosts.add(post.getId());
    }
    public void removeLikedPost(Post post){
        for (int i =0 ;i<likedPosts.size(); i++){
            if (likedPosts.get(i) == post.getId()){
                likedPosts.remove(i);
                return;
            }
        }
    }

    //    Methods
    public abstract boolean decreasePostTokens(String text, boolean hasFile);

}
