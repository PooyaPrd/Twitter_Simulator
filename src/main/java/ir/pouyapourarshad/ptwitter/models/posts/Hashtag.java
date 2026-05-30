package ir.pouyapourarshad.ptwitter.models.posts;

import ir.pouyapourarshad.ptwitter.models.storage.Database;
import org.w3c.dom.html.HTMLImageElement;

import java.util.ArrayList;

public class Hashtag {
    private static int lastId = 0;
    private static Database database = Database.getInstance();

    private int id;
    private final String title;
    private ArrayList<Integer> posts;

    public Hashtag(String title, ArrayList<Integer> posts){
        this.id = lastId++;
        this.title = title;
        this.posts = posts;
        database.addHashtag(this);
    }

    public String getTitle(){
        return title;
    }
    public int getId(){
        return id;
    }
    public void addPostId(int id){
        posts.add(id);
    }
    public ArrayList<Integer> getPosts(){
        return new ArrayList<>(posts);
    }

}
