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

public class GameBoard {
    private BorderPane borderPane;
    private Tile[][] tiles;
    private ImageView[] imageViews;
    private ImageView ball;
    private Pane pane;
    private boolean isLevelCompleted;
    //private Stage primaryStage;
    private Scene boardScene;
    private Button checkButton;
    private Button nextButton;
    private static int totalLevelNo;
    private ArrayList<File> levels;

    public GameBoard(){
        File folder = new File("src/Levels");
        ArrayList<File> levels = new ArrayList<>();
        for (int l = 0; l < folder.list().length; l++) {
            String levelNo = l + 1 + "";
            File file = new File("src/Levels/level" + levelNo + ".txt");
            levels.add(file);
        }
        Button checkButton = new Button("Check");
        checkButton.setDisable(true);
        setCheckButton(checkButton);
        Button nextButton = new Button("Next");
        nextButton.setDisable(true);
        setNextButton(nextButton);
        setLevels(levels);
        setTotalLevelNo(levels.size());
        setBoardScene(makeScene(levels, Main.getLevelNumber()));
    }

    public void displayNumberOfMoves(){
        Label label = new Label("Number of Moves " + Main.getNumberOfMoves());
        label.setStyle("-fx-text-fill: white");
        label.setFont(Font.font("Arial", FontWeight.BOLD, 30));
        EdgePane edgePane = new EdgePane(label);
        edgePane.setBackground(new Background(new BackgroundImage(new Image("Background.jpg"), BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT)));
        getBorderPane().setTop(edgePane);
    }


    public Scene makeScene(ArrayList<File> levels,int levelNo){

        createTiles();
        createImageViews();

        //Creating the pane
        BorderPane borderPane = new BorderPane();
        Pane pane = new Pane();
        pane.setBackground(new Background(new BackgroundImage(new Image("Background.jpg"), BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT)));
        borderPane.setRight(new EdgePane(new Label(" "))); // delete later
        pane.getChildren().addAll(getImageViews());
        borderPane.setBottom(new TwoButtons(getCheckButton(), getNextButton()));
        borderPane.setLeft(new EdgePane(new Label(" "))); // delete later
        borderPane.setCenter(pane);

        pane.getChildren().add(createBall());
        setPane(pane);
        setBorderPane(borderPane);
        displayNumberOfMoves();
        Scene scene = new Scene(borderPane, 950, 780);
        setBoardScene(scene);
        return getBoardScene();
    }


    public ImageView[] createImageViews(){
        ArrayList<ImageView> imageViewsArrayList = new ArrayList<>();
        for (int i = 0; i < 16; i++) {
            final double sizeOfTile = 140;

            ImageView imageView = new ImageView(tiles[i / 4][i % 4].getImage());
            imageView.setX((i % 4) * 143);
            imageView.setY((i / 4) * 143);
            imageView.setFitHeight(sizeOfTile);
            imageView.setFitWidth(sizeOfTile);
            imageViewsArrayList.add(imageView);
        }
        ImageView[] imageViews = new ImageView[16];
        imageViews = imageViewsArrayList.toArray(imageViews);
        setImageViews(imageViews);


        return imageViews;
    }

    public Tile[][] createTiles(){
        Scanner input;
        try {
            input = new Scanner(getLevels().get(Main.getLevelNumber()));
        } catch (FileNotFoundException e) {
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
        Tile[][] tiles = new Tile[4][4];
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
        setTiles(tiles);
        return tiles;
    }

    public ImageView createBall(){
        ImageView ball = new ImageView(new Image("ball.png"));
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
            ball.setX(getImageViews()[imageViewIndex].getX()+ 42.5);
            ball.setY(getImageViews()[imageViewIndex].getY()  + 30);
        }
        if (tiles[indexOfStarterX][indexOfStarterY].getStatus().equalsIgnoreCase("Horizontal")) {
            ball.setFitWidth(55);
            ball.setFitHeight(55);
            ball.setX(getImageViews()[imageViewIndex].getX()+ 52.5);
            ball.setY(getImageViews()[imageViewIndex].getY()  + 42.3);
        }
        setBall(ball);
        return ball;
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

    public boolean isLevelCompleted() {
        return isLevelCompleted;
    }

    public void setLevelCompleted(boolean levelCompleted) {
        isLevelCompleted = levelCompleted;
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

    public static int getTotalLevelNo() {
        return totalLevelNo;
    }

    public static void setTotalLevelNo(int totalLevelNo) {
        GameBoard.totalLevelNo = totalLevelNo;
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


class EdgePane extends StackPane {
    /*
        public EdgePane(Button button, Button button2){

            getChildren().add(button);
            getChildren().add(button2);
            setPadding(new Insets(25.5, 57.5, 50.5, 45.5));

            setBackground(new Background(new BackgroundImage(new Image("Background.jpg"), BackgroundRepeat.NO_REPEAT,
                    BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT)));
        }
    */
    public EdgePane(Label label) {
        getChildren().add(label);
        setPadding(new Insets(10, 57.5, 75, 105.5));
        setBackground(new Background(new BackgroundImage(new Image("Background.jpg"), BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT)));
    }

}

class TwoButtons extends HBox {

    public TwoButtons(Button button, Button button2){
        getChildren().add(button);
        getChildren().add(button2);
        setPadding(new Insets(25.5, 57.5, 50.5, 45.5));

        setBackground(new Background(new BackgroundImage(new Image("Background.jpg"), BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT)));
    }

}