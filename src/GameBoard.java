import Tiles.*;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class GameBoard{ //extends Application {
    private BorderPane borderPane;
    private Tile[][] tiles;
    private ImageView[] imageViews;

    public GameBoard(){
        BackgroundImage backgroundImage = new BackgroundImage(new Image("Background.jpg"), BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        Button startButton = new Button("Start");
        StackPane startPane = new StackPane();
        startPane.getChildren().add(startButton);
        startPane.setBackground(new Background(backgroundImage));
        Scene startScene = new Scene(startPane,850,780);
        Stage startStage = new Stage();

        startStage.setScene(startScene);
        startStage.setTitle("PipeBallMaze");
        startStage.show();
        Stage primaryStage = new Stage();

        startButton.setOnMouseClicked(event -> {
            startStage.hide();
            primaryStage.show();
        });
        File folder = new File("src/Levels");
        ArrayList<File> levels = new ArrayList<>();
        for (int l = 0; l < folder.list().length; l++) {
            String levelNo = l + 1 + "";
            File file = new File("src/Levels/level" + levelNo + ".txt");
            levels.add(file);
        }

        Scanner input;
        try {
            input = new Scanner(levels.get(3));
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

            if (words.get(k + 1).equals("Starter")) {
                tiles[queue / 4][queue % 4] = new StartPipe(words.get(k + 2));
            }

            if (words.get(k + 1).equals("Empty")) {
                if (words.get(k + 2).equals("Free")) {
                    tiles[queue / 4][queue % 4] = new EmptyFree();
                }

                if (words.get(k + 2).equals("none")) {
                    tiles[queue / 4][queue % 4] = new Empty();
                }
            }

            if (words.get(k + 1).equals("Pipe")) {
                if (words.get(k + 2).equals("Vertical") || words.get(k + 2).equals("Horizontal")) {
                    tiles[queue / 4][queue % 4] = new LinearPipe(words.get(k + 2));
                } else if (words.get(k + 2).equals("00") || words.get(k + 2).equals("01") || words.get(k + 2).equals("10") || words.get(k + 2).equals("11")) {
                    tiles[queue / 4][queue % 4] = new CurvedPipeMovable(words.get(k + 2));
                }
            }

            if (words.get(k + 1).equals("PipeStatic")) {
                if (words.get(k + 2).equals("Vertical") || words.get(k + 2).equals("Horizontal")) {
                    tiles[queue / 4][queue % 4] = new NormalPipeStatic(words.get(k + 2));
                } else if (words.get(k + 2).equals("00") || words.get(k + 2).equals("01") || words.get(k + 2).equals("10") || words.get(k + 2).equals("11")) {
                    tiles[queue / 4][queue % 4] = new CurvedPipeStatic(words.get(k + 2));
                }

            }

            if (words.get(k + 1).equals("End")) {
                tiles[queue / 4][queue % 4] = new EndPipe(words.get(k + 2));
            }
        }


        BorderPane borderPane = new BorderPane();
        Pane pane = new Pane();
        pane.setBackground(new Background(new BackgroundImage(new Image("Background.jpg"), BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT)));
        borderPane.setRight(new EdgePane(new Label("Right"))); // delete later


        Button checkButton = new Button("Check !");
        borderPane.setBottom(new EdgePane(checkButton));

        borderPane.setLeft(new EdgePane(new Label("Left"))); // delete later

        borderPane.setCenter(pane);
        setBorderPane(borderPane);

        final double sizeOfTile = 140;


        ImageView imageView1 = new ImageView(tiles[0][0].getImage());
        imageView1.setX(0);
        imageView1.setY(0);
        imageView1.setFitHeight(sizeOfTile);
        imageView1.setFitWidth(sizeOfTile);

        ImageView imageView2 = new ImageView(tiles[0][1].getImage());
        imageView2.setX(143);
        imageView2.setY(0);
        imageView2.setFitHeight(sizeOfTile);
        imageView2.setFitWidth(sizeOfTile);

        ImageView imageView3 = new ImageView(tiles[0][2].getImage());
        imageView3.setX(286);
        imageView3.setY(0);
        imageView3.setFitHeight(sizeOfTile);
        imageView3.setFitWidth(sizeOfTile);

        ImageView imageView4 = new ImageView(tiles[0][3].getImage());
        imageView4.setX(429);
        imageView4.setY(0);
        imageView4.setFitHeight(sizeOfTile);
        imageView4.setFitWidth(sizeOfTile);

        ImageView imageView5 = new ImageView(tiles[1][0].getImage());
        imageView5.setX(0);
        imageView5.setY(143);
        imageView5.setFitHeight(sizeOfTile);
        imageView5.setFitWidth(sizeOfTile);

        ImageView imageView6 = new ImageView(tiles[1][1].getImage());
        imageView6.setX(143);
        imageView6.setY(143);
        imageView6.setFitHeight(sizeOfTile);
        imageView6.setFitWidth(sizeOfTile);

        ImageView imageView7 = new ImageView(tiles[1][2].getImage());
        imageView7.setX(286);
        imageView7.setY(143);
        imageView7.setFitHeight(sizeOfTile);
        imageView7.setFitWidth(sizeOfTile);

        ImageView imageView8 = new ImageView(tiles[1][3].getImage());
        imageView8.setX(429);
        imageView8.setY(143);
        imageView8.setFitHeight(sizeOfTile);
        imageView8.setFitWidth(sizeOfTile);

        ImageView imageView9 = new ImageView(tiles[2][0].getImage());
        imageView9.setX(0);
        imageView9.setY(286);
        imageView9.setFitHeight(sizeOfTile);
        imageView9.setFitWidth(sizeOfTile);

        ImageView imageView10 = new ImageView(tiles[2][1].getImage());
        imageView10.setX(143);
        imageView10.setY(286);
        imageView10.setFitHeight(sizeOfTile);
        imageView10.setFitWidth(sizeOfTile);

        ImageView imageView11 = new ImageView(tiles[2][2].getImage());
        imageView11.setX(286);
        imageView11.setY(286);
        imageView11.setFitHeight(sizeOfTile);
        imageView11.setFitWidth(sizeOfTile);

        ImageView imageView12 = new ImageView(tiles[2][3].getImage());
        imageView12.setX(429);
        imageView12.setY(286);
        imageView12.setFitHeight(sizeOfTile);
        imageView12.setFitWidth(sizeOfTile);

        ImageView imageView13 = new ImageView(tiles[3][0].getImage());
        imageView13.setX(0);
        imageView13.setY(429);
        imageView13.setFitHeight(sizeOfTile);
        imageView13.setFitWidth(sizeOfTile);

        ImageView imageView14 = new ImageView(tiles[3][1].getImage());
        imageView14.setX(143);
        imageView14.setY(429);
        imageView14.setFitHeight(sizeOfTile);
        imageView14.setFitWidth(sizeOfTile);

        ImageView imageView15 = new ImageView(tiles[3][2].getImage());
        imageView15.setX(286);
        imageView15.setY(429);
        imageView15.setFitHeight(sizeOfTile);
        imageView15.setFitWidth(sizeOfTile);

        ImageView imageView16 = new ImageView(tiles[3][3].getImage());
        imageView16.setX(429);
        imageView16.setY(429);
        imageView16.setFitHeight(sizeOfTile);
        imageView16.setFitWidth(sizeOfTile);


        pane.getChildren().addAll(imageView1, imageView2, imageView3, imageView4,
                imageView5, imageView6, imageView7, imageView8, imageView9, imageView10,
                imageView11, imageView12, imageView13, imageView14, imageView15, imageView16);

        ImageView[] imageViews = {imageView1, imageView2, imageView3, imageView4,
                imageView5, imageView6, imageView7, imageView8, imageView9, imageView10,
                imageView11, imageView12, imageView13, imageView14, imageView15, imageView16};
        setImageViews(imageViews);
        setTiles(tiles);
        Scene scene = new Scene(borderPane, 950, 780);
        primaryStage.setTitle("PipeBallMaze");
        primaryStage.setScene(scene);
        //primaryStage.show();

    }
    public BorderPane getBorderPane() {
        return borderPane;
    }

    public void setBorderPane(BorderPane borderPane) {
        this.borderPane = borderPane;
    }

    //public static void main(String[] args) {launch(args);}

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

}
class EdgePane extends StackPane {
    private Button button;
    public EdgePane(Button button){

        getChildren().add(button);
        setButton(button);
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
    public Button getButton(){
        return button;
    }
    public void setButton(Button button){
        this.button = button;
    }

}