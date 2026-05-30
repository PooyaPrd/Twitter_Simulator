module com.example.ptwitter {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires jdk.compiler;
    requires jdk.xml.dom;
    requires javafx.media;


    opens ir.pouyapourarshad.ptwitter to javafx.fxml;
    opens ir.pouyapourarshad.ptwitter.view to javafx.fxml;
    opens ir.pouyapourarshad.ptwitter.controllers.modelControllers to javafx.fxml;
    opens ir.pouyapourarshad.ptwitter.controllers.serviceControllers to javafx.fxml;
    exports ir.pouyapourarshad.ptwitter;
    opens ir.pouyapourarshad.ptwitter.controllers.pageControllers to javafx.fxml;
    opens ir.pouyapourarshad.ptwitter.view.profilepage to javafx.fxml;

}