package ir.pouyapourarshad.ptwitter.controllers.pageControllers;

import ir.pouyapourarshad.ptwitter.models.storage.Database;
import ir.pouyapourarshad.ptwitter.models.users.Account;

public class PostNewController {
    private static final Database database = Database.getInstance();
    private static final Account currentAccount = database.getCurrentAccount();

}
