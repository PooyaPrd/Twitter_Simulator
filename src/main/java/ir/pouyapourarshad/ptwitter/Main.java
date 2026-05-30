//   The hidden law of a probable outcome
//        The numbers lead a dance;
//                                 (Shape of my heart, Sting)

// P (Twitter) Project, 5/07/2026, Ui!

package ir.pouyapourarshad.ptwitter;

import ir.pouyapourarshad.ptwitter.controllers.FakeDatabase;
import ir.pouyapourarshad.ptwitter.models.media.image.Image;
import ir.pouyapourarshad.ptwitter.models.media.image.ImageTypes;
import ir.pouyapourarshad.ptwitter.models.posts.Hashtag;
import ir.pouyapourarshad.ptwitter.models.posts.Post;
import ir.pouyapourarshad.ptwitter.models.storage.Database;
import ir.pouyapourarshad.ptwitter.models.users.Admin;
import ir.pouyapourarshad.ptwitter.models.users.User;
import ir.pouyapourarshad.ptwitter.models.users.normaluser.NormalUser;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.ArrayList;

public class Main extends Application {
    private static final Database database = Database.getInstance();
    public static Stage mainStage;

    @Override
    public void start(Stage stage) throws Exception {
        mainStage = stage;
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("Auth.fxml"));
        Scene scene = new Scene(loader.load());
        stage.setTitle(":P | Welcome");
        stage.setScene(scene);
        stage.getIcons().add(new javafx.scene.image.Image(Main.class.getResourceAsStream("assets/images/icon.png")));
        NormalUser user = new NormalUser("p","1", "Pouya Pourarshad",
                LocalDate.of(1,1,1), "PouyaPourarshad@Pouya.ir",
                "09023735065", null, LocalDate.now(), "");
        database.addUser(user);

        Admin admin = Admin.getInstance();

//        user.addFavoriteHashtags(2);
        FakeDatabase.generateFakeDatabase();
        user.setProfilePicture(new Image("C:\\Users\\red pc\\IdeaProjects\\P-Twitter\\src\\main\\resources\\ir\\pouyapourarshad\\ptwitter\\assets\\images\\default_profile.jpg", ImageTypes.JPG));

        database.addHashtag(new Hashtag("#Education", new ArrayList<Integer>()));
        database.addHashtag(new Hashtag("#Sports", new ArrayList<Integer>()));
        database.addHashtag(new Hashtag("#News", new ArrayList<Integer>()));
        database.addHashtag(new Hashtag("#Iran", new ArrayList<Integer>()));
        ArrayList<Hashtag> hashtags =  database.getHashtags();
        user.addFavoriteHashtags(7);


        stage.show();
    }
}

