package ir.pouyapourarshad.ptwitter.models.posts;

import ir.pouyapourarshad.ptwitter.models.media.File;
import ir.pouyapourarshad.ptwitter.models.media.image.Image;
import ir.pouyapourarshad.ptwitter.models.storage.Database;
import ir.pouyapourarshad.ptwitter.models.users.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Post {
    private static int lastId = 0;
    static Database database = Database.getInstance();

    private int id;
    private String text;
    private File file;
    private int authorId;
    private LocalDate createdAt;
    private ArrayList<Hashtag> hashtags;
    private ArrayList<Integer> likedUserIds;
    private Integer parentPostId;
    private ArrayList<Integer> replyPostIds;
    private int views;
    private int likes;
    private boolean locked;
    private boolean deleted;

    public Post(String text, File file, int authorId, LocalDate createdAt,
                Integer parentPostId, boolean locked) {
        this.id = lastId++;
        this.text = text;
        this.file = file;
        this.authorId = authorId;
        this.createdAt = createdAt;
        this.hashtags = Post.getHashtags(text, id);
        this.likedUserIds = new ArrayList<>();
        this.parentPostId = parentPostId;
        this.replyPostIds = new ArrayList<>();
        this.views = 0;
        this.likes = 0;
        this.locked = locked;
        this.deleted = false;
    }

    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public File getFile() {
        return file;
    }

    public int getAuthorId() {
        return authorId;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public ArrayList<Hashtag> getHashtags() {
        return hashtags;
    }

    public ArrayList<Integer> getLikedUserIds() {
        return likedUserIds;
    }

    public Integer getParentPostId() {
        return parentPostId;
    }

    public ArrayList<Integer> getReplyPostIds() {
        return replyPostIds;
    }

    public void addRepliedPost(int id){
        replyPostIds.add(id);
    }

    public int getViews() {
        return views;
    }

    public int getLikes() {
        return likes;
    }

    public boolean isLocked() {
        return locked;
    }

    public boolean isDeleted() {
        return deleted;
    }
    public void setDeleted(){
        deleted = true;
    }
    public void addView(){
        views++;
    }

    public static ArrayList<Hashtag> getHashtags(String msg, int postId){

        ArrayList<Hashtag> hashtags = new ArrayList<>();
        Pattern pattern = Pattern.compile("#[^\\\\s#]+");
        Matcher matcher = pattern.matcher(msg);

        while(matcher.find()){

            String title = matcher.group();
            Hashtag hashtag = database.getHashtagByTitle(title);

            if (hashtag == null){
                ArrayList<Integer> posts = new ArrayList<>();
                posts.add(postId);
                hashtags.add(new Hashtag(title, posts));

            } else {
                hashtags.add(hashtag);
                hashtag.addPostId(postId);
            }
        }
        return hashtags;
    }

    public void addLike(User user){
        if (!likedUserIds.contains(user.getId())) {
            this.likedUserIds.add(user.getId());
            this.likes++;
        }
    }
    public void removeLike(User user){
        for (int i = 0; i<likedUserIds.size(); i++){
            if (likedUserIds.get(i) == user.getId()){
                likedUserIds.remove(i);
                if (likes > 0) {
                    likes--;
                }
                return;
            }
        }
    }

}
