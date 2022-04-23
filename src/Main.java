import Tiles.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main extends Application {
    private static int numberOfMoves;

    public BorderPane getBorderPane() {
        return borderPane;
    }

    public void setBorderPane(BorderPane borderPane) {
        this.borderPane = borderPane;
    }

    private BorderPane borderPane;
    public static int getNumberOfMoves() {
        return numberOfMoves;
    }

    public static void setNumberOfMoves(int numberOfMoves) {
        Main.numberOfMoves = numberOfMoves;
    }


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {

            GameBoard gameBoard = new GameBoard();
            drag(gameBoard.getImageViews(), gameBoard.getTiles(),gameBoard);
            displayNumberOfMoves(gameBoard);





}

    private void drag(ImageView[] imageViews, Tile[][] tiles, GameBoard gameBoard) {
        for (int i = 0; i < imageViews.length; i++){

            imageViews[i].setOnMouseReleased(e -> {
                ImageView imageView1 = (ImageView) e.getTarget(); // ilk konumunu aldık
                ImageView imageView2 = (ImageView) e.getPickResult().getIntersectedNode(); // son konum
                int index1x = 0;
                int index1y = 0;
                int index2x = 0;
                int index2y = 0;
                for (int k = 0; k < 4; k++){
                    for (int j = 0; j < 4; j++){
                        if(tiles[k][j].getImage().equals(imageView1.getImage())){
                            index1x = k;
                            index1y = j;
                        }
                        if(tiles[k][j].getImage().equals(imageView2.getImage())){
                            index2x = k;
                            index2y = j;
                        }
                    }
                }
                if(!((index1x == index2x) && (index1y == index2y))) {
                    if (tiles[index1x][index1y] instanceof EmptyFree) {
                        if ((tiles[index1x][index1y] instanceof Movable) && (tiles[index2x][index2y] instanceof Movable)
                                && !(tiles[index1x][index1y] instanceof EmptyFree)) {
                            if (Math.abs(imageView2.getX() - imageView1.getX()) <= 180 &&
                                    imageView2.getY() == imageView1.getY()) {
                                swapImages(imageView1, imageView2);
                                displayNumberOfMoves(gameBoard);
                                swapTiles(tiles,index1x,index1y,index2x,index2y,gameBoard);

                            }
                            if (Math.abs(imageView2.getY() - imageView1.getY()) <= 180 &&
                                    imageView2.getX() == imageView1.getX()) {
                                swapImages(imageView1, imageView2);
                                displayNumberOfMoves(gameBoard);
                                swapTiles(tiles,index1x,index1y,index2x,index2y,gameBoard);

                            }
                        }
                    } else {
                        if ((tiles[index1x][index1y] instanceof Movable) && (tiles[index2x][index2y] instanceof Movable)
                                && !(tiles[index1x][index1y] instanceof EmptyFree)) {
                            if (Math.abs(imageView2.getX() - imageView1.getX()) <= 180 &&
                                    imageView2.getY() == imageView1.getY()) {
                                swapImages(imageView1, imageView2);
                                displayNumberOfMoves(gameBoard);
                                swapTiles(tiles,index1x,index1y,index2x,index2y,gameBoard);

                            }
                            if (Math.abs(imageView2.getY() - imageView1.getY()) <= 180 &&
                                    imageView2.getX() == imageView1.getX()) {
                                swapImages(imageView1, imageView2);
                                displayNumberOfMoves(gameBoard);
                                swapTiles(tiles,index1x,index1y,index2x,index2y,gameBoard);
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

    public void swapTiles(Tile[][] tiles, int index1x, int index1y, int index2x, int index2y, GameBoard gameBoard){
        Tile temp = gameBoard.getTiles()[index1x][index1y];
        gameBoard.getTiles()[index1x][index1y] = gameBoard.getTiles()[index2x][index2y];
        gameBoard.getTiles()[index2x][index2y] = temp;
    }
}

class EdgePane extends StackPane {
    public EdgePane(Button button){

        getChildren().add(button);
        //setStyle("-fx-border-color: white");
        setPadding(new Insets(25.5, 57.5, 50.5, 45.5));

        //For EdgePane's Background
        setBackground(new Background(new BackgroundImage(new Image("Background.jpg"), BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT)));
    }
    public EdgePane(Label label) {
        getChildren().add(label);
        //setStyle("-fx-border-color: white");
        setPadding(new Insets(10, 57.5, 75, 105.5));

        //For EdgePane's Background
        setBackground(new Background(new BackgroundImage(new Image("Background.jpg"), BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT)));
    }
}

