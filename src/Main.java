import Tiles.*;
import javafx.application.Application;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Main extends Application {
    private static int numberOfMoves;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

            GameBoard gameBoard = new GameBoard();
            drag(gameBoard.getImageViews(), gameBoard.getTiles(),gameBoard);
            displayNumberOfMoves(gameBoard);


                ((EdgePane) gameBoard.getBorderPane().getBottom()).getButton().setOnMouseClicked(event -> {
                    checkForSolution(gameBoard);
                });


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
        boolean tileTrue = false;
        boolean tileTrue2 = false;
        boolean tileTrue3 = false;
        boolean tileTrue4 = false;
        boolean tileTrue5 = false;
        boolean tileTrue6 = false;
        boolean tileTrue7 = false;
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
                        }
                        if (gameBoard.getTiles()[i + 1][j].getStatus().equals("00") && (i != 3)) {
                            tileTrue4 = true;
                        } else if (gameBoard.getTiles()[i + 1][j].getStatus().equals("01") && (i != 3)) {
                            tileTrue4 = true;
                        }
                    }

                    if (gameBoard.getTiles()[i][j].getStatus().equals("Horizontal") && (j != 0)) {
                        tileTrue = true;
                    }

                    tileTrue3 = tileTrue || tileTrue2 || tileTrue4;
                    checkBooleanList.add(tileTrue3);


                    //PATH CREATION
                    MoveTo moveTo = new MoveTo(gameBoard.getBall().getX() + 28, gameBoard.getBall().getY() + 28.5);
                    Path path = new Path();
                    if (gameBoard.getTiles()[i][j].getStatus().equals("Vertical")) {
                        LineTo lineTo = new LineTo(gameBoard.getBall().getX() + 28, gameBoard.getBall().getY() + 109.5);
                        path.getElements().addAll(moveTo, lineTo);
                    }
                    if (gameBoard.getTiles()[i][j].getStatus().equals("Horizontal")) {
                        LineTo lineTo = new LineTo(gameBoard.getBall().getX() - 53, gameBoard.getBall().getY() + 28.5);
                        path.getElements().addAll(moveTo, lineTo);
                    }
                    path.setStroke(Color.WHITE);
                    gameBoard.getPane().getChildren().add(path);

                }


                if (gameBoard.getTiles()[i][j] instanceof EndPipe) {
                    if (gameBoard.getTiles()[i][j].getStatus().equals("Vertical") && (i != 3)) {
                        if (gameBoard.getTiles()[i + 1][j].getStatus().equals("Vertical")) {
                            tileTrue = true;
                        }
                        if (gameBoard.getTiles()[i + 1][j].getStatus().equals("00") && (i != 3)) {
                            tileTrue4 = true;
                        } else if (gameBoard.getTiles()[i + 1][j].getStatus().equals("01") && (i != 3)) {
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
                        }
                        if (gameBoard.getTiles()[i + 1][j].getStatus().equals("00") && (i != 3)) {
                            tileTrue4 = true;
                        } else if (gameBoard.getTiles()[i + 1][j].getStatus().equals("01") && (i != 3)) {
                            tileTrue4 = true;
                        }
                    }

                    if (gameBoard.getTiles()[i][j].getStatus().equals("Horizontal") && (j != 3)) {
                        if (gameBoard.getTiles()[i][j + 1].getStatus().equals("Horizontal")) {
                            tileTrue2 = true;
                        }
                        if (gameBoard.getTiles()[i][j + 1].getStatus().equals("00") && (j != 3)) {
                            tileTrue4 = true;
                        } else if (gameBoard.getTiles()[i][j + 1].getStatus().equals("10") && (j != 3)) {
                            tileTrue4 = true;
                        }
                    }
                    tileTrue3 = tileTrue || tileTrue2 || tileTrue4;
                    checkBooleanList.add(tileTrue3);

                    int indexOfEndX = 0;
                    int indexOfEndY = 0;
                    for (int k = 0; k < 4; k++){
                        for (int l = 0; l < 4; l++){
                            if (gameBoard.getTiles()[k][l] instanceof LinearPipe){
                                indexOfEndX = i;
                                indexOfEndY = j;
                            }
                        }
                    }
                    int imageViewIndex = 0;
                    for (int m = 0; m < 16; m++){
                        if(gameBoard.getTiles()[indexOfEndX][indexOfEndY].getImage().equals(gameBoard.getImageViews()[m].getImage())){
                            imageViewIndex = m;
                        }
                    }
                    //PATH CREATION
                    // 3LÜK ARA EKLENECEK!
                    Path path = new Path();
                    if (gameBoard.getTiles()[i][j].getStatus().equals("Vertical")) {
                        MoveTo moveTo = new MoveTo(gameBoard.getImageViews()[imageViewIndex].getX() + 70, gameBoard.getImageViews()[imageViewIndex].getY());

                        LineTo lineTo = new LineTo(gameBoard.getImageViews()[imageViewIndex].getX() + 70, gameBoard.getImageViews()[imageViewIndex].getY() + 140);
                        path.getElements().addAll(moveTo, lineTo);
                    }
                    if (gameBoard.getTiles()[i][j].getStatus().equals("Horizontal")) {
                        MoveTo moveTo = new MoveTo(gameBoard.getImageViews()[imageViewIndex].getX(), gameBoard.getImageViews()[imageViewIndex].getY()+70);
                        LineTo lineTo = new LineTo(gameBoard.getImageViews()[imageViewIndex].getX() + 140, gameBoard.getImageViews()[imageViewIndex].getY() + 70);
                        path.getElements().addAll(moveTo, lineTo);
                    }
                    path.setStroke(Color.WHITE);
                    gameBoard.getPane().getChildren().add(path);


                }


                if (gameBoard.getTiles()[i][j] instanceof NormalPipeStatic) {
                    if (gameBoard.getTiles()[i][j].getStatus().equals("Vertical") && (i != 3)) {
                        if (gameBoard.getTiles()[i + 1][j].getStatus().equals("Vertical")) {
                            tileTrue = true;
                        }
                        if (gameBoard.getTiles()[i + 1][j].getStatus().equals("00") && (i != 3)) {
                            tileTrue4 = true;
                        } else if (gameBoard.getTiles()[i + 1][j].getStatus().equals("01") && (i != 3)) {
                            tileTrue4 = true;
                        }
                    }

                    if (gameBoard.getTiles()[i][j].getStatus().equals("Horizontal") && (j != 3)) {
                        if (gameBoard.getTiles()[i][j + 1].getStatus().equals("Horizontal")) {
                            tileTrue2 = true;
                        }
                        if (gameBoard.getTiles()[i][j + 1].getStatus().equals("00") && (j != 3)) {
                            tileTrue4 = true;
                        } else if (gameBoard.getTiles()[i][j + 1].getStatus().equals("10") && (j != 3)) {
                            tileTrue4 = true;
                        }
                    }
                    tileTrue3 = tileTrue || tileTrue2 || tileTrue4;
                    checkBooleanList.add(tileTrue3);
                }


                if (gameBoard.getTiles()[i][j] instanceof CurvedPipeMovable) {

                    if (gameBoard.getTiles()[i][j].getStatus().equals("00") && (i != 0 && j != 0)) {
                        tileTrue7 = true;
                    }

                    if (gameBoard.getTiles()[i][j].getStatus().equals("01") && (i != 0) && (j != 3)) {
                        if (gameBoard.getTiles()[i][j + 1].getStatus().equals("Horizontal")) {
                            tileTrue = true;
                        }
                        if (gameBoard.getTiles()[i][j + 1].getStatus().equals("00") && (j != 3)) {
                            tileTrue6 = true;
                        } else if (gameBoard.getTiles()[i][j + 1].getStatus().equals("10") && (j != 3)) {
                            tileTrue6 = true;
                        }
                    }
                if (gameBoard.getTiles()[i][j].getStatus().equals("10") && (j != 0) && (i != 3)) {
                    if (gameBoard.getTiles()[i + 1][j].getStatus().equals("Vertical")) {
                        tileTrue4 = true;
                    }
                    if (gameBoard.getTiles()[i][j - 1].getStatus().equals("00") && (j != 3)) {
                        tileTrue6 = true;
                    } else if (gameBoard.getTiles()[i][j - 1].getStatus().equals("01") && (j != 3)) {
                        tileTrue6 = true;
                    }
                }
                if (gameBoard.getTiles()[i][j].getStatus().equals("11")) {
                    if (gameBoard.getTiles()[i][j + 1].getStatus().equals("Horizontal") && (j != 3)) {
                        tileTrue2 = true;
                    }
                    if (gameBoard.getTiles()[i + 1][j].getStatus().equals("Vertical") && (i != 3)) {
                        tileTrue5 = true;
                    }
                    // YANA BAKANLAR
                    if (gameBoard.getTiles()[i][j + 1].getStatus().equals("00") && (j != 3)) {
                        tileTrue6 = true;
                    } else if (gameBoard.getTiles()[i][j + 1].getStatus().equals("10") && (j != 3)) {
                        tileTrue6 = true;
                    }
                    // ALTA BAKANLAR
                    else if (gameBoard.getTiles()[i + 1][j].getStatus().equals("00") && (i != 3)) {
                        tileTrue6 = true;
                    } else if (gameBoard.getTiles()[i + 1][j].getStatus().equals("01") && (i != 3)) {
                        tileTrue6 = true;
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
                        }
                        if (gameBoard.getTiles()[i][j + 1].getStatus().equals("00") && (j != 3)) {
                            tileTrue6 = true;
                        } else if (gameBoard.getTiles()[i][j + 1].getStatus().equals("10") && (j != 3)) {
                            tileTrue6 = true;
                        }
                    }
                    if (gameBoard.getTiles()[i][j].getStatus().equals("10") && (j != 0) && (i != 3)) {
                        if (gameBoard.getTiles()[i + 1][j].getStatus().equals("Vertical")) {
                            tileTrue4 = true;
                        }
                        if (gameBoard.getTiles()[i][j - 1].getStatus().equals("00") && (j != 3)) {
                            tileTrue6 = true;
                        } else if (gameBoard.getTiles()[i][j - 1].getStatus().equals("01") && (j != 3)) {
                            tileTrue6 = true;
                        }
                    }
                    if (gameBoard.getTiles()[i][j].getStatus().equals("11")) {
                        if (gameBoard.getTiles()[i][j + 1].getStatus().equals("Horizontal") && (j != 3)) {
                            tileTrue2 = true;
                        }
                        if (gameBoard.getTiles()[i + 1][j].getStatus().equals("Vertical") && (i != 3)) {
                            tileTrue5 = true;
                        }
                        // YANA BAKANLAR
                        if (gameBoard.getTiles()[i][j + 1].getStatus().equals("00") && (j != 3)) {
                            tileTrue6 = true;
                        } else if (gameBoard.getTiles()[i][j + 1].getStatus().equals("10") && (j != 3)) {
                            tileTrue6 = true;
                        }
                        // ALTA BAKANLAR
                        else if (gameBoard.getTiles()[i + 1][j].getStatus().equals("00") && (i != 3)) {
                            tileTrue6 = true;
                        } else if (gameBoard.getTiles()[i + 1][j].getStatus().equals("01") && (i != 3)) {
                            tileTrue6 = true;
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
    public static int getNumberOfMoves() {
        return numberOfMoves;
    }

    public static void setNumberOfMoves(int numberOfMoves) {
        Main.numberOfMoves = numberOfMoves;
    }

}