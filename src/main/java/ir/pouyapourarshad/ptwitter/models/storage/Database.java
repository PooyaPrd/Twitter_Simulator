package ir.pouyapourarshad.ptwitter.models.storage;

import ir.pouyapourarshad.ptwitter.models.posts.Hashtag;
import ir.pouyapourarshad.ptwitter.models.posts.Post;
import ir.pouyapourarshad.ptwitter.models.report.Report;
import ir.pouyapourarshad.ptwitter.models.users.Account;
import ir.pouyapourarshad.ptwitter.models.users.User;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Database {
    private static Database database;
    private Account currentAccount;

    private Database(){
    }
    public static Database getInstance(){
        if (database == null){
            database = new Database();
        }
        return database;
    }

    private final ArrayList<User> users = new ArrayList<>();
    private final ArrayList<Post> posts = new ArrayList<>();
    private final ArrayList<Hashtag> hashtags = new ArrayList<>();
    private final ArrayList<Report> reports = new ArrayList<>();

    public Account getCurrentAccount(){
        return currentAccount;
    }
    public void setCurrentAccount(Account account){
        currentAccount = account;
    }

//    User's Method
    public boolean isUsernameExist(String username){
        for (User user:users){
            if (user.getUsername().equals(username)){
                return true;
            }
        }
        return false;
    }
    public void addUser(User user){
        this.users.add(user);
    }
    public boolean isValidUsernameAndPassword(String username, String password){
        for (User user:users){
            if (user.getUsername().equals(username)){
                if (user.getPassword().equals(password)){
                    return true;
                }
            }
        }
        return false;
    }
    public User getUserByUsername(String username){
        for (User user: users){
            if (user.getUsername().equals(username)){
                return user;
            }
        }
        return null;
    }
    public ArrayList<User> getUsers(){
        return new ArrayList<>(users);
    }
    public User getUserById(int id){
        for (User user:users){
            if (user.getId() == id){
                return user;
            }
        }
        return null;
    }
    public void setUser(int id, User targetUser){
        for (int i=0; i<users.size(); i++){
            if (users.get(i).getId() == id){
                users.set(i, targetUser);
            }
        }
    }


//    Hashtag's Methods
    public Hashtag getHashtagByTitle(String title){
        for (Hashtag hashtag:hashtags){
            if (hashtag.getTitle().equalsIgnoreCase(title)){
                return hashtag;
            }
        }
        return null;
    }
    public void addHashtag(Hashtag hashtag){
        hashtags.add(hashtag);
    }
    public ArrayList<Hashtag> getHashtags(){
        return new ArrayList<>(hashtags);
    }
    public Hashtag getHashtagById(int id){
        for (Hashtag hashtag: hashtags){
            if (hashtag.getId() == id){
                return hashtag;
            }
        }
        return null;
    }


//    Post's Methods
    public Post getPostById(int id){
        return posts.get(id);
    }
    public void addPost(Post post){
        posts.add(post);
    }
    public ArrayList<Post> getPosts(){
        return new ArrayList<>(posts);
    }

    public void addReport(Report report){
        reports.add(report);
    }

    public ArrayList<Report> getReports(){
        return new ArrayList<>(reports);
    }
}
