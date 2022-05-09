import Tiles.*;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/** The class GameBoard
 * This class creates the gameboard of the game using the input.
 * Name Surname / Student ID: Beyza Nur Kaya / 150120077
 * Name Surname / Student ID: Sena Ektiricioğlu / 150120047
 */

public class GameBoard {

    public static int numberOfMoves; // numberOfMoves data field represent number of moves made by gamer at that level.
    private BorderPane borderPane; // borderPane data field represent border pane for game board
    private Pane pane;
    private Scene boardScene; // boardScene data field represent
    private Tile[][] tiles; // tiles data field holds all tiles in that level
    private ImageView[] imageViews; // imageView data field holds all tiles' images in that level
    private ImageView ball; // ball data field holds ball that will move at that level
    private Button checkButton; // checkButton data field represent button for the player can control the path s/he creates
    private Button nextButton; // nextButton data field represent button that provides to go to next level
    private static int totalLevelNumber; // totalLevelNumber data field represents total level number in the game
    private ArrayList<File> levels; // levels data field holds all level in levels package

    public GameBoard() {

        File folder = new File("src/Levels");
        // Create ArrayList levels that holds all level in levels package
        levels = new ArrayList<>();
        for (int l = 0; l < folder.list().length; l++) {
            String levelNo = l + 1 + "";
            File file = new File("src/Levels/level" + levelNo + ".txt");
            levels.add(file);
        }

        // Create buttons to check the path and go to next level
        checkButton = new Button("Check");
        checkButton.setDisable(true); // If the gamer couldn't create the path, the control button will not be active

        nextButton = new Button("Next");
        nextButton.setDisable(true); // If the ball don't reach end pipe tile, the next button will not be active

        setTotalLevelNumber(levels.size());
        setBoardScene(makeScene());
    }

    /** Method to create game scene */
    protected Scene makeScene(){

        createTiles();
        createImageViews();

        borderPane = new BorderPane();
        pane = new Pane();
        borderPane.setBackground(new Background(new BackgroundImage(new Image("Assets/Background.jpg"), BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT)));
        borderPane.setRight(edgePane(new Label("")));
        pane.getChildren().addAll(getImageViews());
        borderPane.setBottom(gameControlsPane(getCheckButton(), getNextButton()));
        borderPane.setLeft(edgePane(new Label("")));
        borderPane.setCenter(pane);

        pane.getChildren().add(createBall());
        displayNumberOfMoves();
        boardScene = new Scene(borderPane, 950, 780);
        return getBoardScene();
    }

    /** Method to create tiles at that level. */
    private Tile[][] createTiles(){

        // Read input from the current level file
        Scanner input;
        try {
            input = new Scanner(getLevels().get(Main.getLevelNumber()));
        }
        catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        ArrayList<String> lines = new ArrayList<>();
        while (input.hasNext()) {
            lines.add(input.next());
        }

        ArrayList<String> words = new ArrayList<>();
        for (String line : lines) {
            String[] splitLine = line.split(",");
            Collections.addAll(words, splitLine);
        }

        // Create tiles according to current level file
        tiles = new Tile[4][4];
        for (int k = 0; k < words.size(); k += 3) {

            int queue = Integer.parseInt(words.get(k)) - 1;

            if (words.get(k + 1).equalsIgnoreCase("Starter")) {
                tiles[queue / 4][queue % 4] = new StartPipe(words.get(k + 2));
            }

            if (words.get(k + 1).equalsIgnoreCase("Empty")) {
                if (words.get(k + 2).equalsIgnoreCase("Free")) {
                    tiles[queue / 4][queue % 4] = new EmptyFree();
                }

                if (words.get(k + 2).equalsIgnoreCase("none")) {
                    tiles[queue / 4][queue % 4] = new Empty();
                }
            }

            if (words.get(k + 1).equalsIgnoreCase("Pipe")) {
                if (words.get(k + 2).equalsIgnoreCase("Vertical") || words.get(k + 2).equalsIgnoreCase("Horizontal")) {
                    tiles[queue / 4][queue % 4] = new LinearPipe(words.get(k + 2));
                } else if (words.get(k + 2).equals("00") || words.get(k + 2).equals("01") || words.get(k + 2).equals("10") || words.get(k + 2).equals("11")) {
                    tiles[queue / 4][queue % 4] = new CurvedPipeMovable(words.get(k + 2));
                }
            }

            if (words.get(k + 1).equalsIgnoreCase("PipeStatic")) {
                if (words.get(k + 2).equalsIgnoreCase("Vertical") || words.get(k + 2).equalsIgnoreCase("Horizontal")) {
                    tiles[queue / 4][queue % 4] = new NormalPipeStatic(words.get(k + 2));
                } else if (words.get(k + 2).equals("00") || words.get(k + 2).equals("01") || words.get(k + 2).equals("10") || words.get(k + 2).equals("11")) {
                    tiles[queue / 4][queue % 4] = new CurvedPipeStatic(words.get(k + 2));
                }
            }

            if (words.get(k + 1).equalsIgnoreCase("End")) {
                tiles[queue / 4][queue % 4] = new EndPipe(words.get(k + 2));
            }
        }

        return tiles;
    }

    /** Method to create image views at that level. */
    private ImageView[] createImageViews(){

        // Create array list to hold all image views.
        ArrayList<ImageView> imageViewsArrayList = new ArrayList<>();
        for (int i = 0; i < 16; i++) {
            final double sizeOfTile = 140;

            // Create image views and set their coordinates.
            ImageView imageView = new ImageView(tiles[i / 4][i % 4].getImage());
            imageView.setX((i % 4) * 143);
            imageView.setY((i / 4) * 143);

            // Set size of tile as 140 pixel.
            imageView.setFitHeight(sizeOfTile);
            imageView.setFitWidth(sizeOfTile);
            imageViewsArrayList.add(imageView);
        }

        // Convert imageViewsArrayList to imageView array
        imageViews = new ImageView[16];
        imageViews = imageViewsArrayList.toArray(imageViews);

        return imageViews;
    }

    /** Method that returns pane for border pane's left, right and top parts */
    private StackPane edgePane(Label label){
        StackPane edgePane = new StackPane();
        edgePane.getChildren().add(label);
        label.setPadding(new Insets(30, 115.5, 50, 70.5));
        return edgePane;
    }

    /** Method that returns pane for border pane's bottom part */
    private HBox gameControlsPane(Button button, Button button2){
        HBox gameControlsPane = new HBox();
        gameControlsPane.getChildren().add(button);
        gameControlsPane.getChildren().add(button2);
        gameControlsPane.setPadding(new Insets(0, 0, 55, 175));
        return gameControlsPane;
    }

    /** Method to create ball that will move at that level*/
    private ImageView createBall(){

        ball = new ImageView(new Image("Assets/ball.png"));
        int indexOfStarterX = 0;
        int indexOfStarterY = 0;
        for (int i = 0; i < 4; i++){
            for (int j = 0; j < 4; j++){
                if (tiles[i][j] instanceof StartPipe){
                    indexOfStarterX = i;
                    indexOfStarterY = j;
                }
            }
        }
        int imageViewIndex = 0;
        for (int i = 0; i < 16; i++){
            if(tiles[indexOfStarterX][indexOfStarterY].getImage().equals(imageViews[i].getImage())){
                imageViewIndex = i;
            }
        }
        if (tiles[indexOfStarterX][indexOfStarterY].getStatus().equalsIgnoreCase("Vertical")) {
            ball.setFitWidth(55);
            ball.setFitHeight(55);
            ball.setX(getImageViews()[imageViewIndex].getX()+ 44.2);
            ball.setY(getImageViews()[imageViewIndex].getY()  + 30);
        }
        if (tiles[indexOfStarterX][indexOfStarterY].getStatus().equalsIgnoreCase("Horizontal")) {
            ball.setFitWidth(55);
            ball.setFitHeight(55);
            ball.setX(getImageViews()[imageViewIndex].getX()+ 52.5);
            ball.setY(getImageViews()[imageViewIndex].getY()  + 44.2);
        }

        return ball;
    }

    /** Method to display number of moves has made by gamer at that level */
    protected void displayNumberOfMoves(){
        Label label = new Label("Number of Moves " + getNumberOfMoves());
        label.setStyle("-fx-text-fill: white");
        label.setFont(Font.font("Arial", FontWeight.BOLD, 30));
        StackPane edgePane = edgePane(label);
        getBorderPane().setTop(edgePane);
    }


    public static int getNumberOfMoves() {
        return numberOfMoves;
    }

    public static void setNumberOfMoves(int numberOfMoves) {
        GameBoard.numberOfMoves = numberOfMoves;
    }

    public BorderPane getBorderPane() {
        return borderPane;
    }

    public void setBorderPane(BorderPane borderPane) {
        this.borderPane = borderPane;
    }

    public Tile[][] getTiles() {
        return tiles;
    }

    public void setTiles(Tile[][] tiles) {
        this.tiles = tiles;
    }

    public ImageView[] getImageViews() {
        return imageViews;
    }

    public void setImageViews(ImageView[] imageViews) {
        this.imageViews = imageViews;
    }

    public ImageView getBall() {
        return ball;
    }

    public void setBall(ImageView ball) {
        this.ball = ball;
    }

    public Pane getPane() {
        return pane;
    }

    public void setPane(Pane pane) {
        this.pane = pane;
    }

    public Scene getBoardScene() {
        return boardScene;
    }

    public void setBoardScene(Scene boardScene) {
        this.boardScene = boardScene;
    }

    public Button getCheckButton() {
        return checkButton;
    }

    public void setCheckButton(Button checkButton) {
        this.checkButton = checkButton;
    }

    public static int getTotalLevelNumber() {
        return totalLevelNumber;
    }

    public static void setTotalLevelNumber(int totalLevelNumber) {
        GameBoard.totalLevelNumber = totalLevelNumber;
    }

    public ArrayList<File> getLevels() {
        return levels;
    }

    public void setLevels(ArrayList<File> levels) {
        this.levels = levels;
    }

    public Button getNextButton() {
        return nextButton;
    }

    public void setNextButton(Button nextButton) {
        this.nextButton = nextButton;
    }

}


