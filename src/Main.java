import Tiles.*;
import javafx.animation.PathTransition;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcTo;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;


public class Main extends Application {
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
        Button startButton = new Button("Start");
        StackPane startPane = new StackPane();
        startPane.getChildren().add(startButton);
        startPane.setBackground(new Background(new BackgroundImage(new Image("Background.jpg"), BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT)));
        Scene startScene = new Scene(startPane,950,780);
        primaryStage.setScene(startScene);
        primaryStage.setTitle("PipeBallMaze");
        primaryStage.show();


        GameBoard gameBoard = new GameBoard();
        Button nextButton = new Button("next");

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
            if(clickCount < (GameBoard.getTotalLevelNo() - 1)) {
                primaryStage.setScene(levelCompletedScene(nextButton));
                primaryStage.show();
            }
            else if(clickCount == (GameBoard.getTotalLevelNo() - 1)){
                StackPane stackPane = new StackPane();
                stackPane.getChildren().add(new Label("The game is completed."));
                Scene scene = new Scene(stackPane, 950, 780);
                primaryStage.setScene(scene);
                primaryStage.show();
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
        PathTransition pathTransition = new PathTransition();
        pathTransition.setPath(getPath());
        pathTransition.setNode(gameBoard.getBall());
        pathTransition.setDuration(Duration.seconds(10));
        pathTransition.play();
    }


    public Scene levelCompletedScene(Button button){
        Pane pane = new Pane();
        pane.getChildren().add(new Label("level completed. press next!"));
        pane.getChildren().add(button);
        Scene scene = new Scene(pane, 950,780);
        return scene;
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
                    if (tiles[index2x][index2y] instanceof EmptyFree) {
                        if (!((index1x == index2x) && (index1y == index2y))) {
                            if (tiles[index1x][index1y] instanceof EmptyFree) {
                                if ((tiles[index1x][index1y] instanceof Movable) && (tiles[index2x][index2y] instanceof Movable)
                                        && !(tiles[index1x][index1y] instanceof EmptyFree)) {
                                    if (Math.abs(imageView2.getX() - imageView1.getX()) <= 180 &&
                                            imageView2.getY() == imageView1.getY()) {
                                        swapImages(imageView1, imageView2);
                                        gameBoard.displayNumberOfMoves();
                                        swapTiles(gameBoard, index1x, index1y, index2x, index2y);

                                    }
                                    if (Math.abs(imageView2.getY() - imageView1.getY()) <= 180 &&
                                            imageView2.getX() == imageView1.getX()) {
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
                                        swapImages(imageView1, imageView2);
                                        gameBoard.displayNumberOfMoves();
                                        swapTiles(gameBoard, index1x, index1y, index2x, index2y);

                                    }
                                    if (Math.abs(imageView2.getY() - imageView1.getY()) <= 180 &&
                                            imageView2.getX() == imageView1.getX()) {
                                        swapImages(imageView1, imageView2);
                                        gameBoard.displayNumberOfMoves();
                                        swapTiles(gameBoard, index1x, index1y, index2x, index2y);
                                    }
                                }
                            }
                        }
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


                if (getPipesInOrder().get(i).getStatus().equals("Vertical")) {
                    MoveTo moveTo = new MoveTo(gameBoard.getImageViews()[indexOfImageView].getX() + 70, gameBoard.getImageViews()[indexOfImageView].getY());

                    LineTo lineTo = new LineTo(gameBoard.getImageViews()[indexOfImageView].getX() + 70, gameBoard.getImageViews()[indexOfImageView].getY() + 140);
                    path.getElements().addAll(moveTo, lineTo);
                }
                if (getPipesInOrder().get(i).getStatus().equals("Horizontal")) {
                    MoveTo moveTo = new MoveTo(gameBoard.getImageViews()[indexOfImageView].getX(), gameBoard.getImageViews()[indexOfImageView].getY() + 70);
                    LineTo lineTo = new LineTo(gameBoard.getImageViews()[indexOfImageView].getX() + 140, gameBoard.getImageViews()[indexOfImageView].getY() + 70);
                    path.getElements().addAll(moveTo, lineTo);
                }
                path.setStroke(Color.WHITE);
            }



            if (getPipesInOrder().get(i) instanceof NormalPipeStatic) {
                int indexOfImageView = indexFinder(gameBoard, getPipesInOrder().get(i));

                if (getPipesInOrder().get(i).getStatus().equals("Vertical")) {
                    MoveTo moveTo = new MoveTo(gameBoard.getImageViews()[indexOfImageView].getX() + 70, gameBoard.getImageViews()[indexOfImageView].getY());

                    LineTo lineTo = new LineTo(gameBoard.getImageViews()[indexOfImageView].getX() + 70, gameBoard.getImageViews()[indexOfImageView].getY() + 140);
                    path.getElements().addAll(moveTo, lineTo);
                }
                if (getPipesInOrder().get(i).getStatus().equals("Horizontal")) {
                    MoveTo moveTo = new MoveTo(gameBoard.getImageViews()[indexOfImageView].getX(), gameBoard.getImageViews()[indexOfImageView].getY()+70);
                    LineTo lineTo = new LineTo(gameBoard.getImageViews()[indexOfImageView].getX() + 140, gameBoard.getImageViews()[indexOfImageView].getY() + 70);
                    path.getElements().addAll(moveTo, lineTo);
                }
                path.setStroke(Color.WHITE);
            }



            if (getPipesInOrder().get(i) instanceof CurvedPipeMovable) {
                int indexOfImageView = indexFinder(gameBoard, getPipesInOrder().get(i));

                if (getPipesInOrder().get(i).getStatus().equals("00")){ // denendi onaylandı (iki yönlü de çalışıyor)
                    MoveTo moveTo;
                    ArcTo arcTo = new ArcTo();
                    if( (i != 0) && (getPipesInOrder().get(i-1).getStatus().equals("Horizontal") ||
                            getPipesInOrder().get(i-1).getStatus().equals("01") ||
                            getPipesInOrder().get(i-1).getStatus().equals("11"))){
                        moveTo = new MoveTo(gameBoard.getImageViews()[indexOfImageView].getX() , gameBoard.getImageViews()[indexOfImageView].getY() +70 );
                        arcTo.setX(gameBoard.getImageViews()[indexOfImageView].getX() + 70);
                        arcTo.setY(gameBoard.getImageViews()[indexOfImageView].getY());
                    }
                    else {
                        moveTo = new MoveTo(gameBoard.getImageViews()[indexOfImageView].getX() + 70, gameBoard.getImageViews()[indexOfImageView].getY());
                        arcTo.setX(gameBoard.getImageViews()[indexOfImageView].getX());
                        arcTo.setY(gameBoard.getImageViews()[indexOfImageView].getY() + 70);
                        arcTo.setLargeArcFlag(false);
                        arcTo.setSweepFlag(true);
                    }
                    arcTo.setRadiusX(70);
                    arcTo.setRadiusY(70);
                    path.getElements().addAll(moveTo, arcTo);
                }

                if (getPipesInOrder().get(i).getStatus().equals("01")){ // denendi onaylandı (iki yönlü de çalışıyor)
                    MoveTo moveTo;
                    ArcTo arcTo = new ArcTo();
                    if( (i != 0) && (getPipesInOrder().get(i-1).getStatus().equals("Vertical") ||
                            getPipesInOrder().get(i-1).getStatus().equals("10") ||
                            getPipesInOrder().get(i-1).getStatus().equals("11"))) {
                        moveTo = new MoveTo(gameBoard.getImageViews()[indexOfImageView].getX() + 70 , gameBoard.getImageViews()[indexOfImageView].getY());
                        arcTo.setX(gameBoard.getImageViews()[indexOfImageView].getX() + 140);
                        arcTo.setY(gameBoard.getImageViews()[indexOfImageView].getY() + 70);
                    }
                    else{
                        moveTo = new MoveTo(gameBoard.getImageViews()[indexOfImageView].getX() + 140, gameBoard.getImageViews()[indexOfImageView].getY() + 70);
                        arcTo.setX(gameBoard.getImageViews()[indexOfImageView].getX() + 70);
                        arcTo.setY(gameBoard.getImageViews()[indexOfImageView].getY());
                        arcTo.setLargeArcFlag(false);
                        arcTo.setSweepFlag(true);
                    }
                    arcTo.setRadiusX(70);
                    arcTo.setRadiusY(70);
                    path.getElements().addAll(moveTo, arcTo);
                }


                if (getPipesInOrder().get(i).getStatus().equals("10")){ // denendi onaylandı (iki yönlü de çalışıyor)
                    MoveTo moveTo;
                    ArcTo arcTo = new ArcTo();
                    if( (i != 0) && (getPipesInOrder().get(i-1).getStatus().equals("Horizontal") ||
                            getPipesInOrder().get(i-1).getStatus().equals("01") ||
                            getPipesInOrder().get(i-1).getStatus().equals("11"))) {
                        moveTo = new MoveTo(gameBoard.getImageViews()[indexOfImageView].getX() , gameBoard.getImageViews()[indexOfImageView].getY() + 70);
                        arcTo.setX(gameBoard.getImageViews()[indexOfImageView].getX() + 70);
                        arcTo.setY(gameBoard.getImageViews()[indexOfImageView].getY() + 140);
                        arcTo.setLargeArcFlag(false);
                        arcTo.setSweepFlag(true);
                    }
                    else{
                        moveTo = new MoveTo(gameBoard.getImageViews()[indexOfImageView].getX() + 70 , gameBoard.getImageViews()[indexOfImageView].getY() + 140);
                        arcTo.setX(gameBoard.getImageViews()[indexOfImageView].getX());
                        arcTo.setY(gameBoard.getImageViews()[indexOfImageView].getY() + 70);
                    }
                    arcTo.setRadiusX(70);
                    arcTo.setRadiusY(70);
                    path.getElements().addAll(moveTo, arcTo);
                }


                if (getPipesInOrder().get(i).getStatus().equals("11")){ // onaylandı (iki yönlü de çalışıyor olmalı)
                    MoveTo moveTo;
                    ArcTo arcTo = new ArcTo();
                    if( (i != 0) && (getPipesInOrder().get(i-1).getStatus().equals("Horizontal") ||
                            getPipesInOrder().get(i-1).getStatus().equals("00") ||
                            getPipesInOrder().get(i-1).getStatus().equals("01"))) {
                        moveTo = new MoveTo(gameBoard.getImageViews()[indexOfImageView].getX() + 140 , gameBoard.getImageViews()[indexOfImageView].getY() + 70);
                        arcTo.setX(gameBoard.getImageViews()[indexOfImageView].getX() + 70);
                        arcTo.setY(gameBoard.getImageViews()[indexOfImageView].getY() + 140);
                    }
                    else{
                        moveTo = new MoveTo(gameBoard.getImageViews()[indexOfImageView].getX() + 70 , gameBoard.getImageViews()[indexOfImageView].getY() + 140);
                        arcTo.setX(gameBoard.getImageViews()[indexOfImageView].getX() + 140);
                        arcTo.setY(gameBoard.getImageViews()[indexOfImageView].getY() + 70);
                        arcTo.setLargeArcFlag(false);
                        arcTo.setSweepFlag(true);
                    }
                    arcTo.setRadiusX(70);
                    arcTo.setRadiusY(70);
                    path.getElements().addAll(moveTo, arcTo);
                }
                path.setStroke(Color.WHITE);
            }


            if (getPipesInOrder().get(i) instanceof CurvedPipeStatic) {
                int indexOfImageView = indexFinder(gameBoard, getPipesInOrder().get(i));

                if (getPipesInOrder().get(i).getStatus().equals("00")){ //  onaylandı (iki yönlü de çalışıyor olmalı)
                    MoveTo moveTo;
                    ArcTo arcTo = new ArcTo();
                    if( (i != 0) && (getPipesInOrder().get(i-1).getStatus().equals("Horizontal") ||
                            getPipesInOrder().get(i-1).getStatus().equals("01") ||
                            getPipesInOrder().get(i-1).getStatus().equals("11"))){
                        moveTo = new MoveTo(gameBoard.getImageViews()[indexOfImageView].getX() , gameBoard.getImageViews()[indexOfImageView].getY() +70 );
                        arcTo.setX(gameBoard.getImageViews()[indexOfImageView].getX() + 70);
                        arcTo.setY(gameBoard.getImageViews()[indexOfImageView].getY());
                    }
                    else {
                        moveTo = new MoveTo(gameBoard.getImageViews()[indexOfImageView].getX() + 70, gameBoard.getImageViews()[indexOfImageView].getY());
                        arcTo.setX(gameBoard.getImageViews()[indexOfImageView].getX());
                        arcTo.setY(gameBoard.getImageViews()[indexOfImageView].getY() + 70);
                        arcTo.setLargeArcFlag(false);
                        arcTo.setSweepFlag(true);
                    }
                    arcTo.setRadiusX(70);
                    arcTo.setRadiusY(70);
                    path.getElements().addAll(moveTo, arcTo);
                }

                if (getPipesInOrder().get(i).getStatus().equals("01")){ // onaylandı (iki yönlü de çalışıyor olmalı)
                    MoveTo moveTo;
                    ArcTo arcTo = new ArcTo();
                    if( (i != 0) && (getPipesInOrder().get(i-1).getStatus().equals("Vertical") ||
                            getPipesInOrder().get(i-1).getStatus().equals("10") ||
                            getPipesInOrder().get(i-1).getStatus().equals("11"))) {
                        moveTo = new MoveTo(gameBoard.getImageViews()[indexOfImageView].getX() + 70 , gameBoard.getImageViews()[indexOfImageView].getY());
                        arcTo.setX(gameBoard.getImageViews()[indexOfImageView].getX() + 140);
                        arcTo.setY(gameBoard.getImageViews()[indexOfImageView].getY() + 70);
                    }
                    else{
                        moveTo = new MoveTo(gameBoard.getImageViews()[indexOfImageView].getX() + 140, gameBoard.getImageViews()[indexOfImageView].getY() + 70);
                        arcTo.setX(gameBoard.getImageViews()[indexOfImageView].getX() + 70);
                        arcTo.setY(gameBoard.getImageViews()[indexOfImageView].getY());
                        arcTo.setLargeArcFlag(false);
                        arcTo.setSweepFlag(true);
                    }
                    arcTo.setRadiusX(70);
                    arcTo.setRadiusY(70);
                    path.getElements().addAll(moveTo, arcTo);
                }

                if (getPipesInOrder().get(i).getStatus().equals("10")){ // onaylandı (iki yönlü de çalışıyor olmalı)
                    MoveTo moveTo;
                    ArcTo arcTo = new ArcTo();
                    if( (i != 0) && (getPipesInOrder().get(i-1).getStatus().equals("Horizontal") ||
                            getPipesInOrder().get(i-1).getStatus().equals("01") ||
                            getPipesInOrder().get(i-1).getStatus().equals("11"))) {
                        moveTo = new MoveTo(gameBoard.getImageViews()[indexOfImageView].getX() , gameBoard.getImageViews()[indexOfImageView].getY() + 70);
                        arcTo.setX(gameBoard.getImageViews()[indexOfImageView].getX() + 70);
                        arcTo.setY(gameBoard.getImageViews()[indexOfImageView].getY() + 140);
                        arcTo.setLargeArcFlag(false);
                        arcTo.setSweepFlag(true);
                    }
                    else{
                        moveTo = new MoveTo(gameBoard.getImageViews()[indexOfImageView].getX() + 70 , gameBoard.getImageViews()[indexOfImageView].getY() + 140);
                        arcTo.setX(gameBoard.getImageViews()[indexOfImageView].getX());
                        arcTo.setY(gameBoard.getImageViews()[indexOfImageView].getY() + 70);
                    }
                    arcTo.setRadiusX(70);
                    arcTo.setRadiusY(70);
                    path.getElements().addAll(moveTo, arcTo);
                }


                if (getPipesInOrder().get(i).getStatus().equals("11")){ // onaylandı (iki yönlü de çalışıyor olmalı)
                    MoveTo moveTo;
                    ArcTo arcTo = new ArcTo();
                    if( (i != 0) && (getPipesInOrder().get(i-1).getStatus().equals("Horizontal") ||
                            getPipesInOrder().get(i-1).getStatus().equals("00") ||
                            getPipesInOrder().get(i-1).getStatus().equals("01"))) {
                        moveTo = new MoveTo(gameBoard.getImageViews()[indexOfImageView].getX() + 70 , gameBoard.getImageViews()[indexOfImageView].getY() + 140);
                        arcTo.setX(gameBoard.getImageViews()[indexOfImageView].getX() + 140);
                        arcTo.setY(gameBoard.getImageViews()[indexOfImageView].getY() + 70);
                        arcTo.setLargeArcFlag(false);
                        arcTo.setSweepFlag(true);
                    }
                    else{
                        moveTo = new MoveTo(gameBoard.getImageViews()[indexOfImageView].getX() + 140 , gameBoard.getImageViews()[indexOfImageView].getY() + 70);
                        arcTo.setX(gameBoard.getImageViews()[indexOfImageView].getX() + 70);
                        arcTo.setY(gameBoard.getImageViews()[indexOfImageView].getY() + 140);
                    }
                    arcTo.setRadiusX(70);
                    arcTo.setRadiusY(70);
                    path.getElements().addAll(moveTo, arcTo);
                }
                path.setStroke(Color.WHITE);
            }


        }

        setPath(path);
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