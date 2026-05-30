package ir.pouyapourarshad.ptwitter.controllers.pageControllers;

import ir.pouyapourarshad.ptwitter.models.storage.Database;
import ir.pouyapourarshad.ptwitter.models.users.Account;
import ir.pouyapourarshad.ptwitter.models.users.User;

public class EditInfoController {
    static Database database = Database.getInstance();
    static Account currentAccount = database.getCurrentAccount();

    public static User getUser(){
        return (User)currentAccount;
    }
}

