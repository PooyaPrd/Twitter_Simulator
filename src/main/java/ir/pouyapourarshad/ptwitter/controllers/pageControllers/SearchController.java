package ir.pouyapourarshad.ptwitter.controllers.pageControllers;

import ir.pouyapourarshad.ptwitter.models.posts.Hashtag;
import ir.pouyapourarshad.ptwitter.models.posts.Post;
import ir.pouyapourarshad.ptwitter.models.storage.Database;
import ir.pouyapourarshad.ptwitter.models.users.Account;
import ir.pouyapourarshad.ptwitter.models.users.User;

import java.util.ArrayList;

public class SearchController {

    static Database database = Database.getInstance();
    static Account currentAccount = database.getCurrentAccount();

    public static String normalizeQuery(String rawQuery){
        return rawQuery.toLowerCase();
    }

    public static ArrayList<Hashtag> getHashtagResult(String query){
        ArrayList<Hashtag> hashtags = database.getHashtags();
        ArrayList<Hashtag> results = new ArrayList<>();
        for (Hashtag hashtag : hashtags){
            if (containsIgnoreCase(hashtag.getTitle(), query)){
                results.add(hashtag);
            }
        }
        return results;
    }

    public static ArrayList<Post> getPostByHashtag(Hashtag hashtag){
        ArrayList<Post> posts = new ArrayList<>();
        ArrayList<Integer> postsId = hashtag.getPosts();
        for (int id : postsId){
            if (!database.getPostById(id).isDeleted()){
                posts.add(database.getPostById(id));
            }
        }
        return posts;
    }

    public static ArrayList<Post> getPostResult(String query){
        ArrayList<Post> posts = database.getPosts();
        ArrayList<Post> results = new ArrayList<>();
        for (Post post : posts){
            if ((!post.isDeleted()) && containsIgnoreCase(post.getText(), query)){
                results.add(post);
            }
        }
        return results;
    }

    public static ArrayList<User> getAccountResult(String query){
        ArrayList<User> users = database.getUsers();
        ArrayList<User> results = new ArrayList<>();
        for (User user : users){
            if (containsIgnoreCase(user.getFullName(), query)){
                results.add(user);
            }
        }
        return results;
    }

    private static boolean containsIgnoreCase(String source, String target){
        return source.toLowerCase().contains(target.toLowerCase());
    }
}
