package ir.pouyapourarshad.ptwitter.controllers.modelControllers;

import ir.pouyapourarshad.ptwitter.Main;
import ir.pouyapourarshad.ptwitter.models.media.File;
import ir.pouyapourarshad.ptwitter.models.media.image.ImageTypes;
import ir.pouyapourarshad.ptwitter.models.posts.Post;
import ir.pouyapourarshad.ptwitter.models.storage.Database;
import ir.pouyapourarshad.ptwitter.models.users.Account;
import ir.pouyapourarshad.ptwitter.models.users.User;
import ir.pouyapourarshad.ptwitter.view.PostNewViewController;
import ir.pouyapourarshad.ptwitter.view.PostPageViewController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PostController {
    static Database database = Database.getInstance();
    static Account currentAccount = database.getCurrentAccount();

    public static VBox longPostBoxBuilder(Post post){
        return postBoxBuilder(false, post);
    }
    public static VBox shortPostBoxBuilder(Post post){
        return postBoxBuilder(true, post);
    }

    private static VBox postBoxBuilder(boolean isShort, Post post){
        post.addView();
        VBox card = new VBox();
        card.setSpacing(12);
        card.setPrefWidth(938);
        card.getStyleClass().clear();
        card.getStyleClass().add("post-card");

        HBox profileSection = profileSectionBuilder(post.getAuthorId());

        String shortContent = post.getText();
        if ((shortContent.length() > 150) && isShort){
            shortContent = shortContent.substring(0, 150) + "...";
        }
        TextFlow content = getDecoratedText(shortContent);

        card.getChildren().addAll(profileSection, content);
        ImageView postImage = getPostImageView(post);
        if (postImage != null){
            card.getChildren().add(postImage);
        }
        card.getChildren().add(interactionSectionBuilder(post, card));
        card.setOnMouseClicked(e -> openPostPage(post));
        return card;

    }

    public static void openPostPage(Post post){
        Stage stage = new Stage();
        Scene scene;
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("PostPage.fxml"));
        stage.initModality(Modality.APPLICATION_MODAL);
        try{
            scene = new Scene(loader.load());
        } catch (IOException e){
            e.printStackTrace();
            return;
        }
        PostPageViewController controller = loader.getController();
        controller.renderPosts(post);

        stage.setTitle("P. | Post Page");
        stage.setScene(scene);
        stage.show();
    }

    private static TextFlow getDecoratedText(String text){
        TextFlow content = new TextFlow();
        Pattern pattern = Pattern.compile("#\\w+|[^#]+");
        Matcher matcher = pattern.matcher(text);

        while (matcher.find()) {
            String segment = matcher.group();
            Text textNode = new Text(segment);

            if (segment.startsWith("#")) {
                textNode.setFill(Color.web("#1DA1F2"));
                textNode.setStyle("-fx-font-weight: bold;");
            } else {
                textNode.setFill(Color.WHITE);
            }
            textNode.setFont(Font.font(16));

            content.getChildren().add(textNode);
        }
        return content;
    }

    private static HBox interactionSectionBuilder(Post post, VBox card){
        HBox hBox = new HBox(18);
        Button likesButton = new Button();
        Button repliesButton = new Button();
        Button viewsButton = new Button();
        likesButton.getStyleClass().add("action-button");
        likesButton.setOnMouseClicked(e -> handleLikeButton(post, likesButton));
        viewsButton.getStyleClass().add("action-button");
        repliesButton.getStyleClass().add("action-button");
        repliesButton.setOnMouseClicked(e -> handleReply(post));
        likesButton.setText("♡ "+post.getLikes());
        repliesButton.setText("↩ "+post.getReplyPostIds().size());
        viewsButton.setText("\uD83D\uDC41 "+post.getViews());
        if (post.getAuthorId() == database.getCurrentAccount().getId()){
            Button deleteButton = new Button();
            deleteButton.getStyleClass().add("action-button");
            deleteButton.setText("Delete");
            deleteButton.setOnMouseClicked(e -> deletePost(post, card));
            hBox.getChildren().addAll(likesButton, repliesButton, viewsButton, deleteButton);
        } else {
            hBox.getChildren().addAll(likesButton, repliesButton, viewsButton);
        }
        return hBox;
    }

    private static ImageView getPostImageView(Post post){
        File file = post.getFile();
        if (file == null){
            return null;
        }
        String path = file.getPath();
        if (path == null || path.isBlank()){
            return null;
        }
        if (!isImageFile(path)){
            return null;
        }

        ImageView imageView = new ImageView(new Image(file.getPathUrl()));
        imageView.setPreserveRatio(true);
        imageView.setFitWidth(520);
        imageView.setSmooth(true);
        imageView.setStyle("-fx-background-radius: 16; -fx-border-radius: 16;");
        return imageView;
    }

    private static boolean isImageFile(String path){
        String lowerCasePath = path.toLowerCase();
        for (ImageTypes imageType : ImageTypes.values()){
            if (lowerCasePath.endsWith("." + imageType.name().toLowerCase())){
                return true;
            }
        }
        return false;
    }

    private static void handleReply(Post post){
        Stage stage = new Stage();
        Scene scene;
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("ReplyPage.fxml"));
        stage.initModality(Modality.APPLICATION_MODAL);
        try{
            scene = new Scene(loader.load());
        } catch (IOException e){
            e.printStackTrace();
            return;
        }
        PostNewViewController controller = loader.getController();
        controller.loadReplyPage(post);

        stage.setTitle("P. | Reply on post");
        stage.setScene(scene);
        stage.show();
    }

    private static void deletePost(Post post, VBox card){
        post.setDeleted();
        card.setVisible(false);
        card.setManaged(false);
    }

    private static void handleLikeButton(Post post, Button button){
        if (isPostLiked(post, (User)database.getCurrentAccount())){
            unLikePost(post, button);
        } else {
            likePost(post, button);
        }
    }

    private static boolean isPostLiked(Post post, User user){
        ArrayList<Integer> likersId = post.getLikedUserIds();
        for (int i: likersId){
            if (i == user.getId()){
                return true;
            }
        }
        return false;
    }


    private static void likePost(Post post, Button likeButton){
        User me = (User)database.getCurrentAccount();
        post.addLike(me);
        me.addLikedPost(post);
        likeButton.setText("♡ "+post.getLikes());
        likeButton.getStyleClass().add("active_interaction");
    }
    private static void unLikePost(Post post, Button likeButton){
        User me = (User)database.getCurrentAccount();
        post.removeLike(me);
        me.removeLikedPost(post);
        likeButton.setText("♡ "+post.getLikes());
        likeButton.getStyleClass().remove("active_interaction");
    }

    private static HBox profileSectionBuilder(int postId){
        Circle profileCircle = new Circle();
        User user = database.getUserById(postId);
        Image image = new Image(user.getProfilePicture().getPathUrl());
        profileCircle.setFill(new ImagePattern(image));
        profileCircle.setRadius(21);
        VBox nameAndUsername = new VBox();
        Label name = new Label(user.getFullName());
        Label username = new Label("@" + user.getUsername());
        nameAndUsername.getChildren().addAll(name, username);
        nameAndUsername.setSpacing(3);

        HBox hbox = new HBox();
        hbox.getChildren().addAll(profileCircle, nameAndUsername);
        hbox.setSpacing(10);
        return hbox;
    }

    public static void createPost(String text, File file, int authorId, int parentPostId){
        Post post = new Post(text,file, authorId, LocalDate.now(),
                parentPostId, false);
        database.getUserById(authorId).addPost(post.getId());
        database.addPost(post);
        if (parentPostId != 0){
            database.getPostById(parentPostId).addRepliedPost(post.getId());
        }
        System.out.println("Post created with id: "+ post.getId());
    }
    public static boolean createPost(String text, File file, int parentPostId) {
        User user = (User) database.getCurrentAccount();
        if (user.decreasePostTokens(text, (file!=null))){
            createPost(text, file, user.getId(), parentPostId);
            return true;
        }
        return false;
    }

    public static ArrayList<Post> getRepliedPosts(Post post){
        ArrayList<Integer> postsId = post.getReplyPostIds();
        ArrayList<Post> posts = new ArrayList<>();
        for (int id:postsId){
            posts.add(database.getPostById(id));
        }
        return posts;
    }


}
