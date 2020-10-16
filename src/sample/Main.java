package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public final int FIXED_WIDTH = 1700;
    public final int FIXED_LENGTH = 1200;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("GridViewer.fxml"));
        primaryStage.setTitle("Path Finder Visualizer");
        primaryStage.setScene(new Scene(root, FIXED_WIDTH, FIXED_LENGTH));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
