package ir.pouyapourarshad.ptwitter.view;

import ir.pouyapourarshad.ptwitter.controllers.serviceControllers.SceneManager;
import ir.pouyapourarshad.ptwitter.models.posts.Post;
import ir.pouyapourarshad.ptwitter.models.report.Report;
import ir.pouyapourarshad.ptwitter.models.storage.Database;
import ir.pouyapourarshad.ptwitter.models.users.User;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.util.ArrayList;

public class AdminViewController {
    private static final Database database = Database.getInstance();

    @FXML
    private Label usersCountLabel;

    @FXML
    private Label postsCountLabel;

    @FXML
    private Label hashtagsCountLabel;

    @FXML
    private Button showUsersButton;

    @FXML
    private Button showPostsButton;

    @FXML
    private Button showUserReportsButton;

    @FXML
    private Button showPostReportsButton;

    @FXML
    public void initialize() {
        loadDashboardStats();
        setupActions();
    }

    private void loadDashboardStats() {
        usersCountLabel.setText(Integer.toString(database.getUsers().size()));
        postsCountLabel.setText(Integer.toString(database.getPosts().size()));
        hashtagsCountLabel.setText(Integer.toString(database.getHashtags().size()));
    }

    private void setupActions() {
        showUsersButton.setOnAction(event -> showAllUsers());
        showPostsButton.setOnAction(event -> showAllPosts());
        showUserReportsButton.setOnAction(event -> showUserReports());
        showPostReportsButton.setOnAction(event -> showPostReports());
    }

    private void showAllUsers() {
        SceneManager.showOnSameStage("UsersPage.fxml", "");
    }

    private void showAllPosts() {
        ArrayList<Post> posts = database.getPosts();
        StringBuilder result = new StringBuilder();
        for (Post post : posts){
            result.append("Post #")
                    .append(post.getId())
                    .append(" by user ")
                    .append(post.getAuthorId())
                    .append(": ")
                    .append(post.getText())
                    .append("\n");
        }
        showAdminInfo("All Posts", result.isEmpty() ? "No posts found." : result.toString());
    }

    private void showUserReports() {
        ArrayList<Report> reports = database.getReports();
        StringBuilder result = new StringBuilder();
        for (Report report : reports){
            result.append("Report #")
                    .append(report.getUid())
                    .append(" reporter: ")
                    .append(report.getReporterUid())
                    .append(" reported user: ")
                    .append(report.getReportedUid())
                    .append(" status: ")
                    .append(report.getReportStatus())
                    .append("\n");
        }
        showAdminInfo("User Reports", result.isEmpty() ? "No reports found." : result.toString());
    }

    private void showPostReports() {
        ArrayList<Report> reports = database.getReports();
        StringBuilder result = new StringBuilder();
        for (Report report : reports){
            result.append("Report #")
                    .append(report.getUid())
                    .append(" description: ")
                    .append(report.getDescription())
                    .append(" status: ")
                    .append(report.getReportStatus())
                    .append("\n");
        }
        showAdminInfo("Post Reports", result.isEmpty() ? "No reports found." : result.toString());
    }

    private void showAdminInfo(String title, String content){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
