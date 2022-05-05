import Tiles.*;
import javafx.animation.PathTransition;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.File;
import java.util.ArrayList;

public class Main extends Application implements Runnable  {

    private Path path;
    private boolean levelCompleted;
    private ArrayList<Tile> pipesInOrder;
    private static int levelNumber;
    private GameBoard gameBoard;
    private boolean draggable;

    public GameBoard getGameBoard() {
        return gameBoard;
    }

    public void setGameBoard(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
    }

    public boolean isDraggable() {
        return draggable;
    }

    public void setDraggable(boolean draggable) {
        this.draggable = draggable;
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        Button startButton = new Button("Play the Game!");
        Button nextLevelButton = new Button("Next Level ->");
        Button closeButton = new Button("Exit!");

        startPage(primaryStage, startButton);
        gameBoard = new GameBoard();
        clickedOnStart(startButton, primaryStage, gameBoard);
        clickedOnCheck(gameBoard);
        clickedOnNext(gameBoard, primaryStage, nextLevelButton, closeButton);
        clickedOnNextLevel(gameBoard, primaryStage, nextLevelButton);
        clickedOnClose(closeButton, primaryStage);

    }

    private void clickedOnClose(Button closeButton, Stage primaryStage){
        closeButton.setOnMouseClicked(event -> {
            primaryStage.close();
        });

    }

    private void startPage(Stage primaryStage, Button startButton){

        Media media = new Media(new File("src/Assets/The_Town_of_Luncheon.wav").toURI().toString());
        AudioClip mediaPlayer = new AudioClip(media.getSource());
        mediaPlayer.setVolume(0);
        mediaPlayer.play();

        primaryStage.setResizable(false);

        Label gameName = new Label("Pipe Ball Maze");
        gameName.setFont(Font.font("Arial", FontWeight.BOLD, 50));
        gameName.setStyle("-fx-text-fill: white");
        startButton.setPrefSize(120,50);
        VBox startPane = new VBox(50);
        startPane.getChildren().add(gameName);
        startPane.getChildren().add(startButton);
        startPane.alignmentProperty().set(Pos.CENTER);
        startPane.setBackground(new Background(new BackgroundImage(new Image("Assets/Background.jpg"), BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT)));
        Scene startScene = new Scene(startPane,950,780);
        primaryStage.setScene(startScene);
        primaryStage.setTitle("PipeBallMaze");
        primaryStage.show();
    }
    private void clickedOnStart(Button startButton, Stage primaryStage, GameBoard gameBoard){

        startButton.setOnMouseClicked(event -> {
            GameBoard.numberOfMoves = 0;
            primaryStage.setScene(gameBoard.makeScene());
            primaryStage.show();
            setDraggable(true);
            setLevelCompleted(checkForSolution(gameBoard));
            drag(gameBoard);

        });

    }

    private void clickedOnCheck(GameBoard gameBoard){

        gameBoard.getCheckButton().setOnMouseClicked(event -> {
            animate(gameBoard);
        });

    }

    private void clickedOnNext(GameBoard gameBoard, Stage primaryStage, Button nextLevelButton, Button closeButton){

        gameBoard.getNextButton().setOnMouseClicked(event -> {
            if(getLevelNumber() < (GameBoard.getTotalLevelNo())) {
                primaryStage.setScene(levelCompletedScene(nextLevelButton));
                primaryStage.show();
            }
            else if(getLevelNumber() == (GameBoard.getTotalLevelNo())){
                nextLevelButton.setText("Finish");
                primaryStage.setScene(levelCompletedScene(nextLevelButton));
                primaryStage.show();
                nextLevelButton.setOnMouseClicked(event1 -> {
                    VBox vBox = new VBox(50);
                    vBox.setBackground(new Background(new BackgroundImage(new Image("Assets/Background.jpg"), BackgroundRepeat.NO_REPEAT,
                            BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT)));
                    Label label = new Label("The game is completed.");
                    label.setFont(Font.font("Arial", FontWeight.BOLD, 30));
                    label.setStyle("-fx-text-fill: white");
                    vBox.getChildren().add(label);
                    vBox.getChildren().add(closeButton);
                    closeButton.setPrefSize(120,50);
                    vBox.alignmentProperty().set(Pos.CENTER);
                    Scene scene = new Scene(vBox, 950, 780);
                    primaryStage.setScene(scene);
                    primaryStage.show();
                });

            }
        });

    }

    private void clickedOnNextLevel(GameBoard gameBoard, Stage primaryStage, Button nextLevelButton){

        nextLevelButton.setOnMouseClicked(event -> {
            gameBoard.getCheckButton().setDisable(true);
            gameBoard.getNextButton().setDisable(true);
            GameBoard.numberOfMoves = 0;
            Scene scene = gameBoard.makeScene();
            primaryStage.setScene(scene);
            primaryStage.show();
            setDraggable(true);
            setLevelCompleted(checkForSolution(gameBoard));
            drag(gameBoard);


        });

    }

    private void animate(GameBoard gameBoard){

        setWholePath(gameBoard);
        setPath(getPath());
        getPath().setOpacity(0);
        gameBoard.getPane().getChildren().add(getPath());
        gameBoard.getBall().toFront();
        PathTransition pathTransition = new PathTransition();
        pathTransition.setPath(getPath());
        pathTransition.setNode(gameBoard.getBall());
        pathTransition.setDuration(Duration.seconds(2));
        pathTransition.play();
        pathTransition.setOnFinished(event -> {
            gameBoard.getNextButton().setDisable(false);
            gameBoard.getCheckButton().setDisable(true);
        });

    }

    private Scene levelCompletedScene(Button button){

        VBox vBox = new VBox(20);

        vBox.setBackground(new Background(new BackgroundImage(new Image("Assets/Background.jpg"), BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT)));

        Label levelCompletedText = new Label("Level " + getLevelNumber() +" is completed in " + GameBoard.numberOfMoves + " moves!");
        levelCompletedText.setFont(Font.font("Arial", FontWeight.BOLD, 30));
        levelCompletedText.setStyle("-fx-text-fill: white");
        vBox.getChildren().add(levelCompletedText);
        button.setPrefSize(100, 50);
        vBox.getChildren().add(button);
        vBox.alignmentProperty().set(Pos.CENTER);

        Scene levelCompletedScene = new Scene(vBox, 950, 780);

        return levelCompletedScene;
    }

    private void drag(GameBoard gameBoard) {

        ImageView[] imageViews = gameBoard.getImageViews();
        Tile[][] tiles = gameBoard.getTiles();

        for (ImageView imageView : imageViews) {

            imageView.setOnMouseReleased(e -> {
                ImageView imageView1 = (ImageView) e.getTarget(); // gets the first node
                ImageView imageView2 = null;

                if (e.getPickResult().getIntersectedNode() instanceof ImageView) {
                    imageView2 = (ImageView) e.getPickResult().getIntersectedNode(); // gets the last node
                }
                if (imageView2 != null) {

                    int index1x = 0;
                    int index1y = 0;
                    int index2x = 0;
                    int index2y = 0;
                    for (int k = 0; k < 4; k++) {
                        for (int j = 0; j < 4; j++) {
                            if (tiles[k][j].getImage().equals(imageView1.getImage())) {
                                index1x = k;
                                index1y = j;
                            }
                            if (tiles[k][j].getImage().equals(imageView2.getImage())) {
                                index2x = k;
                                index2y = j;
                            }
                        }
                    }

                    if ((tiles[index2x][index2y] instanceof EmptyFree) && !(tiles[index1x][index1y] instanceof EmptyFree)) {
                        if (!((index1x == index2x) && (index1y == index2y))) {
                            if ((tiles[index1x][index1y] instanceof Movable)) {
                                if (isDraggable()) {
                                    if (Math.abs(imageView2.getX() - imageView1.getX()) <= 180 &&
                                            imageView2.getY() == imageView1.getY()) {
                                        imageView1.toFront();
                                        dragAnimation(imageView1, imageView2);
                                        swapImages(imageView1, imageView2);
                                        gameBoard.displayNumberOfMoves();
                                        swapTiles(gameBoard, index1x, index1y, index2x, index2y);
                                        Media media = new Media(new File("src/Assets/swap_tiles.mp3").toURI().toString());
                                        MediaPlayer mediaPlayer = new MediaPlayer(media);
                                        mediaPlayer.play();
                                        setLevelCompleted(checkForSolution(gameBoard));
                                    }
                                    if (Math.abs(imageView2.getY() - imageView1.getY()) <= 180 &&
                                            imageView2.getX() == imageView1.getX()) {
                                        imageView1.toFront();
                                        dragAnimation(imageView1, imageView2);
                                        swapImages(imageView1, imageView2);
                                        gameBoard.displayNumberOfMoves();
                                        swapTiles(gameBoard, index1x, index1y, index2x, index2y);
                                        Media media = new Media(new File("src/Assets/swap_tiles.mp3").toURI().toString());
                                        MediaPlayer mediaPlayer = new MediaPlayer(media);
                                        mediaPlayer.play();
                                        setLevelCompleted(checkForSolution(gameBoard));
                                    }
                                }
                            }
                        }
                    }
                }
            });
        }

    }

    private void swapImages(ImageView imageView1, ImageView imageView2){

        ImageView temp = new ImageView(imageView1.getImage());
        temp.setX(imageView1.getX());
        temp.setY(imageView1.getY());
        imageView1.setX(imageView2.getX());
        imageView1.setY(imageView2.getY());
        imageView2.setX(temp.getX());
        imageView2.setY(temp.getY());

        GameBoard.numberOfMoves++;
    }

    private void swapTiles(GameBoard gameBoard, int index1x, int index1y, int index2x, int index2y){

        Tile temp = gameBoard.getTiles()[index1x][index1y];
        gameBoard.getTiles()[index1x][index1y] = gameBoard.getTiles()[index2x][index2y];
        gameBoard.getTiles()[index2x][index2y] = temp;
    }

    private void dragAnimation(ImageView imageView1, ImageView imageView2){

        PathTransition pathTransition = new PathTransition();
        Line line = new Line(imageView1.getX() + 70, imageView1.getY() + 70,
                imageView2.getX() + 70 , imageView2.getY() + 70);
        pathTransition.setPath(line);
        pathTransition.setNode(imageView1);
        pathTransition.setDuration(Duration.seconds(0.50));
        pathTransition.play();
        pathTransition.setOnFinished(event -> {
            imageView1.toBack();
        });

    }

    private boolean checkForSolution(GameBoard gameBoard){

        pipesInOrder = new ArrayList<>();
        int rowIndexOfStart = 0;
        int columnIndexOfStart = 0;
        int curveCount = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (gameBoard.getTiles()[i][j] instanceof StartPipe) {
                    columnIndexOfStart = j;
                    rowIndexOfStart = i;
                }
                if ((gameBoard.getTiles()[i][j] instanceof CurvedPipeStatic) || (gameBoard.getTiles()[i][j] instanceof CurvedPipeMovable)) {
                    curveCount++;
                }
            }
        }
        int rowIndex = 0;
        int columnIndex = 0;
        String previousStatus = "";
        pipesInOrder.add(gameBoard.getTiles()[rowIndexOfStart][columnIndexOfStart]);
        if (gameBoard.getTiles()[rowIndexOfStart][columnIndexOfStart].getStatus().equalsIgnoreCase("Vertical")) {
            for (int i = rowIndexOfStart + 1; i <= 3; i++) {
                if ((gameBoard.getTiles()[i][columnIndexOfStart] instanceof LinearPipe) ||
                        (gameBoard.getTiles()[i][columnIndexOfStart] instanceof NormalPipeStatic)) {
                    pipesInOrder.add(gameBoard.getTiles()[i][columnIndexOfStart]);
                } else if ((gameBoard.getTiles()[i][columnIndexOfStart] instanceof CurvedPipeMovable) ||
                        (gameBoard.getTiles()[i][columnIndexOfStart] instanceof CurvedPipeStatic)) {
                    pipesInOrder.add(gameBoard.getTiles()[i][columnIndexOfStart]);
                    rowIndex = i;
                    columnIndex = columnIndexOfStart;
                    previousStatus = "Vertical";
                    break;
                } else if ((gameBoard.getTiles()[i][columnIndexOfStart] instanceof EndPipe)) {
                    pipesInOrder.add(gameBoard.getTiles()[i][columnIndexOfStart]);
                    gameBoard.getCheckButton().setDisable(false);
                    levelNumber++;
                    setDraggable(false);
                    return true;
                } else
                    break;
            }
        }
        if (gameBoard.getTiles()[rowIndexOfStart][columnIndexOfStart].getStatus().equalsIgnoreCase("Horizontal")) {
            for (int i = columnIndexOfStart - 1; i >= 0; i--) {
                if ((gameBoard.getTiles()[rowIndexOfStart][i] instanceof LinearPipe) ||
                        (gameBoard.getTiles()[rowIndexOfStart][i] instanceof NormalPipeStatic)) {
                    pipesInOrder.add(gameBoard.getTiles()[rowIndexOfStart][i]);
                } else if ((gameBoard.getTiles()[rowIndexOfStart][i] instanceof CurvedPipeMovable) ||
                        (gameBoard.getTiles()[rowIndexOfStart][i] instanceof CurvedPipeStatic)) {
                    pipesInOrder.add(gameBoard.getTiles()[rowIndexOfStart][i]);
                    rowIndex = rowIndexOfStart;
                    columnIndex = i;
                    previousStatus = "Horizontal";
                    break;
                } else if ((gameBoard.getTiles()[rowIndexOfStart][i] instanceof EndPipe)) {
                    pipesInOrder.add(gameBoard.getTiles()[rowIndexOfStart][i]);
                    gameBoard.getCheckButton().setDisable(false);
                    levelNumber++;
                    setDraggable(false);
                    return true;
                } else
                    break;
            }
        }

        for (int a = 1; a <= curveCount; a++) {
            if (directionFinder(gameBoard.getTiles()[rowIndex][columnIndex].getStatus(), previousStatus).equals("down")) {
                for (int i = rowIndex + 1; i <= 3; i++) {
                    if ((gameBoard.getTiles()[i][columnIndex] instanceof LinearPipe) ||
                            (gameBoard.getTiles()[i][columnIndex] instanceof NormalPipeStatic)) {
                        pipesInOrder.add(gameBoard.getTiles()[i][columnIndex]);
                    } else if ((gameBoard.getTiles()[i][columnIndex] instanceof CurvedPipeMovable) ||
                            (gameBoard.getTiles()[i][columnIndex] instanceof CurvedPipeStatic)) {
                        pipesInOrder.add(gameBoard.getTiles()[i][columnIndex]);
                        rowIndex = i;
                        previousStatus = "Vertical";
                        break;
                    } else if ((gameBoard.getTiles()[i][columnIndex] instanceof EndPipe)) {
                        pipesInOrder.add(gameBoard.getTiles()[i][columnIndex]);
                        gameBoard.getCheckButton().setDisable(false);
                        levelNumber++;
                        setDraggable(false);
                        return true;
                    } else
                        break;
                }
            }
            if (directionFinder(gameBoard.getTiles()[rowIndex][columnIndex].getStatus(), previousStatus).equals("up")) {
                for (int i = rowIndex - 1; i >= 0; i--) {
                    if ((gameBoard.getTiles()[i][columnIndex] instanceof LinearPipe) ||
                            (gameBoard.getTiles()[i][columnIndex] instanceof NormalPipeStatic)) {
                        pipesInOrder.add(gameBoard.getTiles()[i][columnIndex]);
                    } else if ((gameBoard.getTiles()[i][columnIndex] instanceof CurvedPipeMovable) ||
                            (gameBoard.getTiles()[i][columnIndex] instanceof CurvedPipeStatic)) {
                        pipesInOrder.add(gameBoard.getTiles()[i][columnIndex]);
                        rowIndex = i;
                        previousStatus = "Vertical";
                        break;
                    } else if ((gameBoard.getTiles()[i][columnIndex] instanceof EndPipe)) {
                        pipesInOrder.add(gameBoard.getTiles()[i][columnIndex]);
                        gameBoard.getCheckButton().setDisable(false);
                        levelNumber++;
                        setDraggable(false);
                        return true;
                    } else
                        break;
                }
            }
            if (directionFinder(gameBoard.getTiles()[rowIndex][columnIndex].getStatus(), previousStatus).equals("right")) {
                for (int i = columnIndex + 1; i <= 3; i++) {
                    if ((gameBoard.getTiles()[rowIndex][i] instanceof LinearPipe) ||
                            (gameBoard.getTiles()[rowIndex][i] instanceof NormalPipeStatic)) {
                        pipesInOrder.add(gameBoard.getTiles()[rowIndex][i]);
                    } else if ((gameBoard.getTiles()[rowIndex][i] instanceof CurvedPipeMovable) ||
                            (gameBoard.getTiles()[rowIndex][i] instanceof CurvedPipeStatic)) {
                        pipesInOrder.add(gameBoard.getTiles()[rowIndex][i]);
                        columnIndex = i;
                        previousStatus = "Horizontal";
                        break;
                    } else if ((gameBoard.getTiles()[rowIndex][i] instanceof EndPipe)) {
                        pipesInOrder.add(gameBoard.getTiles()[rowIndex][i]);
                        gameBoard.getCheckButton().setDisable(false);
                        levelNumber++;
                        setDraggable(false);
                        return true;
                    } else
                        break;
                }
            }
            if (directionFinder(gameBoard.getTiles()[rowIndex][columnIndex].getStatus(), previousStatus).equals("left")) {
                for (int i = columnIndex - 1; i >= 0; i--) {
                    if ((gameBoard.getTiles()[rowIndex][i] instanceof LinearPipe) ||
                            (gameBoard.getTiles()[rowIndex][i] instanceof NormalPipeStatic)) {
                        pipesInOrder.add(gameBoard.getTiles()[rowIndex][i]);
                    } else if ((gameBoard.getTiles()[rowIndex][i] instanceof CurvedPipeMovable) ||
                            (gameBoard.getTiles()[rowIndex][i] instanceof CurvedPipeStatic)) {
                        pipesInOrder.add(gameBoard.getTiles()[rowIndex][i]);
                        columnIndex = i;
                        previousStatus = "Horizontal";
                        break;
                    } else if ((gameBoard.getTiles()[rowIndex][i] instanceof EndPipe)) {
                        pipesInOrder.add(gameBoard.getTiles()[rowIndex][i]);
                        gameBoard.getCheckButton().setDisable(false);
                        levelNumber++;
                        setDraggable(false);
                        return true;
                    } else
                        break;
                }
            }
        }

        return false;
    }

    private String directionFinder(String statusCurve, String statusPrevious ){

        if(statusPrevious.equalsIgnoreCase("Horizontal") && statusCurve.equals("00"))
            return "up";
        else if(statusPrevious.equalsIgnoreCase("Horizontal") && statusCurve.equals("01"))
            return "up";
        else if(statusPrevious.equalsIgnoreCase("Horizontal") && statusCurve.equals("10"))
            return "down";
        else if(statusPrevious.equalsIgnoreCase("Horizontal") && statusCurve.equals("11"))
            return "down";
        else if(statusPrevious.equalsIgnoreCase("Vertical") && statusCurve.equals("00"))
            return "left";
        else if(statusPrevious.equalsIgnoreCase("Vertical") && statusCurve.equals("01"))
            return "right";
        else if(statusPrevious.equalsIgnoreCase("Vertical") && statusCurve.equals("10"))
            return "left";
        else
            return "right";
    }

    private void setWholePath(GameBoard gameBoard){

        Path path = new Path();
        for (int i = 0; i < getPipesInOrder().size(); i++){

            if (getPipesInOrder().get(i) instanceof StartPipe) {
                int indexOfImageView = indexFinder(gameBoard, getPipesInOrder().get(i));

                if (getPipesInOrder().get(i).getStatus().equalsIgnoreCase("Vertical")) {
                    MoveTo moveTo = new MoveTo(gameBoard.getImageViews()[indexOfImageView].getX() + 70, gameBoard.getImageViews()[indexOfImageView].getY() + 50.5);
                    LineTo lineTo = new LineTo(gameBoard.getImageViews()[indexOfImageView].getX() + 70, gameBoard.getImageViews()[indexOfImageView].getY() + 141.5);
                    path.getElements().addAll(moveTo, lineTo);
                }
                if (getPipesInOrder().get(i).getStatus().equalsIgnoreCase("Horizontal")) {
                    MoveTo moveTo = new MoveTo(gameBoard.getImageViews()[indexOfImageView].getX() + 90, gameBoard.getImageViews()[indexOfImageView].getY() + 70);
                    LineTo lineTo = new LineTo(gameBoard.getImageViews()[indexOfImageView].getX(), gameBoard.getImageViews()[indexOfImageView].getY() + 70);
                    path.getElements().addAll(moveTo, lineTo);
                }
                path.setStroke(Color.WHITE);

            }


            if (getPipesInOrder().get(i) instanceof EndPipe) {
                int indexOfImageView = indexFinder(gameBoard, getPipesInOrder().get(i));

                if (getPipesInOrder().get(i).getStatus().equalsIgnoreCase("Vertical")) {
                    LineTo lineTo = new LineTo(gameBoard.getImageViews()[indexOfImageView].getX() + 70, gameBoard.getImageViews()[indexOfImageView].getY() + 50.5);
                    MoveTo moveTo = new MoveTo(gameBoard.getImageViews()[indexOfImageView].getX() + 70, gameBoard.getImageViews()[indexOfImageView].getY() + 141.5);
                    path.getElements().addAll(moveTo, lineTo);
                }
                if (getPipesInOrder().get(i).getStatus().equalsIgnoreCase("Horizontal")) {
                    LineTo lineTo = new LineTo(gameBoard.getImageViews()[indexOfImageView].getX() + 90, gameBoard.getImageViews()[indexOfImageView].getY() + 70);
                    MoveTo moveTo = new MoveTo(gameBoard.getImageViews()[indexOfImageView].getX(), gameBoard.getImageViews()[indexOfImageView].getY() + 70);
                    path.getElements().addAll(moveTo, lineTo);
                }
                path.setStroke(Color.WHITE);
            }




            if (getPipesInOrder().get(i) instanceof LinearPipe) {
                int indexOfImageView = indexFinder(gameBoard, getPipesInOrder().get(i));
                int indexOfImageViewOfPreviousPipe = indexFinder(gameBoard, getPipesInOrder().get(i-1));

                if (getPipesInOrder().get(i).getStatus().equalsIgnoreCase("Vertical")) {
                    MoveTo moveTo = new MoveTo(gameBoard.getImageViews()[indexOfImageView].getX() + 70, gameBoard.getImageViews()[indexOfImageView].getY());
                    LineTo lineTo = new LineTo(gameBoard.getImageViews()[indexOfImageView].getX() + 70, gameBoard.getImageViews()[indexOfImageView].getY() + 140);
                    if(gameBoard.getImageViews()[indexOfImageView].getY() - gameBoard.getImageViews()[indexOfImageViewOfPreviousPipe].getY() < 0)
                        reverseDirection(moveTo, lineTo);
                    path.getElements().addAll(moveTo, lineTo);
                }
                if (getPipesInOrder().get(i).getStatus().equalsIgnoreCase("Horizontal")) {
                    MoveTo moveTo = new MoveTo(gameBoard.getImageViews()[indexOfImageView].getX(), gameBoard.getImageViews()[indexOfImageView].getY() + 70);
                    LineTo lineTo = new LineTo(gameBoard.getImageViews()[indexOfImageView].getX() + 140, gameBoard.getImageViews()[indexOfImageView].getY() + 70);
                    if(gameBoard.getImageViews()[indexOfImageView].getX() - gameBoard.getImageViews()[indexOfImageViewOfPreviousPipe].getX() < 0)
                        reverseDirection(moveTo, lineTo);
                    path.getElements().addAll(moveTo, lineTo);
                }
                path.setStroke(Color.WHITE);
            }



            if (getPipesInOrder().get(i) instanceof NormalPipeStatic) {
                int indexOfImageView = indexFinder(gameBoard, getPipesInOrder().get(i));
                int indexOfImageViewOfPreviousPipe = indexFinder(gameBoard, getPipesInOrder().get(i-1));

                if (getPipesInOrder().get(i).getStatus().equalsIgnoreCase("Vertical")) {
                    MoveTo moveTo = new MoveTo(gameBoard.getImageViews()[indexOfImageView].getX() + 70, gameBoard.getImageViews()[indexOfImageView].getY());
                    LineTo lineTo = new LineTo(gameBoard.getImageViews()[indexOfImageView].getX() + 70, gameBoard.getImageViews()[indexOfImageView].getY() + 140);
                    if(gameBoard.getImageViews()[indexOfImageView].getY() - gameBoard.getImageViews()[indexOfImageViewOfPreviousPipe].getY() < 0)
                        reverseDirection(moveTo, lineTo);
                    path.getElements().addAll(moveTo, lineTo);
                }
                if (getPipesInOrder().get(i).getStatus().equalsIgnoreCase("Horizontal")) {
                    MoveTo moveTo = new MoveTo(gameBoard.getImageViews()[indexOfImageView].getX(), gameBoard.getImageViews()[indexOfImageView].getY()+70);
                    LineTo lineTo = new LineTo(gameBoard.getImageViews()[indexOfImageView].getX() + 140, gameBoard.getImageViews()[indexOfImageView].getY() + 70);
                    if(gameBoard.getImageViews()[indexOfImageView].getX() - gameBoard.getImageViews()[indexOfImageViewOfPreviousPipe].getX() < 0)
                        reverseDirection(moveTo, lineTo);
                    path.getElements().addAll(moveTo, lineTo);
                }
                path.setStroke(Color.WHITE);
            }



            if (getPipesInOrder().get(i) instanceof CurvedPipeMovable) {
                int indexOfImageView = indexFinder(gameBoard, getPipesInOrder().get(i));
                int indexOfImageViewOfPreviousPipe = indexFinder(gameBoard, getPipesInOrder().get(i-1));

                if (getPipesInOrder().get(i).getStatus().equals("00")){
                    MoveTo moveTo = new MoveTo(gameBoard.getImageViews()[indexOfImageView].getX() , gameBoard.getImageViews()[indexOfImageView].getY() +70 );
                    ArcTo arcTo = new ArcTo();
                    arcTo.setX(gameBoard.getImageViews()[indexOfImageView].getX() + 70);
                    arcTo.setY(gameBoard.getImageViews()[indexOfImageView].getY());
                    arcTo.setRadiusX(70);
                    arcTo.setRadiusY(70);
                    if(gameBoard.getImageViews()[indexOfImageView].getY() - gameBoard.getImageViews()[indexOfImageViewOfPreviousPipe].getY() > 0)
                        reverseDirection(moveTo, arcTo);
                    path.getElements().addAll(moveTo, arcTo);
                }

                if (getPipesInOrder().get(i).getStatus().equals("01")){
                    MoveTo moveTo = new MoveTo(gameBoard.getImageViews()[indexOfImageView].getX() + 70 , gameBoard.getImageViews()[indexOfImageView].getY());
                    ArcTo arcTo = new ArcTo();
                    arcTo.setX(gameBoard.getImageViews()[indexOfImageView].getX() + 140);
                    arcTo.setY(gameBoard.getImageViews()[indexOfImageView].getY() + 70);
                    arcTo.setRadiusX(70);
                    arcTo.setRadiusY(70);
                    if(gameBoard.getImageViews()[indexOfImageView].getX() - gameBoard.getImageViews()[indexOfImageViewOfPreviousPipe].getX() < 0)
                        reverseDirection(moveTo, arcTo);
                    path.getElements().addAll(moveTo, arcTo);
                }


                if (getPipesInOrder().get(i).getStatus().equals("10")){
                    MoveTo moveTo = new MoveTo(gameBoard.getImageViews()[indexOfImageView].getX() + 70, gameBoard.getImageViews()[indexOfImageView].getY() + 140);
                    ArcTo arcTo = new ArcTo();
                    arcTo.setX(gameBoard.getImageViews()[indexOfImageView].getX());
                    arcTo.setY(gameBoard.getImageViews()[indexOfImageView].getY() + 70);
                    arcTo.setRadiusX(70);
                    arcTo.setRadiusY(70);
                    if(gameBoard.getImageViews()[indexOfImageView].getX() - gameBoard.getImageViews()[indexOfImageViewOfPreviousPipe].getX() > 0)
                        reverseDirection(moveTo, arcTo);
                    path.getElements().addAll(moveTo, arcTo);
                }


                if (getPipesInOrder().get(i).getStatus().equals("11")){
                    MoveTo moveTo = new MoveTo(gameBoard.getImageViews()[indexOfImageView].getX() + 140 , gameBoard.getImageViews()[indexOfImageView].getY() + 70);
                    ArcTo arcTo = new ArcTo();
                    arcTo.setX(gameBoard.getImageViews()[indexOfImageView].getX() + 70);
                    arcTo.setY(gameBoard.getImageViews()[indexOfImageView].getY() + 140);
                    arcTo.setRadiusX(70);
                    arcTo.setRadiusY(70);
                    if(gameBoard.getImageViews()[indexOfImageView].getY() - gameBoard.getImageViews()[indexOfImageViewOfPreviousPipe].getY() < 0)
                        reverseDirection(moveTo, arcTo);
                    path.getElements().addAll(moveTo, arcTo);
                }
                path.setStroke(Color.WHITE);
            }


            if (getPipesInOrder().get(i) instanceof CurvedPipeStatic) {
                int indexOfImageView = indexFinder(gameBoard, getPipesInOrder().get(i));
                int indexOfImageViewOfPreviousPipe = indexFinder(gameBoard, getPipesInOrder().get(i-1));

                if (getPipesInOrder().get(i).getStatus().equals("00")){
                    MoveTo moveTo = new MoveTo(gameBoard.getImageViews()[indexOfImageView].getX() , gameBoard.getImageViews()[indexOfImageView].getY() +70 );
                    ArcTo arcTo = new ArcTo();
                    arcTo.setX(gameBoard.getImageViews()[indexOfImageView].getX() + 70);
                    arcTo.setY(gameBoard.getImageViews()[indexOfImageView].getY());
                    arcTo.setRadiusX(70);
                    arcTo.setRadiusY(70);
                    if(gameBoard.getImageViews()[indexOfImageView].getY() - gameBoard.getImageViews()[indexOfImageViewOfPreviousPipe].getY() > 0)
                        reverseDirection(moveTo, arcTo);
                    path.getElements().addAll(moveTo, arcTo);
                }

                if (getPipesInOrder().get(i).getStatus().equals("01")){
                    MoveTo moveTo = new MoveTo(gameBoard.getImageViews()[indexOfImageView].getX() + 70 , gameBoard.getImageViews()[indexOfImageView].getY());
                    ArcTo arcTo = new ArcTo();
                    arcTo.setX(gameBoard.getImageViews()[indexOfImageView].getX() + 140);
                    arcTo.setY(gameBoard.getImageViews()[indexOfImageView].getY() + 70);
                    arcTo.setRadiusX(70);
                    arcTo.setRadiusY(70);
                    if(gameBoard.getImageViews()[indexOfImageView].getX() - gameBoard.getImageViews()[indexOfImageViewOfPreviousPipe].getX() < 0)
                        reverseDirection(moveTo, arcTo);
                    path.getElements().addAll(moveTo, arcTo);
                }

                if (getPipesInOrder().get(i).getStatus().equals("10")){
                    MoveTo moveTo = new MoveTo(gameBoard.getImageViews()[indexOfImageView].getX() + 70, gameBoard.getImageViews()[indexOfImageView].getY() + 140);
                    ArcTo arcTo = new ArcTo();
                    arcTo.setX(gameBoard.getImageViews()[indexOfImageView].getX());
                    arcTo.setY(gameBoard.getImageViews()[indexOfImageView].getY() + 70);
                    arcTo.setRadiusX(70);
                    arcTo.setRadiusY(70);
                    if(gameBoard.getImageViews()[indexOfImageView].getX() - gameBoard.getImageViews()[indexOfImageViewOfPreviousPipe].getX() > 0)
                        reverseDirection(moveTo, arcTo);
                    path.getElements().addAll(moveTo, arcTo);
                }


                if (getPipesInOrder().get(i).getStatus().equals("11")){
                    MoveTo moveTo = new MoveTo(gameBoard.getImageViews()[indexOfImageView].getX() + 140 , gameBoard.getImageViews()[indexOfImageView].getY() + 70);
                    ArcTo arcTo = new ArcTo();
                    arcTo.setX(gameBoard.getImageViews()[indexOfImageView].getX() + 70);
                    arcTo.setY(gameBoard.getImageViews()[indexOfImageView].getY() + 140);
                    arcTo.setRadiusX(70);
                    arcTo.setRadiusY(70);
                    if(gameBoard.getImageViews()[indexOfImageView].getY() - gameBoard.getImageViews()[indexOfImageViewOfPreviousPipe].getY() < 0)
                        reverseDirection(moveTo, arcTo);
                    path.getElements().addAll(moveTo, arcTo);
                }
                path.setStroke(Color.WHITE);
            }


        }

        setPath(path);
    }

    private void reverseDirection(MoveTo moveTo, LineTo lineTo){

        double tempX = moveTo.getX();
        double tempY = moveTo.getY();
        moveTo.setX(lineTo.getX());
        moveTo.setY(lineTo.getY());
        lineTo.setX(tempX);
        lineTo.setY(tempY);

    }
    // overloading reverseDirection method
    private void reverseDirection(MoveTo moveTo, ArcTo arcTo){

        double tempX = moveTo.getX();
        double tempY = moveTo.getY();
        moveTo.setX(arcTo.getX());
        moveTo.setY(arcTo.getY());
        arcTo.setX(tempX);
        arcTo.setY(tempY);
        arcTo.setLargeArcFlag(false);
        arcTo.setSweepFlag(true);

    }

    private int indexFinder(GameBoard gameBoard, Tile tile){

        int indexOfRow = 0;
        int indexOfColumn = 0;
        for (int k = 0; k < 4; k++){
            for (int l = 0; l < 4; l++){
                if (gameBoard.getTiles()[k][l].equals(tile)) {
                    indexOfRow = k;
                    indexOfColumn = l;
                }
            }
        }
        int imageViewIndex = 0;
        for (int m = 0; m < 16; m++) {
            if (gameBoard.getTiles()[indexOfRow][indexOfColumn].getImage().equals(gameBoard.getImageViews()[m].getImage())) {
                imageViewIndex = m;
            }
        }
        return  imageViewIndex;
    }


    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
    }

    public boolean isLevelCompleted() {
        return levelCompleted;
    }

    public void setLevelCompleted(boolean levelCompleted){
        this.levelCompleted = levelCompleted;
    }

    public ArrayList<Tile> getPipesInOrder() {
        return pipesInOrder;
    }

    public void setPipesInOrder(ArrayList<Tile> pipesInOrder) {
        this.pipesInOrder = pipesInOrder;
    }

    public static int getLevelNumber() {
        return levelNumber;
    }

    public static void setLevelNumber(int levelNumber) {
        Main.levelNumber = levelNumber;
    }

    @Override
    public void run() {
        drag(gameBoard);
    }
}