import Tiles.*;
import javafx.animation.PathTransition;
import javafx.application.Application;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcTo;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;

public class Main extends Application {
    private static int numberOfMoves;
    private Path path;
    private boolean levelCompleted;
    private ArrayList<Tile> orderedPipes;



    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {


        GameBoard gameBoard = new GameBoard();
        drag(gameBoard.getImageViews(), gameBoard.getTiles(),gameBoard);
        displayNumberOfMoves(gameBoard);
        Path path = new Path();
        setPath(path);
        ((EdgePane) gameBoard.getBorderPane().getBottom()).getButton().setOnMouseClicked(event -> {
            setLevelCompleted(checkForSolution(gameBoard));
            if (isLevelCompleted()) {
                setWholePath(gameBoard);
                setPath(getPath());
                gameBoard.getPane().getChildren().add(getPath());
                PathTransition pathTransition = new PathTransition();
                pathTransition.setPath(getPath());
                pathTransition.setNode(gameBoard.getBall());
                pathTransition.setDuration(Duration.seconds(3));
                pathTransition.play();
                gameBoard.setLevelCompleted(true);
                gameBoard.setLevelNo(gameBoard.getLevelNo() + 1);
            }
        });
/*
        gameBoard.getNextButton().setOnMouseClicked(e -> {
        drag(gameBoard.getImageViews(), gameBoard.getTiles(),gameBoard);
        displayNumberOfMoves(gameBoard);

        Path path2 = new Path();
        setPath(path2);
        ((EdgePane) gameBoard.getBorderPane().getBottom()).getButton().setOnMouseClicked(event -> {
            setLevelCompleted(checkForSolution(gameBoard));
            if (isLevelCompleted()) {
                setWholePath(gameBoard);
                setPath(getPath());
                gameBoard.getPane().getChildren().add(getPath());
                PathTransition pathTransition = new PathTransition();
                pathTransition.setPath(getPath());
                pathTransition.setNode(gameBoard.getBall());
                pathTransition.setDuration(Duration.seconds(3));
                pathTransition.play();
                gameBoard.setLevelCompleted(true);
                gameBoard.setLevelNo(gameBoard.getLevelNo() + 1);
            }
        });
        });

 */





    }

    private void drag(ImageView[] imageViews, Tile[][] tiles, GameBoard gameBoard) {
        for (ImageView imageView : imageViews) {

            imageView.setOnMouseReleased(e -> {
                ImageView imageView1 = (ImageView) e.getTarget(); // gets the first node
                ImageView imageView2 = (ImageView) e.getPickResult().getIntersectedNode(); // gets the last node
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
                if(tiles[index2x][index2y] instanceof  EmptyFree) {
                    if (!((index1x == index2x) && (index1y == index2y))) {
                        if (tiles[index1x][index1y] instanceof EmptyFree) {
                            if ((tiles[index1x][index1y] instanceof Movable) && (tiles[index2x][index2y] instanceof Movable)
                                    && !(tiles[index1x][index1y] instanceof EmptyFree)) {
                                if (Math.abs(imageView2.getX() - imageView1.getX()) <= 180 &&
                                        imageView2.getY() == imageView1.getY()) {
                                    swapImages(imageView1, imageView2);
                                    displayNumberOfMoves(gameBoard);
                                    swapTiles(index1x, index1y, index2x, index2y, gameBoard);

                                }
                                if (Math.abs(imageView2.getY() - imageView1.getY()) <= 180 &&
                                        imageView2.getX() == imageView1.getX()) {
                                    swapImages(imageView1, imageView2);
                                    displayNumberOfMoves(gameBoard);
                                    swapTiles(index1x, index1y, index2x, index2y, gameBoard);

                                }
                            }
                        } else {
                            if ((tiles[index1x][index1y] instanceof Movable) && (tiles[index2x][index2y] instanceof Movable)
                                    && !(tiles[index1x][index1y] instanceof EmptyFree)) {
                                if (Math.abs(imageView2.getX() - imageView1.getX()) <= 180 &&
                                        imageView2.getY() == imageView1.getY()) {
                                    swapImages(imageView1, imageView2);
                                    displayNumberOfMoves(gameBoard);
                                    swapTiles(index1x, index1y, index2x, index2y, gameBoard);

                                }
                                if (Math.abs(imageView2.getY() - imageView1.getY()) <= 180 &&
                                        imageView2.getX() == imageView1.getX()) {
                                    swapImages(imageView1, imageView2);
                                    displayNumberOfMoves(gameBoard);
                                    swapTiles(index1x, index1y, index2x, index2y, gameBoard);
                                }
                            }
                        }
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
    public void displayNumberOfMoves(GameBoard gameBoard){

        Label label = new Label("Number of Moves " + getNumberOfMoves());
        label.setStyle("-fx-text-fill: white");
        label.setFont(Font.font("Arial", FontWeight.BOLD, 30));
        EdgePane edgePane = new EdgePane(label);
        edgePane.setBackground(new Background(new BackgroundImage(new Image("Background.jpg"), BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT)));
        gameBoard.getBorderPane().setTop(edgePane);

    }

    public void swapTiles(/*Tile[][] tiles,*/ int index1x, int index1y, int index2x, int index2y, GameBoard gameBoard){
        Tile temp = gameBoard.getTiles()[index1x][index1y];
        gameBoard.getTiles()[index1x][index1y] = gameBoard.getTiles()[index2x][index2y];
        gameBoard.getTiles()[index2x][index2y] = temp;
    }

    public boolean checkForSolution(GameBoard gameBoard){
        orderedPipes = new ArrayList<>();
        boolean tileTrue;
        boolean tileTrue2;
        boolean tileTrue3;
        boolean tileTrue4;
        boolean tileTrue5;
        boolean tileTrue6;
        boolean tileTrue7;
        ArrayList<Boolean> checkBooleanList = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                tileTrue = false;
                tileTrue2 = false;
                tileTrue3 = false;
                tileTrue4 = false;
                tileTrue5 = false;
                tileTrue6 = false;
                tileTrue7 = false;

                if (gameBoard.getTiles()[i][j] instanceof StartPipe) {
                    if (gameBoard.getTiles()[i][j].getStatus().equals("Vertical") && (i != 3)) {
                        if (gameBoard.getTiles()[i + 1][j].getStatus().equals("Vertical")) {
                            tileTrue2 = true;
                            orderedPipes.add(gameBoard.getTiles()[i][j]);
                            orderedPipes.add(gameBoard.getTiles()[i + 1][j]);
                        }
                        if (gameBoard.getTiles()[i + 1][j].getStatus().equals("00")) {
                            tileTrue4 = true;
                            orderedPipes.add(gameBoard.getTiles()[i + 1][j]);
                        } else if (gameBoard.getTiles()[i + 1][j].getStatus().equals("01")) {
                            tileTrue4 = true;
                            orderedPipes.add(gameBoard.getTiles()[i + 1][j]);
                        }
                    }

                    if (gameBoard.getTiles()[i][j].getStatus().equals("Horizontal") && (j != 0)) {
                        tileTrue = true;
                    }

                    tileTrue3 = tileTrue || tileTrue2 || tileTrue4;
                    checkBooleanList.add(tileTrue3);
                }



                if (gameBoard.getTiles()[i][j] instanceof EndPipe) {
                    if (gameBoard.getTiles()[i][j].getStatus().equals("Vertical") && (i != 3)) {
                        if (gameBoard.getTiles()[i + 1][j].getStatus().equals("Vertical")) {
                            tileTrue = true;
                        }
                        if (gameBoard.getTiles()[i + 1][j].getStatus().equals("00")) {
                            tileTrue4 = true;
                        } else if (gameBoard.getTiles()[i + 1][j].getStatus().equals("01")) {
                            tileTrue4 = true;
                        }
                    }
                    if (gameBoard.getTiles()[i][j].getStatus().equals("Horizontal") && (j != 0)) {
                        tileTrue2 = true;
                    }
                    tileTrue3 = tileTrue || tileTrue2 || tileTrue4;
                    checkBooleanList.add(tileTrue3);
                }


                if (gameBoard.getTiles()[i][j] instanceof LinearPipe) {
                    if (gameBoard.getTiles()[i][j].getStatus().equals("Vertical") && (i != 3)) {
                        if (gameBoard.getTiles()[i + 1][j].getStatus().equals("Vertical")) {
                            tileTrue = true;
                            orderedPipes.add(gameBoard.getTiles()[i + 1][j]);
                        }
                        if (gameBoard.getTiles()[i + 1][j].getStatus().equals("00")) {
                            tileTrue4 = true;
                            orderedPipes.add(gameBoard.getTiles()[i + 1][j]);
                        }
                        else if (gameBoard.getTiles()[i + 1][j].getStatus().equals("01")) {
                            tileTrue4 = true;
                            orderedPipes.add(gameBoard.getTiles()[i + 1][j]);
                        }
                    }

                    if (gameBoard.getTiles()[i][j].getStatus().equals("Horizontal") && (j != 3)) {
                        if (gameBoard.getTiles()[i][j + 1].getStatus().equals("Horizontal")) {
                            tileTrue2 = true;
                            orderedPipes.add(gameBoard.getTiles()[i][j + 1]);
                        }
                        if (gameBoard.getTiles()[i][j + 1].getStatus().equals("00")) {
                            tileTrue4 = true;
                            orderedPipes.add(gameBoard.getTiles()[i][j + 1]);
                        } else if (gameBoard.getTiles()[i][j + 1].getStatus().equals("10")) {
                            tileTrue4 = true;
                            orderedPipes.add(gameBoard.getTiles()[i][j + 1]);
                        }
                    }
                    tileTrue3 = tileTrue || tileTrue2 || tileTrue4;
                    checkBooleanList.add(tileTrue3);
                }


                if (gameBoard.getTiles()[i][j] instanceof NormalPipeStatic) {
                    if (gameBoard.getTiles()[i][j].getStatus().equals("Vertical") && (i != 3)) {
                        if (gameBoard.getTiles()[i + 1][j].getStatus().equals("Vertical")) {
                            tileTrue = true;
                            orderedPipes.add(gameBoard.getTiles()[i + 1][j]);
                        }
                        if (gameBoard.getTiles()[i + 1][j].getStatus().equals("00")) {
                            tileTrue4 = true;
                            orderedPipes.add(gameBoard.getTiles()[i + 1][j]);
                        } else if (gameBoard.getTiles()[i + 1][j].getStatus().equals("01")) {
                            tileTrue4 = true;
                            orderedPipes.add(gameBoard.getTiles()[i + 1][j]);
                        }
                    }

                    if (gameBoard.getTiles()[i][j].getStatus().equals("Horizontal") && (j != 3)) {
                        if (gameBoard.getTiles()[i][j + 1].getStatus().equals("Horizontal")) {
                            tileTrue2 = true;
                            orderedPipes.add(gameBoard.getTiles()[i][j + 1]);
                        }
                        if (gameBoard.getTiles()[i][j + 1].getStatus().equals("00")) {
                            tileTrue4 = true;
                            orderedPipes.add(gameBoard.getTiles()[i][j + 1]);
                        } else if (gameBoard.getTiles()[i][j + 1].getStatus().equals("10")) {
                            tileTrue4 = true;
                            orderedPipes.add(gameBoard.getTiles()[i][j + 1]);
                        }
                    }
                    tileTrue3 = tileTrue || tileTrue2 || tileTrue4;
                    checkBooleanList.add(tileTrue3);
                }


                if (gameBoard.getTiles()[i][j] instanceof CurvedPipeMovable) {

                    if (gameBoard.getTiles()[i][j].getStatus().equals("00") && (i != 0 && j != 0)) {
                        tileTrue7 = true;
                        orderedPipes.add(gameBoard.getTiles()[i - 1][j]);
                    }

                    if (gameBoard.getTiles()[i][j].getStatus().equals("01") && (i != 0) && (j != 3)) {
                        if (gameBoard.getTiles()[i][j + 1].getStatus().equals("Horizontal")) {
                            tileTrue = true;
                            orderedPipes.add(gameBoard.getTiles()[i][j + 1]);
                        }
                        if (gameBoard.getTiles()[i][j + 1].getStatus().equals("00")) {
                            tileTrue6 = true;
                            orderedPipes.add(gameBoard.getTiles()[i][j + 1]);
                        } else if (gameBoard.getTiles()[i][j + 1].getStatus().equals("10")) {
                            tileTrue6 = true;
                            orderedPipes.add(gameBoard.getTiles()[i][j + 1]);
                        }
                    }
                    if (gameBoard.getTiles()[i][j].getStatus().equals("10") && (j != 0) && (i != 3)) {
                        if (gameBoard.getTiles()[i + 1][j].getStatus().equals("Vertical")) {
                            tileTrue4 = true;
                            orderedPipes.add(gameBoard.getTiles()[i + 1][j]);
                        }
                        /*
                        if (gameBoard.getTiles()[i][j - 1].getStatus().equals("00") && (j != 3)) {
                            tileTrue6 = true;
                            orderedPipes.add(gameBoard.getTiles()[i][j-1]);

                        } else if (gameBoard.getTiles()[i][j - 1].getStatus().equals("01") && (j != 3)) {
                            tileTrue6 = true;
                            orderedPipes.add(gameBoard.getTiles()[i][j-1]);
                        }
                         */
                    }
                    if (gameBoard.getTiles()[i][j].getStatus().equals("11")) {
                        if (gameBoard.getTiles()[i][j + 1].getStatus().equals("Horizontal") && (j != 3)) {
                            tileTrue2 = true;
                            orderedPipes.add(gameBoard.getTiles()[i][j + 1]);
                        }
                        if (gameBoard.getTiles()[i + 1][j].getStatus().equals("Vertical") && (i != 3)) {
                            tileTrue5 = true;
                            orderedPipes.add(gameBoard.getTiles()[i][j + 1]);
                        }
                        // YANA BAKANLAR
                        if (gameBoard.getTiles()[i][j + 1].getStatus().equals("00") && (j != 3)) {
                            tileTrue6 = true;
                            orderedPipes.add(gameBoard.getTiles()[i][j + 1]);
                        } else if (gameBoard.getTiles()[i][j + 1].getStatus().equals("10") && (j != 3)) {
                            tileTrue6 = true;
                            orderedPipes.add(gameBoard.getTiles()[i][j + 1]);
                        }
                        // ALTA BAKANLAR
                        else if (gameBoard.getTiles()[i + 1][j].getStatus().equals("00") && (i != 3)) {
                            tileTrue6 = true;
                            orderedPipes.add(gameBoard.getTiles()[i + 1][j]);
                        } else if (gameBoard.getTiles()[i + 1][j].getStatus().equals("01") && (i != 3)) {
                            tileTrue6 = true;
                            orderedPipes.add(gameBoard.getTiles()[i + 1][j]);
                        }
                    }

                    tileTrue3 = tileTrue ||tileTrue2 || tileTrue4 || tileTrue5 || tileTrue6 || tileTrue7;
                    checkBooleanList.add(tileTrue3);
                }

                if (gameBoard.getTiles()[i][j] instanceof CurvedPipeStatic) {

                    if (gameBoard.getTiles()[i][j].getStatus().equals("00") && (i != 0 && j != 0)) {
                        tileTrue7 = true;
                    }

                    if (gameBoard.getTiles()[i][j].getStatus().equals("01") && (i != 0) && (j != 3)) {
                        if (gameBoard.getTiles()[i][j + 1].getStatus().equals("Horizontal")) {
                            tileTrue = true;
                            orderedPipes.add(gameBoard.getTiles()[i][j + 1]);
                        }
                        if (gameBoard.getTiles()[i][j + 1].getStatus().equals("00")) {
                            tileTrue6 = true;
                            orderedPipes.add(gameBoard.getTiles()[i][j + 1]);
                        } else if (gameBoard.getTiles()[i][j + 1].getStatus().equals("10")) {
                            tileTrue6 = true;
                            orderedPipes.add(gameBoard.getTiles()[i][j + 1]);
                        }
                    }
                    if (gameBoard.getTiles()[i][j].getStatus().equals("10") && (j != 0) && (i != 3)) {
                        if (gameBoard.getTiles()[i + 1][j].getStatus().equals("Vertical")) {
                            tileTrue4 = true;
                            orderedPipes.add(gameBoard.getTiles()[i + 1][j]);
                        }
                        /*
                        if (gameBoard.getTiles()[i][j - 1].getStatus().equals("00") && (j != 3)) {
                            tileTrue6 = true;
                            orderedPipes.add(gameBoard.getTiles()[i][j-1]);
                        } else if (gameBoard.getTiles()[i][j - 1].getStatus().equals("01") && (j != 3)) {
                            tileTrue6 = true;
                            orderedPipes.add(gameBoard.getTiles()[i][j-1]);
                        }
                         */
                    }
                    if (gameBoard.getTiles()[i][j].getStatus().equals("11")) {
                        if (gameBoard.getTiles()[i][j + 1].getStatus().equals("Horizontal") && (j != 3)) {
                            tileTrue2 = true;
                            orderedPipes.add(gameBoard.getTiles()[i][j + 1]);
                        }
                        if (gameBoard.getTiles()[i + 1][j].getStatus().equals("Vertical") && (i != 3)) {
                            tileTrue5 = true;
                            orderedPipes.add(gameBoard.getTiles()[i + 1][j]);
                        }
                        // YANA BAKANLAR
                        if (gameBoard.getTiles()[i][j + 1].getStatus().equals("00") && (j != 3)) {
                            tileTrue6 = true;
                            orderedPipes.add(gameBoard.getTiles()[i + 1][j]);
                        } else if (gameBoard.getTiles()[i][j + 1].getStatus().equals("10") && (j != 3)) {
                            tileTrue6 = true;
                            orderedPipes.add(gameBoard.getTiles()[i + 1][j]);
                        }
                        // ALTA BAKANLAR
                        else if (gameBoard.getTiles()[i + 1][j].getStatus().equals("00") && (i != 3)) {
                            tileTrue6 = true;
                            orderedPipes.add(gameBoard.getTiles()[i + 1][j]);
                        } else if (gameBoard.getTiles()[i + 1][j].getStatus().equals("01") && (i != 3)) {
                            tileTrue6 = true;
                            orderedPipes.add(gameBoard.getTiles()[i + 1][j]);
                        }
                    }

                    tileTrue3 = tileTrue ||tileTrue2 || tileTrue4 || tileTrue5 || tileTrue6 || tileTrue7;
                    checkBooleanList.add(tileTrue3);
                }
            }
        }
        if (checkBooleanList.contains(false)){
            System.out.println("false");
            return false;
        }
        else {
            System.out.println("true");
            return true;
        }
    }

    public void setWholePath(GameBoard gameBoard){
        Path path = new Path();
        for (int i = 0; i < getOrderedPipes().size(); i++){

                if (getOrderedPipes().get(i) instanceof StartPipe) {
                    int indexOfImageView = indexFinder(gameBoard, getOrderedPipes().get(i));

                    if (getOrderedPipes().get(i).getStatus().equals("Vertical")) {
                        MoveTo moveTo = new MoveTo(gameBoard.getImageViews()[indexOfImageView].getX() + 70, gameBoard.getImageViews()[indexOfImageView].getY() + 50.5);
                        LineTo lineTo = new LineTo(gameBoard.getImageViews()[indexOfImageView].getX() + 70, gameBoard.getImageViews()[indexOfImageView].getY() + 141.5);
                        path.getElements().addAll(moveTo, lineTo);
                    }
                    if (getOrderedPipes().get(i).getStatus().equals("Horizontal")) {
                        MoveTo moveTo = new MoveTo(gameBoard.getImageViews()[indexOfImageView].getX() + 90, gameBoard.getImageViews()[indexOfImageView].getY() + 70);
                        LineTo lineTo = new LineTo(gameBoard.getImageViews()[indexOfImageView].getX(), gameBoard.getImageViews()[indexOfImageView].getY() + 70);
                        path.getElements().addAll(moveTo, lineTo);
                    }
                    path.setStroke(Color.WHITE);

                }


                if (getOrderedPipes().get(i) instanceof EndPipe) {
                    int indexOfImageView = indexFinder(gameBoard, getOrderedPipes().get(i));

                    if (getOrderedPipes().get(i).getStatus().equals("Vertical")) {
                        LineTo lineTo = new LineTo(gameBoard.getImageViews()[indexOfImageView].getX() + 70, gameBoard.getImageViews()[indexOfImageView].getY() + 50.5);
                        MoveTo moveTo = new MoveTo(gameBoard.getImageViews()[indexOfImageView].getX() + 70, gameBoard.getImageViews()[indexOfImageView].getY() + 141.5);
                        path.getElements().addAll(moveTo, lineTo);
                    }
                    if (getOrderedPipes().get(i).getStatus().equals("Horizontal")) {
                        LineTo lineTo = new LineTo(gameBoard.getImageViews()[indexOfImageView].getX() + 90, gameBoard.getImageViews()[indexOfImageView].getY() + 70);
                        MoveTo moveTo = new MoveTo(gameBoard.getImageViews()[indexOfImageView].getX(), gameBoard.getImageViews()[indexOfImageView].getY() + 70);
                        path.getElements().addAll(moveTo, lineTo);
                    }
                    path.setStroke(Color.WHITE);
                }




                if (getOrderedPipes().get(i) instanceof LinearPipe) {
                    int indexOfImageView = indexFinder(gameBoard, getOrderedPipes().get(i));


                    if (getOrderedPipes().get(i).getStatus().equals("Vertical")) {
                        MoveTo moveTo = new MoveTo(gameBoard.getImageViews()[indexOfImageView].getX() + 70, gameBoard.getImageViews()[indexOfImageView].getY());

                        LineTo lineTo = new LineTo(gameBoard.getImageViews()[indexOfImageView].getX() + 70, gameBoard.getImageViews()[indexOfImageView].getY() + 140);
                        path.getElements().addAll(moveTo, lineTo);
                    }
                    if (getOrderedPipes().get(i).getStatus().equals("Horizontal")) {
                        MoveTo moveTo = new MoveTo(gameBoard.getImageViews()[indexOfImageView].getX(), gameBoard.getImageViews()[indexOfImageView].getY() + 70);
                        LineTo lineTo = new LineTo(gameBoard.getImageViews()[indexOfImageView].getX() + 140, gameBoard.getImageViews()[indexOfImageView].getY() + 70);
                        path.getElements().addAll(moveTo, lineTo);
                    }
                    path.setStroke(Color.WHITE);
                }



                if (getOrderedPipes().get(i) instanceof NormalPipeStatic) {
                    int indexOfImageView = indexFinder(gameBoard, getOrderedPipes().get(i));

                    if (getOrderedPipes().get(i).getStatus().equals("Vertical")) {
                        MoveTo moveTo = new MoveTo(gameBoard.getImageViews()[indexOfImageView].getX() + 70, gameBoard.getImageViews()[indexOfImageView].getY());

                        LineTo lineTo = new LineTo(gameBoard.getImageViews()[indexOfImageView].getX() + 70, gameBoard.getImageViews()[indexOfImageView].getY() + 140);
                        path.getElements().addAll(moveTo, lineTo);
                    }
                    if (getOrderedPipes().get(i).getStatus().equals("Horizontal")) {
                        MoveTo moveTo = new MoveTo(gameBoard.getImageViews()[indexOfImageView].getX(), gameBoard.getImageViews()[indexOfImageView].getY()+70);
                        LineTo lineTo = new LineTo(gameBoard.getImageViews()[indexOfImageView].getX() + 140, gameBoard.getImageViews()[indexOfImageView].getY() + 70);
                        path.getElements().addAll(moveTo, lineTo);
                    }
                    path.setStroke(Color.WHITE);
                }



                if (getOrderedPipes().get(i) instanceof CurvedPipeMovable) {
                    int indexOfImageView = indexFinder(gameBoard, getOrderedPipes().get(i));

                    if (getOrderedPipes().get(i).getStatus().equals("00")){
                        MoveTo moveTo = new MoveTo(gameBoard.getImageViews()[indexOfImageView].getX() , gameBoard.getImageViews()[indexOfImageView].getY() +70 );
                        ArcTo arcTo = new ArcTo();
                        arcTo.setX(gameBoard.getImageViews()[indexOfImageView].getX() + 70);
                        arcTo.setY(gameBoard.getImageViews()[indexOfImageView].getY());
                        arcTo.setRadiusX(70);
                        arcTo.setRadiusY(70);
                        path.getElements().addAll(moveTo, arcTo);
                    }
                    if (getOrderedPipes().get(i).getStatus().equals("01")){
                        MoveTo moveTo = new MoveTo(gameBoard.getImageViews()[indexOfImageView].getX() + 70 , gameBoard.getImageViews()[indexOfImageView].getY());
                        ArcTo arcTo = new ArcTo();
                        arcTo.setX(gameBoard.getImageViews()[indexOfImageView].getX() + 140);
                        arcTo.setY(gameBoard.getImageViews()[indexOfImageView].getY() + 70);
                        arcTo.setRadiusX(70);
                        arcTo.setRadiusY(70);
                        path.getElements().addAll(moveTo, arcTo);
                    }
                    if (getOrderedPipes().get(i).getStatus().equals("10")){
                        MoveTo moveTo = new MoveTo(gameBoard.getImageViews()[indexOfImageView].getX() , gameBoard.getImageViews()[indexOfImageView].getY() + 70);
                        ArcTo arcTo = new ArcTo();
                        arcTo.setX(gameBoard.getImageViews()[indexOfImageView].getX() + 70);
                        arcTo.setY(gameBoard.getImageViews()[indexOfImageView].getY() + 140);
                        arcTo.setRadiusX(70);
                        arcTo.setRadiusY(70);
                        arcTo.setLargeArcFlag(false);
                        arcTo.setSweepFlag(true);
                        path.getElements().addAll(moveTo, arcTo);
                    }
                    if (getOrderedPipes().get(i).getStatus().equals("11")){ // mantıken yapıyorum deneyemedim
                        MoveTo moveTo = new MoveTo(gameBoard.getImageViews()[indexOfImageView].getX() + 70 , gameBoard.getImageViews()[indexOfImageView].getY() + 140);
                        ArcTo arcTo = new ArcTo();
                        arcTo.setX(gameBoard.getImageViews()[indexOfImageView].getX() + 140);
                        arcTo.setY(gameBoard.getImageViews()[indexOfImageView].getY() + 70);
                        arcTo.setRadiusX(70);
                        arcTo.setRadiusY(70);
                        arcTo.setLargeArcFlag(false);
                        arcTo.setSweepFlag(true);
                        path.getElements().addAll(moveTo, arcTo);
                    }
                    path.setStroke(Color.WHITE);
                }


                if (getOrderedPipes().get(i) instanceof CurvedPipeStatic) {
                    int indexOfImageView = indexFinder(gameBoard, getOrderedPipes().get(i));

                    if (getOrderedPipes().get(i).getStatus().equals("00")){
                        MoveTo moveTo = new MoveTo(gameBoard.getImageViews()[indexOfImageView].getX() , gameBoard.getImageViews()[indexOfImageView].getY() +70 );
                        ArcTo arcTo = new ArcTo();
                        arcTo.setX(gameBoard.getImageViews()[indexOfImageView].getX() + 70);
                        arcTo.setY(gameBoard.getImageViews()[indexOfImageView].getY());
                        arcTo.setRadiusX(70);
                        arcTo.setRadiusY(70);
                        path.getElements().addAll(moveTo, arcTo);
                    }
                    if (getOrderedPipes().get(i).getStatus().equals("01")){
                        MoveTo moveTo = new MoveTo(gameBoard.getImageViews()[indexOfImageView].getX() + 70 , gameBoard.getImageViews()[indexOfImageView].getY());
                        ArcTo arcTo = new ArcTo();
                        arcTo.setX(gameBoard.getImageViews()[indexOfImageView].getX() + 140);
                        arcTo.setY(gameBoard.getImageViews()[indexOfImageView].getY() + 70);
                        arcTo.setRadiusX(70);
                        arcTo.setRadiusY(70);
                        path.getElements().addAll(moveTo, arcTo);
                    }
                    if (getOrderedPipes().get(i).getStatus().equals("10")){
                        MoveTo moveTo = new MoveTo(gameBoard.getImageViews()[indexOfImageView].getX() , gameBoard.getImageViews()[indexOfImageView].getY() + 70);
                        ArcTo arcTo = new ArcTo();
                        arcTo.setX(gameBoard.getImageViews()[indexOfImageView].getX() + 70);
                        arcTo.setY(gameBoard.getImageViews()[indexOfImageView].getY() + 140);
                        arcTo.setRadiusX(70);
                        arcTo.setRadiusY(70);
                        arcTo.setLargeArcFlag(false);
                        arcTo.setSweepFlag(true);
                        path.getElements().addAll(moveTo, arcTo);
                    }
                    if (getOrderedPipes().get(i).getStatus().equals("11")){ // mantıken yapıyorum deneyemedim
                        MoveTo moveTo = new MoveTo(gameBoard.getImageViews()[indexOfImageView].getX() + 70 , gameBoard.getImageViews()[indexOfImageView].getY() + 140);
                        ArcTo arcTo = new ArcTo();
                        arcTo.setX(gameBoard.getImageViews()[indexOfImageView].getX() + 140);
                        arcTo.setY(gameBoard.getImageViews()[indexOfImageView].getY() + 70);
                        arcTo.setRadiusX(70);
                        arcTo.setRadiusY(70);
                        arcTo.setLargeArcFlag(false);
                        arcTo.setSweepFlag(true);
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

    public void setLevelCompleted(boolean levelCompleted) {
        this.levelCompleted = levelCompleted;
    }

    public ArrayList<Tile> getOrderedPipes() {
        return orderedPipes;
    }

    public void setOrderedPipes(ArrayList<Tile> orderedPipes) {
        this.orderedPipes = orderedPipes;
    }
}