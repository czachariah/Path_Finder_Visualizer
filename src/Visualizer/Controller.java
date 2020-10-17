package Visualizer;

import Grid.*;
import Heuristic.*;
import SearchAlgos.*;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.TextField;
import javafx.scene.shape.Rectangle;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import javafx.scene.control.TextArea;
import javafx.geometry.*;

import java.util.List;
import java.util.Set;


public class Controller {

    public Grid grid;

    public Rectangle[][] displayRect;

    public FileChooser fileChoose;

    public Stage stage;

    @FXML
    public TextArea TextOutput;

    @FXML
    public GridPane gridPane;

    @FXML
    public Button generateNewGridButton;

    @FXML
    public Button loadSavedGridButton;

    @FXML
    public Button saveNewGridButton;

    @FXML
    public TextField saveNewGridName;

    @FXML
    public Button runAStar;



    public Controller() {
        this.displayRect = new Rectangle[120][160];
    } // constructor of the Controller() class



    @FXML
    private void initialize() {
        initializeGridGUI(gridPane);
    }



    public void initializeGridGUI(GridPane gridPane) {
        this.gridPane.setPadding(new Insets(2));
        this.gridPane.setHgap(2);
        this.gridPane.setVgap(2);

        this.gridPane = gridPane;
        this.grid = new Grid();
        colorGridBeforePath();
    }



    public void colorGridBeforePath() {
        this.gridPane.getChildren().clear();

        Rectangle rect;
        Cell cell;
        Color color = Color.BLACK;
        Cell[][] arr = grid.getGrid();

        for (int r = 1; r <= 120; r++) {
            for (int c = 1; c <= 160; c++) {

                cell = arr[r-1][c-1];

                if (cell.getType() == 0) {
                    color = Color.BLACK;
                } else if (cell.getType() == 1) {
                    color = Color.DARKOLIVEGREEN;
                } else if (cell.getType() == 2) {
                    color = Color.BEIGE;
                } else if (cell.getType() == 3) {
                    color = Color.LIGHTBLUE;
                } else {
                    color = Color.DARKBLUE;
                }

                if (cell.getX() == grid.getStartCell()[0][0] && cell.getY() == grid.getStartCell()[0][1]) {
                    color = Color.RED;
                }
                if (cell.getX() == grid.getEndCell()[0][0] && cell.getY() == grid.getEndCell()[0][1]) {
                    color = Color.RED;
                }



                rect = new Rectangle(6, 6, color);
                addClick(rect, c, r);
                this.displayRect[r - 1][c - 1] = rect;
                gridPane.add(rect, c, r);
            }
        }
    }


    private void addClick(Rectangle rect, int c, int r) {
        // nothing for now
    }



    public void runAStarClicked() {
        EuclideanDistance heu = new EuclideanDistance(grid);
        AStarSearch a = new AStarSearch(grid,heu);
        a.run();
        List<Cell> path = a.getPath();
        Set<Cell> explored = a.getExploredCells();
        colorGridAfterPath(path, explored);
    }



    public void colorGridAfterPath(List<Cell> path, Set<Cell> explored) {
        gridPane.getChildren().clear();

        Rectangle rect;
        Cell cell;
        Color color = Color.BLACK;
        Cell[][] arr = grid.getGrid();

        for (int r = 1; r <= 120; r++) {
            for (int c = 1; c <= 160; c++) {

                cell = arr[r-1][c-1];

                if (path.contains(arr[r-1][c-1])) {
                    color = Color.RED;
                } else if(explored.contains(arr[r-1][c-1])) {
                    color = Color.ORANGE;
                } else if (cell.getType() == 0) {
                    color = Color.BLACK;
                } else if (cell.getType() == 1) {
                    color = Color.DARKOLIVEGREEN;
                } else if (cell.getType() == 2) {
                    color = Color.BEIGE;
                } else if (cell.getType() == 3) {
                    color = Color.LIGHTBLUE;
                } else {
                    color = Color.DARKBLUE;
                }

                rect = new Rectangle(6, 6, color);
                //addClick(rect, c, r);
                this.displayRect[r - 1][c - 1] = rect;
                gridPane.add(rect, c, r);
            }
        }
    }


    public void generateNewGridButtonClicked() {
        this.grid = new Grid();
        grid.generateEntireGrid();
        colorGridBeforePath();
    }

    public void loadSavedGridButtonClicked() {
        fileChoose = new FileChooser();
        fileChoose.setInitialDirectory(new File("./src/SavedGrids/"));
        File file = fileChoose.showOpenDialog(stage);
        if(file != null) {
            grid = new Grid();
            grid.importGrid(file);
            colorGridBeforePath();
        }
    }

    public void saveNewGridButtonClicked() {
        String pathToDirectory = "./src/SavedGrids/";
        String newFileName = saveNewGridName.getText();
        pathToDirectory += newFileName;
        System.out.println(pathToDirectory);
        File file = new File(pathToDirectory);
        if(file != null) {
            grid.saveGrid(file);
        }
    }



} // ends the Controller Class
