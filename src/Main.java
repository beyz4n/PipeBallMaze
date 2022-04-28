import Tiles.*;
import javafx.animation.PathTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
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


public class Main extends Application {
    private static String MEDIA_URL;
    private boolean isClicked;
    private static int numberOfMoves;
    private Path path;
    private boolean levelCompleted;
    private ArrayList<Tile> pipesInOrder;
    private static int levelNumber;
    public static int clickCount;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setResizable(false);
        Label gameName = new Label("Pipe Ball Maze");
        gameName.setFont(Font.font("Arial", FontWeight.BOLD, 50));
        gameName.setStyle("-fx-text-fill: white");
        Button startButton = new Button("Play the Game!");
        startButton.setPrefSize(120,50);
        VBox startPane = new VBox(50);
        startPane.getChildren().add(gameName);
        startPane.getChildren().add(startButton);
        startPane.alignmentProperty().set(Pos.CENTER);
        startPane.setBackground(new Background(new BackgroundImage(new Image("Background.jpg"), BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT)));
        Scene startScene = new Scene(startPane,950,780);
        primaryStage.setScene(startScene);
        primaryStage.setTitle("PipeBallMaze");
        //MEDIA_URL = "file:///Users/senaektiricioglu/Desktop/Flashback.mp3";
        Media media = new Media(new File("Flashback.mp3").toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
        primaryStage.show();


        GameBoard gameBoard = new GameBoard();
        Button nextButton = new Button("Next Level ->");

        startButton.setOnMouseClicked(event -> {
            numberOfMoves = 0;
            primaryStage.setScene(gameBoard.makeScene(gameBoard.getLevels(), clickCount));
            primaryStage.show();
            setLevelCompleted(checkForSolution(gameBoard));
            drag(gameBoard);
            if(isLevelCompleted()) {
                clickCount++;
            }
        });


        gameBoard.getCheckButton().setOnMouseClicked(event -> {
            animate(gameBoard);
            gameBoard.getNextButton().setDisable(false);
        });

        gameBoard.getNextButton().setOnMouseClicked(event -> {
            if(getLevelNumber() < (GameBoard.getTotalLevelNo())) {
                primaryStage.setScene(levelCompletedScene(nextButton));
                primaryStage.show();
            }
            else if(getLevelNumber() == (GameBoard.getTotalLevelNo())){
                nextButton.setText("Finish");
                primaryStage.setScene(levelCompletedScene(nextButton));
                primaryStage.show();
                nextButton.setOnMouseClicked(event1 -> {
                    StackPane stackPane = new StackPane();
                    stackPane.setBackground(new Background(new BackgroundImage(new Image("Background.jpg"), BackgroundRepeat.NO_REPEAT,
                            BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT)));
                    Label label = new Label("The game is completed.");
                    label.setFont(Font.font("Arial", FontWeight.BOLD, 30));;
                    label.setStyle("-fx-text-fill: white");
                    stackPane.getChildren().add(label);
                    Scene scene = new Scene(stackPane, 950, 780);
                    primaryStage.setScene(scene);
                    primaryStage.show();});

            }
        });


        nextButton.setOnMouseClicked(event -> {
            gameBoard.getCheckButton().setDisable(true);
            gameBoard.getNextButton().setDisable(true);
            numberOfMoves = 0;
            Scene scene = gameBoard.makeScene(gameBoard.getLevels(), clickCount);
            primaryStage.setScene(scene);
            primaryStage.show();
            setLevelCompleted(checkForSolution(gameBoard));
            drag(gameBoard);
            if (isLevelCompleted()) {
                clickCount++;
            }
        });
    }

    public void animate(GameBoard gameBoard){
        setWholePath(gameBoard);
        setPath(getPath());
        gameBoard.getPane().getChildren().add(getPath());
        gameBoard.getBall().toFront();
        PathTransition pathTransition = new PathTransition();
        pathTransition.setPath(getPath());
        pathTransition.setNode(gameBoard.getBall());
        pathTransition.setDuration(Duration.seconds(2));
        pathTransition.play();
    }


    public Scene levelCompletedScene(Button button){
        VBox vBox = new VBox(20);

        vBox.setBackground(new Background(new BackgroundImage(new Image("Background.jpg"), BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT)));

        Label levelCompletedText = new Label("Level " + getLevelNumber() +" is completed in " + getNumberOfMoves() + " moves!");
        levelCompletedText.setFont(Font.font("Arial", FontWeight.BOLD, 30));
        levelCompletedText.setStyle("-fx-text-fill: white");
        vBox.getChildren().add(levelCompletedText);
        button.setPrefSize(100, 50);
        vBox.getChildren().add(button);
        vBox.alignmentProperty().set(Pos.CENTER);

        Scene levelCompletedScene = new Scene(vBox, 950, 780);
        //MEDIA_URL = "file:///Users/senaektiricioglu/Desktop/Kids%20Cheering.mp3";
        Media media = new Media(new File("Kids_Cheering.mp3").toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
        return levelCompletedScene;
        /*
        Pane pane = new Pane();
        pane.getChildren().add(new Label("level completed. press next!"));
        pane.getChildren().add(button);
        Scene scene = new Scene(pane, 950,780);
        return scene;
         */
    }


    private void drag(GameBoard gameBoard) {

        ImageView[] imageViews = gameBoard.getImageViews();
        Tile[][] tiles = gameBoard.getTiles();

        for (ImageView imageView : imageViews) {

            imageView.setOnMouseReleased(e -> {
                ImageView imageView1 = (ImageView) e.getTarget(); // gets the first node
                ImageView imageView2 = null;

                if(e.getPickResult().getIntersectedNode() instanceof ImageView) {
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
                    imageView1.toFront();
                    if (tiles[index2x][index2y] instanceof EmptyFree) {
                        if (!((index1x == index2x) && (index1y == index2y))) {
                            if (tiles[index1x][index1y] instanceof EmptyFree) {
                                if ((tiles[index1x][index1y] instanceof Movable) && (tiles[index2x][index2y] instanceof Movable)
                                        && !(tiles[index1x][index1y] instanceof EmptyFree)) {
                                    if (Math.abs(imageView2.getX() - imageView1.getX()) <= 180 &&
                                            imageView2.getY() == imageView1.getY()) {
                                        dragAnimation(imageView1,imageView2);
                                        swapImages(imageView1, imageView2);
                                        gameBoard.displayNumberOfMoves();
                                        swapTiles(gameBoard, index1x, index1y, index2x, index2y);

                                    }
                                    if (Math.abs(imageView2.getY() - imageView1.getY()) <= 180 &&
                                            imageView2.getX() == imageView1.getX()) {
                                        dragAnimation(imageView1,imageView2);
                                        swapImages(imageView1, imageView2);
                                        gameBoard.displayNumberOfMoves();
                                        swapTiles(gameBoard, index1x, index1y, index2x, index2y);

                                    }
                                }
                            } else {
                                if ((tiles[index1x][index1y] instanceof Movable) && (tiles[index2x][index2y] instanceof Movable)
                                        && !(tiles[index1x][index1y] instanceof EmptyFree)) {
                                    if (Math.abs(imageView2.getX() - imageView1.getX()) <= 180 &&
                                            imageView2.getY() == imageView1.getY()) {
                                        dragAnimation(imageView1,imageView2);
                                        swapImages(imageView1, imageView2);
                                        gameBoard.displayNumberOfMoves();
                                        swapTiles(gameBoard, index1x, index1y, index2x, index2y);

                                    }
                                    if (Math.abs(imageView2.getY() - imageView1.getY()) <= 180 &&
                                            imageView2.getX() == imageView1.getX()) {
                                        dragAnimation(imageView1,imageView2);
                                        swapImages(imageView1, imageView2);
                                        gameBoard.displayNumberOfMoves();
                                        swapTiles(gameBoard, index1x, index1y, index2x, index2y);
                                    }
                                }
                            }
                        }
                      //  MEDIA_URL = "file:///Users/senaektiricioglu/Desktop/Bell_Transition.mp3";
                        Media media = new Media(new File("Bell_Transition.mp3").toURI().toString());
                        MediaPlayer mediaPlayer = new MediaPlayer(media);
                        mediaPlayer.play();
                        setLevelCompleted(checkForSolution(gameBoard));
                    }
                }
            });
        }
    }

    public void swapImages(ImageView imageView1, ImageView imageView2){

        ImageView temp = new ImageView(imageView1.getImage());
        temp.setX(imageView1.getX());
        temp.setY(imageView1.getY());
        imageView1.setX(imageView2.getX());
        imageView1.setY(imageView2.getY());
        imageView2.setX(temp.getX());
        imageView2.setY(temp.getY());

        setNumberOfMoves(getNumberOfMoves() + 1);
    }

    public void swapTiles(GameBoard gameBoard, int index1x, int index1y, int index2x, int index2y){
        Tile temp = gameBoard.getTiles()[index1x][index1y];
        gameBoard.getTiles()[index1x][index1y] = gameBoard.getTiles()[index2x][index2y];
        gameBoard.getTiles()[index2x][index2y] = temp;
    }

    public void dragAnimation(ImageView imageView1, ImageView imageView2){
        PathTransition pathTransition = new PathTransition();
        Line line = new Line(imageView1.getX() + 70, imageView1.getY() + 70,
                imageView2.getX() + 70 , imageView2.getY() + 70);
        pathTransition.setPath(line);
        pathTransition.setNode(imageView1);
        pathTransition.setDuration(Duration.seconds(0.75));
        pathTransition.play();
        pathTransition.setOnFinished(event -> {
            imageView1.toBack();
        });
    }

    public boolean checkForSolution(GameBoard gameBoard){

        pipesInOrder = new ArrayList<>();
        int rowIndexOfStart = 0;
        int columnIndexOfStart = 0;
        int curveCount = 0;
        for (int i = 0 ; i < 4 ; i++){
            for (int j = 0 ; j < 4 ; j++){
                if (gameBoard.getTiles()[i][j] instanceof StartPipe){
                     columnIndexOfStart = j;
                     rowIndexOfStart = i;
                }
                if((gameBoard.getTiles()[i][j] instanceof CurvedPipeStatic) || (gameBoard.getTiles()[i][j] instanceof CurvedPipeMovable)){
                    curveCount++;
                }
            }
        }
        int rowIndex = 0;
        int columnIndex = 0;
        String previousStatus = "";
        pipesInOrder.add(gameBoard.getTiles()[rowIndexOfStart][columnIndexOfStart]);
        if (gameBoard.getTiles()[rowIndexOfStart][columnIndexOfStart].getStatus().equals("Vertical")) {
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
                    System.out.println("true");
                    gameBoard.getCheckButton().setDisable(false);
                    levelNumber++;
                    return true;
                    //break;
                } else
                    break;
            }
        }
            if (gameBoard.getTiles()[rowIndexOfStart][columnIndexOfStart].getStatus().equals("Horizontal")) {
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
                    }
                    else if ((gameBoard.getTiles()[rowIndexOfStart][i] instanceof EndPipe)){
                        pipesInOrder.add(gameBoard.getTiles()[rowIndexOfStart][i]);
                        System.out.println("true");
                        gameBoard.getCheckButton().setDisable(false);
                        levelNumber++;
                        return true;
                        //break;
                    } else
                        break;
                }
            }

            for(int  a = 1 ; a <= curveCount; a++) {
                if(directionFinder(gameBoard.getTiles()[rowIndex][columnIndex].getStatus(), previousStatus).equals("down")){
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
                        }
                        else if ((gameBoard.getTiles()[i][columnIndex] instanceof EndPipe)){
                            pipesInOrder.add(gameBoard.getTiles()[i][columnIndex]);
                            System.out.println("true");
                            gameBoard.getCheckButton().setDisable(false);
                            levelNumber++;
                            return true;
                            //break;
                        } else
                            break;
                    }
                }
                if(directionFinder(gameBoard.getTiles()[rowIndex][columnIndex].getStatus(), previousStatus).equals("up")){
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
                        }
                        else if ((gameBoard.getTiles()[i][columnIndex] instanceof EndPipe)){
                            pipesInOrder.add(gameBoard.getTiles()[i][columnIndex]);
                            System.out.println("true");
                            gameBoard.getCheckButton().setDisable(false);
                            levelNumber++;
                            return true;
                            //break;
                        } else
                            break;
                    }
                }
                if(directionFinder(gameBoard.getTiles()[rowIndex][columnIndex].getStatus(), previousStatus).equals("right")){
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
                        }
                        else if ((gameBoard.getTiles()[rowIndex][i] instanceof EndPipe)){
                            pipesInOrder.add(gameBoard.getTiles()[rowIndex][i]);
                            System.out.println("true");
                            gameBoard.getCheckButton().setDisable(false);
                            levelNumber++;
                            return true;
                            //break;
                        } else
                            break;
                    }
                }
                if(directionFinder(gameBoard.getTiles()[rowIndex][columnIndex].getStatus(), previousStatus).equals("left")){
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
                        }
                        else if ((gameBoard.getTiles()[rowIndex][i] instanceof EndPipe)){
                            pipesInOrder.add(gameBoard.getTiles()[rowIndex][i]);
                            System.out.println("true");
                            gameBoard.getCheckButton().setDisable(false);
                            levelNumber++;
                            return true;
                            //break;
                        } else
                            break;
                    }
                    }
                }



        return false;




        /*
        if (checkBooleanList.contains(false)){
            System.out.println("false");
            return false;
        }
        else {
            System.out.println("true");
            gameBoard.getCheckButton().setDisable(false);
            levelNumber++;
            return true;
        }
         */
    }

    public String directionFinder(String statusCurve, String statusPrevious ){

        if(statusPrevious.equals("Horizontal") && statusCurve.equals("00"))
            return "up";
        else if(statusPrevious.equals("Horizontal") && statusCurve.equals("01"))
            return "up";
        else if(statusPrevious.equals("Horizontal") && statusCurve.equals("10"))
            return "down";
        else if(statusPrevious.equals("Horizontal") && statusCurve.equals("11"))
            return "down";
        else if(statusPrevious.equals("Vertical") && statusCurve.equals("00"))
            return "left";
        else if(statusPrevious.equals("Vertical") && statusCurve.equals("01"))
            return "right";
        else if(statusPrevious.equals("Vertical") && statusCurve.equals("10"))
            return "left";
        else
            return "right";
    }


    public void setWholePath(GameBoard gameBoard){
        Path path = new Path();
        for (int i = 0; i < getPipesInOrder().size(); i++){

            if (getPipesInOrder().get(i) instanceof StartPipe) {
                int indexOfImageView = indexFinder(gameBoard, getPipesInOrder().get(i));

                if (getPipesInOrder().get(i).getStatus().equals("Vertical")) {
                    MoveTo moveTo = new MoveTo(gameBoard.getImageViews()[indexOfImageView].getX() + 70, gameBoard.getImageViews()[indexOfImageView].getY() + 50.5);
                    LineTo lineTo = new LineTo(gameBoard.getImageViews()[indexOfImageView].getX() + 70, gameBoard.getImageViews()[indexOfImageView].getY() + 141.5);
                    path.getElements().addAll(moveTo, lineTo);
                }
                if (getPipesInOrder().get(i).getStatus().equals("Horizontal")) {
                    MoveTo moveTo = new MoveTo(gameBoard.getImageViews()[indexOfImageView].getX() + 90, gameBoard.getImageViews()[indexOfImageView].getY() + 70);
                    LineTo lineTo = new LineTo(gameBoard.getImageViews()[indexOfImageView].getX(), gameBoard.getImageViews()[indexOfImageView].getY() + 70);
                    path.getElements().addAll(moveTo, lineTo);
                }
                path.setStroke(Color.WHITE);

            }


            if (getPipesInOrder().get(i) instanceof EndPipe) {
                int indexOfImageView = indexFinder(gameBoard, getPipesInOrder().get(i));

                if (getPipesInOrder().get(i).getStatus().equals("Vertical")) {
                    LineTo lineTo = new LineTo(gameBoard.getImageViews()[indexOfImageView].getX() + 70, gameBoard.getImageViews()[indexOfImageView].getY() + 50.5);
                    MoveTo moveTo = new MoveTo(gameBoard.getImageViews()[indexOfImageView].getX() + 70, gameBoard.getImageViews()[indexOfImageView].getY() + 141.5);
                    path.getElements().addAll(moveTo, lineTo);
                }
                if (getPipesInOrder().get(i).getStatus().equals("Horizontal")) {
                    LineTo lineTo = new LineTo(gameBoard.getImageViews()[indexOfImageView].getX() + 90, gameBoard.getImageViews()[indexOfImageView].getY() + 70);
                    MoveTo moveTo = new MoveTo(gameBoard.getImageViews()[indexOfImageView].getX(), gameBoard.getImageViews()[indexOfImageView].getY() + 70);
                    path.getElements().addAll(moveTo, lineTo);
                }
                path.setStroke(Color.WHITE);
            }




            if (getPipesInOrder().get(i) instanceof LinearPipe) {
                int indexOfImageView = indexFinder(gameBoard, getPipesInOrder().get(i));
                int indexOfImageViewOfPreviousPipe = indexFinder(gameBoard, getPipesInOrder().get(i-1));

                if (getPipesInOrder().get(i).getStatus().equals("Vertical")) {
                    MoveTo moveTo = new MoveTo(gameBoard.getImageViews()[indexOfImageView].getX() + 70, gameBoard.getImageViews()[indexOfImageView].getY());
                    LineTo lineTo = new LineTo(gameBoard.getImageViews()[indexOfImageView].getX() + 70, gameBoard.getImageViews()[indexOfImageView].getY() + 140);
                    if(gameBoard.getImageViews()[indexOfImageView].getY() - gameBoard.getImageViews()[indexOfImageViewOfPreviousPipe].getY() < 0)
                        reverseDirection(moveTo, lineTo);
                    path.getElements().addAll(moveTo, lineTo);
                }
                if (getPipesInOrder().get(i).getStatus().equals("Horizontal")) {
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

                if (getPipesInOrder().get(i).getStatus().equals("Vertical")) {
                    MoveTo moveTo = new MoveTo(gameBoard.getImageViews()[indexOfImageView].getX() + 70, gameBoard.getImageViews()[indexOfImageView].getY());
                    LineTo lineTo = new LineTo(gameBoard.getImageViews()[indexOfImageView].getX() + 70, gameBoard.getImageViews()[indexOfImageView].getY() + 140);
                    if(gameBoard.getImageViews()[indexOfImageView].getY() - gameBoard.getImageViews()[indexOfImageViewOfPreviousPipe].getY() < 0)
                        reverseDirection(moveTo, lineTo);
                    path.getElements().addAll(moveTo, lineTo);
                }
                if (getPipesInOrder().get(i).getStatus().equals("Horizontal")) {
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

    public void reverseDirection(MoveTo moveTo, LineTo lineTo){
        double tempX = moveTo.getX();
        double tempY = moveTo.getY();
        moveTo.setX(lineTo.getX());
        moveTo.setY(lineTo.getY());
        lineTo.setX(tempX);
        lineTo.setY(tempY);
    }
    // overloading reverseDirection method
    public void reverseDirection(MoveTo moveTo, ArcTo arcTo){
        double tempX = moveTo.getX();
        double tempY = moveTo.getY();
        moveTo.setX(arcTo.getX());
        moveTo.setY(arcTo.getY());
        arcTo.setX(tempX);
        arcTo.setY(tempY);
        arcTo.setLargeArcFlag(false);
        arcTo.setSweepFlag(true);
    }

    public int indexFinder(GameBoard gameBoard, Tile tile){
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



    public static int getNumberOfMoves() {
        return numberOfMoves;
    }

    public static void setNumberOfMoves(int numberOfMoves) {
        Main.numberOfMoves = numberOfMoves;
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
    public boolean isClicked() {
        return isClicked;
    }

    public void setClicked(boolean clicked) {
        isClicked = clicked;
    }

}