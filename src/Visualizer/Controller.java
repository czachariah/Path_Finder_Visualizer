package Visualizer;

import Grid.*;
import Grid.Cell;
import Heuristic.*;
import SearchAlgos.*;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;

import java.util.List;
import java.util.Set;


public class Controller {

    // Grid and Cell global variables
    public Grid grid;
    public Cell cell;

    // global heuristic variables
    public ManhattanDistance manhattanDistance;
    public ManhattanDistanceByFour manhattanDistanceByFour;
    public EuclideanDistance euclideanDistance;
    public EuclideanDistanceByFour euclideanDistanceByFour;
    public Chebyshev chebyshev;

    // global Search variables
    public AStarSearch aStarSearch;
    public WeightedAStarSearch weightedAStarSearch;
    public UniformCostSearch uniformCostSearch;
    public SequentialAStarSearch sequentialAStarSearch;

    // global weights
    public float weightForWeightedAStar;
    public float weight1ForSequentialSearch;
    public float weight2ForSequentialSearch;

    // other global variables
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
    public ToggleGroup HeuristicGroup;

    @FXML
    public Toggle ManhattanDistanceRadioButton;

    @FXML
    public Toggle EuclideanDistanceRadioButton;

    @FXML
    public Toggle ManhattanDistanceByFourRadioButton;

    @FXML
    public Toggle EuclideanDistanceByFourRadioButton;

    @FXML
    public Button runAStar;

    @FXML
    public Button runUniformCostSearch;

    @FXML
    public Button runWeightedAStar;

    @FXML
    public TextField weightedAStarWeight;



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
        colorGridBeforePath(grid);
    }



    public void colorGridBeforePath(Grid gridToColor) {
        TextOutput.appendText("\n\nStart Cell: ("+gridToColor.getStartCell()[0][0]+" , "+gridToColor.getStartCell()[0][1]+")\n");
        TextOutput.appendText("End Cell: ("+gridToColor.getEndCell()[0][0]+" , "+gridToColor.getEndCell()[0][1]+")");
        this.gridPane.getChildren().clear();

        Rectangle rect;
        Color color = Color.BLACK;
        Cell[][] arr = gridToColor.getGrid();

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
                this.displayRect[r - 1][c - 1] = rect;
                gridPane.add(rect, c, r);
            }
        }
    }


    private void addClick(Rectangle rect, int c, int r, List<Cell> path, Set<Cell> explored) {

        rect.setOnMouseClicked(e -> {
            int realc = c-1;
            int realr = r-1;
            Cell curCell = grid.getGrid()[realr][realc];
            if (path.contains(curCell) || explored.contains(curCell)) {
                TextOutput.appendText("\n\nCell ["+realr+"]["+realc+"] clicked.\n");
                TextOutput.appendText("G-Cost: " + curCell.getgCost() + "\n");
                TextOutput.appendText("H-Cost: " + curCell.gethCost() + "\n");
                TextOutput.appendText("F-Cost: " + curCell.getfCost());
            } else {
                TextOutput.appendText("\n\nCell ["+realr+"]["+realc+"] clicked.\n");
                TextOutput.appendText("CELL HAS NOT BEEN VISITED \nAND IS NOT PART OF THE SHORTEST PATH.");
            }
        });
    }



    public void runAStarClicked() {
        TextOutput.appendText("\n\nRunning A Star Search ...");
        if (ManhattanDistanceRadioButton.isSelected()) {
            manhattanDistance = new ManhattanDistance(this.grid);
            aStarSearch = new AStarSearch(grid,manhattanDistance);
            aStarSearch.run();
            List<Cell> path = aStarSearch.getPath();
            Set<Cell> explored = aStarSearch.getExploredCells();
            if (path == null) {
                TextOutput.appendText("\nNO PATH FOUND or SEARCH WAS DONE ON THIS GRID");
            } else {
                TextOutput.appendText("\nPATH FOUND!");
                TextOutput.appendText("\nLength of the path: " + path.size() + " cells.");
                TextOutput.appendText("\nNumber of cells visited: " + explored.size() + "cells");
                TextOutput.appendText("\nCost of the path: " + aStarSearch.getPathCost());
                colorGridAfterPath(grid,path, explored);
            }
        } else if (EuclideanDistanceRadioButton.isSelected()) {
            euclideanDistance = new EuclideanDistance(this.grid);
            aStarSearch = new AStarSearch(grid,euclideanDistance);
            aStarSearch.run();
            List<Cell> path = aStarSearch.getPath();
            Set<Cell> explored = aStarSearch.getExploredCells();
            if (path == null) {
                TextOutput.appendText("\nNO PATH FOUND or SEARCH WAS DONE ON THIS GRID");
            } else {
                TextOutput.appendText("\nPATH FOUND!");
                TextOutput.appendText("\nLength of the path: " + path.size() + " cells.");
                TextOutput.appendText("\nNumber of cells visited: " + explored.size() + "cells");
                TextOutput.appendText("\nCost of the path: " + aStarSearch.getPathCost());
                colorGridAfterPath(grid,path, explored);
            }
        } else if (ManhattanDistanceByFourRadioButton.isSelected()) {
            manhattanDistanceByFour = new ManhattanDistanceByFour(this.grid);
            aStarSearch = new AStarSearch(grid,manhattanDistanceByFour);
            aStarSearch.run();
            List<Cell> path = aStarSearch.getPath();
            Set<Cell> explored = aStarSearch.getExploredCells();
            if (path == null) {
                TextOutput.appendText("\nNO PATH FOUND or SEARCH WAS DONE ON THIS GRID");
            } else {
                TextOutput.appendText("\nPATH FOUND!");
                TextOutput.appendText("\nLength of the path: " + path.size() + " cells.");
                TextOutput.appendText("\nNumber of cells visited: " + explored.size() + "cells");
                TextOutput.appendText("\nCost of the path: " + aStarSearch.getPathCost());
                colorGridAfterPath(grid,path, explored);
            }
        } else if (EuclideanDistanceByFourRadioButton.isSelected()) {
            euclideanDistanceByFour = new EuclideanDistanceByFour(this.grid);
            aStarSearch = new AStarSearch(grid,euclideanDistanceByFour);
            aStarSearch.run();
            List<Cell> path = aStarSearch.getPath();
            Set<Cell> explored = aStarSearch.getExploredCells();
            if (path == null) {
                TextOutput.appendText("\nNO PATH FOUND or SEARCH WAS DONE ON THIS GRID");
            } else {
                TextOutput.appendText("\nPATH FOUND!");
                TextOutput.appendText("\nLength of the path: " + path.size() + " cells.");
                TextOutput.appendText("\nNumber of cells visited: " + explored.size() + "cells");
                TextOutput.appendText("\nCost of the path: " + aStarSearch.getPathCost());
                colorGridAfterPath(grid,path, explored);
            }
        } else {
            chebyshev = new Chebyshev(this.grid);
            aStarSearch = new AStarSearch(grid,chebyshev);
            aStarSearch.run();
            List<Cell> path = aStarSearch.getPath();
            Set<Cell> explored = aStarSearch.getExploredCells();
            if (path == null) {
                TextOutput.appendText("\nNO PATH FOUND or SEARCH WAS DONE ON THIS GRID");
            } else {
                TextOutput.appendText("\nPATH FOUND!");
                TextOutput.appendText("\nLength of the path: " + path.size() + " cells.");
                TextOutput.appendText("\nNumber of cells visited: " + explored.size() + "cells");
                TextOutput.appendText("\nCost of the path: " + aStarSearch.getPathCost());
                colorGridAfterPath(grid,path, explored);
            }
        }
    }

    public void runUniformCostSearchButtonClicked() {
        TextOutput.appendText("\n\nRunning Uniform Cost Search ...");
        uniformCostSearch = new UniformCostSearch(this.grid);
        uniformCostSearch.run();
        List<Cell> path = uniformCostSearch.getPath();
        Set<Cell> explored = uniformCostSearch.getExploredCells();
        if (path == null) {
            TextOutput.appendText("\nNO PATH FOUND or SEARCH WAS DONE ON THIS GRID");
        } else {
            TextOutput.appendText("\nPATH FOUND!");
            TextOutput.appendText("\nLength of the path: " + path.size() + " cells.");
            TextOutput.appendText("\nNumber of cells visited: " + explored.size() + "cells");
            TextOutput.appendText("\nCost of the path: " + uniformCostSearch.getPathCost());
            colorGridAfterPath(grid,path, explored);
        }
    }



    public void runWeightedAStarButtonClicked() {
        float weight = 1;
        try {
            weight = Float.parseFloat(weightedAStarWeight.getText());
        } catch (Exception e) {
            TextOutput.appendText("\n\nPLEASE MAKE SURE THE WEIGHTS ARE DECIMALS.");
        }
        if (weight >= 1) {
            TextOutput.appendText("\n\nRunning Weighted A Star Search ...");
            if (ManhattanDistanceRadioButton.isSelected()) {
                manhattanDistance = new ManhattanDistance(this.grid);
                weightedAStarSearch = new WeightedAStarSearch(grid,manhattanDistance,weight);
                weightedAStarSearch.run();
                List<Cell> path = weightedAStarSearch.getPath();
                Set<Cell> explored = weightedAStarSearch.getExploredCells();
                if (path == null) {
                    TextOutput.appendText("\nNO PATH FOUND or SEARCH WAS DONE ON THIS GRID");
                } else {
                    TextOutput.appendText("\nPATH FOUND!");
                    TextOutput.appendText("\nLength of the path: " + path.size() + " cells.");
                    TextOutput.appendText("\nNumber of cells visited: " + explored.size() + "cells");
                    TextOutput.appendText("\nCost of the path: " + weightedAStarSearch.getPathCost());
                    colorGridAfterPath(grid,path,explored);
                }
            } else if (EuclideanDistanceRadioButton.isSelected()) {
                euclideanDistance = new EuclideanDistance(this.grid);
                weightedAStarSearch = new WeightedAStarSearch(grid,euclideanDistance,weight);
                weightedAStarSearch.run();
                List<Cell> path = weightedAStarSearch.getPath();
                Set<Cell> explored = weightedAStarSearch.getExploredCells();
                if (path == null) {
                    TextOutput.appendText("\nNO PATH FOUND or SEARCH WAS DONE ON THIS GRID");
                } else {
                    TextOutput.appendText("\nPATH FOUND!");
                    TextOutput.appendText("\nLength of the path: " + path.size() + " cells.");
                    TextOutput.appendText("\nNumber of cells visited: " + explored.size() + "cells");
                    TextOutput.appendText("\nCost of the path: " + weightedAStarSearch.getPathCost());
                    colorGridAfterPath(grid,path,explored);
                }
            } else if (ManhattanDistanceByFourRadioButton.isSelected()) {
                manhattanDistanceByFour = new ManhattanDistanceByFour(this.grid);
                aStarSearch = new AStarSearch(grid,manhattanDistanceByFour);
                aStarSearch.run();
                List<Cell> path = aStarSearch.getPath();
                Set<Cell> explored = aStarSearch.getExploredCells();
                if (path == null) {
                    TextOutput.appendText("\nNO PATH FOUND or SEARCH WAS DONE ON THIS GRID");
                } else {
                    TextOutput.appendText("\nPATH FOUND!");
                    TextOutput.appendText("\nLength of the path: " + path.size() + " cells.");
                    TextOutput.appendText("\nNumber of cells visited: " + explored.size() + "cells");
                    TextOutput.appendText("\nCost of the path: " + aStarSearch.getPathCost());
                    colorGridAfterPath(grid,path, explored);
                }
            } else if (EuclideanDistanceByFourRadioButton.isSelected()) {
                euclideanDistanceByFour = new EuclideanDistanceByFour(this.grid);
                aStarSearch = new AStarSearch(grid,euclideanDistanceByFour);
                aStarSearch.run();
                List<Cell> path = aStarSearch.getPath();
                Set<Cell> explored = aStarSearch.getExploredCells();
                if (path == null) {
                    TextOutput.appendText("\nNO PATH FOUND or SEARCH WAS DONE ON THIS GRID");
                } else {
                    TextOutput.appendText("\nPATH FOUND!");
                    TextOutput.appendText("\nLength of the path: " + path.size() + " cells.");
                    TextOutput.appendText("\nNumber of cells visited: " + explored.size() + "cells");
                    TextOutput.appendText("\nCost of the path: " + aStarSearch.getPathCost());
                    colorGridAfterPath(grid,path, explored);
                }
            } else {
                chebyshev = new Chebyshev(this.grid);
                aStarSearch = new AStarSearch(grid,chebyshev);
                aStarSearch.run();
                List<Cell> path = aStarSearch.getPath();
                Set<Cell> explored = aStarSearch.getExploredCells();
                if (path == null) {
                    TextOutput.appendText("\nNO PATH FOUND or SEARCH WAS DONE ON THIS GRID");
                } else {
                    TextOutput.appendText("\nPATH FOUND!");
                    TextOutput.appendText("\nLength of the path: " + path.size() + " cells.");
                    TextOutput.appendText("\nNumber of cells visited: " + explored.size() + "cells");
                    TextOutput.appendText("\nCost of the path: " + aStarSearch.getPathCost());
                    colorGridAfterPath(grid,path, explored);
                }
            }
        } else {
            TextOutput.appendText("\n\nPLEASE USE A WEIGHT >= 1.");
        }
    }




    public void colorGridAfterPath(Grid gridToColor, List<Cell> path, Set<Cell> explored) {
        gridPane.getChildren().clear();

        Rectangle rect;
        Color color = Color.BLACK;
        Cell[][] arr = gridToColor.getGrid();

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
                addClick(rect, c, r,path,explored);
                this.displayRect[r - 1][c - 1] = rect;
                gridPane.add(rect, c, r);
            }
        }
    }


    public void generateNewGridButtonClicked() {
        grid = new Grid();
        grid.generateEntireGrid();
        colorGridBeforePath(grid);
    }

    public void loadSavedGridButtonClicked() {
        fileChoose = new FileChooser();
        fileChoose.setInitialDirectory(new File("./src/SavedGrids/"));
        File file = fileChoose.showOpenDialog(stage);
        if(file != null) {
            grid = new Grid();
            grid.importGrid(file);
            colorGridBeforePath(grid);
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
