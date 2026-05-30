package ir.pouyapourarshad.ptwitter.controllers;

import ir.pouyapourarshad.ptwitter.controllers.modelControllers.PostController;
import ir.pouyapourarshad.ptwitter.models.media.image.Image;
import ir.pouyapourarshad.ptwitter.models.media.image.ImageTypes;
import ir.pouyapourarshad.ptwitter.models.posts.Post;
import ir.pouyapourarshad.ptwitter.models.storage.Database;
import ir.pouyapourarshad.ptwitter.models.users.User;
import ir.pouyapourarshad.ptwitter.models.users.normaluser.NormalUser;

import java.time.LocalDate;

public class FakeDatabase {
    private static final Database database = Database.getInstance();
    public static void generateFakeDatabase(){
        database.addUser(new NormalUser("Ali_Kazemi", "123456789",
                "Ali Kazemi", LocalDate.of(1980,10,14),
                "AliKazemi@Pouya.ir", "09021231234", new Image("C:\\Users\\red pc\\Desktop\\AliKazemi.jpg", ImageTypes.JPG),
                LocalDate.now(), "Computer Science Professor at Chicago University;"));

        database.addUser(new NormalUser("Aboli", "123456789",
                "Abolfazl Ghanbari", LocalDate.of(1990,10,14),
                "Abouli@Pouya.ir", "09021231234", new Image("C:\\Users\\red pc\\Desktop\\Abol.jpg", ImageTypes.JPG),
                LocalDate.now(), "Head of HR at @Dayereh Holding."));

        database.addUser(new NormalUser("Hp", "123456789",
                "Ali HoseinPour", LocalDate.of(1999,9,9),
                "hp@Pouya.ir", "09021231234", new Image("C:\\Users\\red pc\\Desktop\\alihp.jpg", ImageTypes.JPG),
                LocalDate.now(), "Dayereh, The worst enemy you can have!"));

        database.addUser(new NormalUser("Dayereh", "123456789",
                "Dayereh Holding", LocalDate.of(1999,9,9),
                "Dayereh@Pouya.ir", "09021231234", new Image("C:\\Users\\red pc\\Desktop\\Dayereh.jpg", ImageTypes.JPG),
                LocalDate.now(), "Dayereh, The worst enemy you can have!"));

        PostController.createPost("Shoma Anja bakhtid ke sedaghat dashtid, Ba kasani ke siasat dashtand! \n #Siasat #Abol #Sedaghat",
                null, database.getUserByUsername("Aboli").getId(), 0);

        PostController.createPost("Maybe im batman, who know? \n #iran",
                null, database.getUserByUsername("Ali_Kazemi").getId(), 0);

        PostController.createPost("Salamp!\n #salamp #iran",
                null, database.getUserByUsername("Hp").getId(), 0);

        PostController.createPost("oh guys i found sth new in #GPT! #me",
                null, database.getUserByUsername("p").getId(), 0);


    }

}
