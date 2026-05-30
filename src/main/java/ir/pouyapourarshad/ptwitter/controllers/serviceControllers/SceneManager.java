package ir.pouyapourarshad.ptwitter.controllers.serviceControllers;

import ir.pouyapourarshad.ptwitter.Main;
import ir.pouyapourarshad.ptwitter.view.AccountPageViewController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneManager {
    static Stage mainStage = Main.mainStage;

    public static void showOnSameStage(String fxml_path, String title){
        try{
            FXMLLoader loader = new FXMLLoader(Main.class.getResource(fxml_path));
            Scene scene = new Scene(loader.load());
            mainStage.setTitle("P. " + title);
            mainStage.setScene(scene);
            mainStage.show();

        } catch (IOException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    public static void showInfo(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.getDialogPane().getStylesheets().add(
                Main.class.getResource("style/alerts.css").toExternalForm()
        );
        alert.showAndWait();
    }

    public static void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.getDialogPane().getStylesheets().add(
                Main.class.getResource("style/alerts.css").toExternalForm()
);

        alert.showAndWait();
    }
}
