package ir.pouyapourarshad.ptwitter.controllers.pageControllers;

import com.sun.source.tree.BreakTree;
import ir.pouyapourarshad.ptwitter.controllers.serviceControllers.SidebarController;
import ir.pouyapourarshad.ptwitter.models.posts.Hashtag;
import ir.pouyapourarshad.ptwitter.models.posts.Post;
import ir.pouyapourarshad.ptwitter.models.storage.Database;
import ir.pouyapourarshad.ptwitter.models.users.Account;
import ir.pouyapourarshad.ptwitter.models.users.User;
import javafx.scene.control.Label;
import javafx.scene.shape.Circle;

import java.util.ArrayList;

public class HomeController {
    private static final Database database = Database.getInstance();
    private static final Account currentAccount = database.getCurrentAccount();

    public static ArrayList<Post> getPostsByInterest(){
        User user = (User)currentAccount;
        ArrayList<Post> resultPosts = new ArrayList<>();
        ArrayList<Integer> favoriteHashtags = user.getFavoriteHashtags();
        for (int hashtagId:favoriteHashtags){
            ArrayList<Integer> postsId = database.getHashtagById(hashtagId).getPosts();
            for (int postId:postsId){
                if (!database.getPostById(postId).isDeleted()){
                    resultPosts.add(database.getPostById(postId));
                }
            }
        }
        return resultPosts;

    }

}
