package ir.pouyapourarshad.ptwitter.view;

import ir.pouyapourarshad.ptwitter.controllers.modelControllers.PostController;
import ir.pouyapourarshad.ptwitter.models.posts.Post;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class PostPageViewController {
    @FXML
    private VBox resultsContainer;

    public void renderPosts(Post post){
        resultsContainer.getChildren().clear();

        VBox mainPostCard = PostController.longPostBoxBuilder(post);
        mainPostCard.setOnMouseClicked(e -> doNothing());
        resultsContainer.getChildren().add(mainPostCard);

        ArrayList<Post> posts = PostController.getRepliedPosts(post);
        for (Post repliedPost : posts){
            resultsContainer.getChildren().add(PostController.shortPostBoxBuilder(repliedPost));
        }


    }


    private void doNothing(){

    }
}
