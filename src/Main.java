import Tiles.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {
/*
        File folder = new File("Levels");
        for (int l = 0; l < folder.list().length; l++) {
            String levelNo = l + 1 + "";
            File file = new File("Levels\\level" + levelNo);

 */
        File file = new File("input.txt");
            Scanner input = new Scanner(file);
            ArrayList<String> lines = new ArrayList<>();
            while (input.hasNext()) {
                lines.add(input.next());
            }
            ArrayList<String> words = new ArrayList<>();
            for (int i = 0; i < lines.size(); i++) {
                String[] splitLine = lines.get(i).split(",");
                for (int j = 0; j < splitLine.length; j++) {
                    words.add(splitLine[j]);
                }
            }

            Tile[][] tiles = new Tile[4][4];
            for (int k = 0; k < words.size(); k += 3) {

                int queue = Integer.parseInt(words.get(k)) - 1;

                if (words.get(k + 1).equals("Starter")) {
                    tiles[queue / 4][queue % 4] = new StartPipe(words.get(k + 2));
                }

                if (words.get(k + 1).equals("Empty")) {
                    if (words.get(k + 2).equals("Free")) {
                        tiles[queue / 4][queue % 4] = new EmptyUnmovable();
                    }

                    if (words.get(k + 2).equals("none")) {
                        tiles[queue / 4][queue % 4] = new EmptyMovable();
                    }
                }

                if (words.get(k + 1).equals("Pipe")) {
                    if (words.get(k+2).equals("Vertical") || words.get(k+2).equals("Horizontal")) {
                        tiles[queue / 4][queue % 4] = new LinearPipe(words.get(k + 2));
                    }
                    else if(words.get(k+2).equals("00") ||words.get(k+2).equals("01") ||words.get(k+2).equals("10") ||words.get(k+2).equals("11")){
                        tiles[queue / 4][queue % 4] = new CurvedPipeMovable(words.get(k + 2));
                    }
                }

                if (words.get(k + 1).equals("PipeStatic")) {
                    if (words.get(k+2).equals("Vertical") || words.get(k+2).equals("Horizontal")) {
                        tiles[queue / 4][queue % 4] = new NormalPipeStatic(words.get(k + 2));
                    }
                    else if(words.get(k+2).equals("00") ||words.get(k+2).equals("01") ||words.get(k+2).equals("10") ||words.get(k+2).equals("11")){
                        tiles[queue / 4][queue % 4] = new CurvedPipeStatic(words.get(k + 2));
                    }
                }

                if (words.get(k + 1).equals("End")) {
                    tiles[queue / 4][queue % 4] = new EndPipe(words.get(k + 2));
                }
            }


            // Pane Layout

        BorderPane borderPane = new BorderPane();
        Pane pane = new Pane();
        borderPane.setTop(new EdgePane("Top"));
        borderPane.setRight(new EdgePane("Right"));
        borderPane.setBottom(new EdgePane("Bottom"));
        borderPane.setLeft(new EdgePane("Left"));
        borderPane.setCenter(pane);


        ImageView imageView1 = new ImageView(tiles[0][0].getImage());
        imageView1.setX(0);
        imageView1.setY(0);
        imageView1.setFitHeight(175);
        imageView1.setFitWidth(175);

        ImageView imageView2 = new ImageView(tiles[0][1].getImage());
        imageView2.setX(180);
        imageView2.setY(0);
        imageView2.setFitHeight(175);
        imageView2.setFitWidth(175);

        ImageView imageView3 = new ImageView(tiles[0][2].getImage());
        imageView3.setX(360);
        imageView3.setY(0);
        imageView3.setFitHeight(175);
        imageView3.setFitWidth(175);

        ImageView imageView4 = new ImageView(tiles[0][3].getImage());
        imageView4.setX(540);
        imageView4.setY(0);
        imageView4.setFitHeight(175);
        imageView4.setFitWidth(175);

        ImageView imageView5 = new ImageView(tiles[1][0].getImage());
        imageView5.setX(0);
        imageView5.setY(180);
        imageView5.setFitHeight(175);
        imageView5.setFitWidth(175);

        ImageView imageView6 = new ImageView(tiles[1][1].getImage());
        imageView6.setX(180);
        imageView6.setY(180);
        imageView6.setFitHeight(175);
        imageView6.setFitWidth(175);

        ImageView imageView7 = new ImageView(tiles[1][2].getImage());
        imageView7.setX(360);
        imageView7.setY(180);
        imageView7.setFitHeight(175);
        imageView7.setFitWidth(175);

        ImageView imageView8 = new ImageView(tiles[1][3].getImage());
        imageView8.setX(540);
        imageView8.setY(180);
        imageView8.setFitHeight(175);
        imageView8.setFitWidth(175);

        ImageView imageView9 = new ImageView(tiles[2][0].getImage());
        imageView9.setX(0);
        imageView9.setY(360);
        imageView9.setFitHeight(175);
        imageView9.setFitWidth(175);

        ImageView imageView10 = new ImageView(tiles[2][1].getImage());
        imageView10.setX(180);
        imageView10.setY(360);
        imageView10.setFitHeight(175);
        imageView10.setFitWidth(175);

        ImageView imageView11 = new ImageView(tiles[2][2].getImage());
        imageView11.setX(360);
        imageView11.setY(360);
        imageView11.setFitHeight(175);
        imageView11.setFitWidth(175);

        ImageView imageView12 = new ImageView(tiles[2][3].getImage());
        imageView12.setX(540);
        imageView12.setY(360);
        imageView12.setFitHeight(175);
        imageView12.setFitWidth(175);

        ImageView imageView13 = new ImageView(tiles[3][0].getImage());
        imageView13.setX(0);
        imageView13.setY(540);
        imageView13.setFitHeight(175);
        imageView13.setFitWidth(175);

        ImageView imageView14 = new ImageView(tiles[3][1].getImage());
        imageView14.setX(180);
        imageView14.setY(540);
        imageView14.setFitHeight(175);
        imageView14.setFitWidth(175);

        ImageView imageView15 = new ImageView(tiles[3][2].getImage());
        imageView15.setX(360);
        imageView15.setY(540);
        imageView15.setFitHeight(175);
        imageView15.setFitWidth(175);

        ImageView imageView16 = new ImageView(tiles[3][3].getImage());
        imageView16.setX(540);
        imageView16.setY(540);
        imageView16.setFitHeight(175);
        imageView16.setFitWidth(175);


        pane.getChildren().addAll(imageView1, imageView2, imageView3,imageView4,
                imageView5, imageView6, imageView7, imageView8, imageView9, imageView10,
                imageView11, imageView12, imageView13, imageView14, imageView15, imageView16);


        Scene scene = new Scene(borderPane, 930,850);
        primaryStage.setTitle("PipeBallMaze");
        primaryStage.setScene(scene);
        primaryStage.show();

         /*   Object ob = new Object();
            Pane pane = new StackPane();
            //pane.getChildren().add(imageView);

        /*
            Scene scene = new Scene(pane, 500, 500);
            primaryStage.setScene(scene);
            */

        }
    }

class EdgePane extends StackPane {
    public EdgePane(String title) {
        getChildren().add(new Label(title));
        setStyle("-fx-border-color: darkblue");
        setPadding(new Insets(22.5, 57.5, 24.5, 22.5));
    }
}

