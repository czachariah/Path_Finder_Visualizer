package Visualizer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * This is the Main class that is used to build and run the GUI.
 */
public class Main extends Application {

    public final int FIXED_WIDTH = 975;
    public final int FIXED_LENGTH = 1800;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/Visualizer/GridViewer.fxml"));
        primaryStage.setTitle("Path Finder Visualizer");
        primaryStage.setScene(new Scene(root, FIXED_LENGTH, FIXED_WIDTH));
        //primaryStage.setResizable(false);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
