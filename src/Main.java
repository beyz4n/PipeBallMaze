import Tiles.*;
import javafx.application.Application;
import javafx.scene.Scene;
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

            Object[][] objects = new Object[4][4];
            for (int k = 0; k < words.size(); k += 3) {

                int queue = Integer.parseInt(words.get(k)) - 1;

                if (words.get(k + 1).equals("Starter")) {
                    objects[queue / 4][queue % 4] = new StartPipe(words.get(k + 2));
                }

                if (words.get(k + 1).equals("Empty")) {
                    if (words.get(k + 2).equals("Free")) {
                        objects[queue / 4][queue % 4] = new EmptyUnmovable();
                    }

                    if (words.get(k + 2).equals("none")) {
                        objects[queue / 4][queue % 4] = new EmptyMovable();
                    }
                }

                if (words.get(k + 1).equals("Pipe")) {
                    objects[queue / 4][queue % 4] = new LinearPipe(words.get(k + 2));
                }

                if (words.get(k + 1).equals("PipeStatic")) {
                    objects[queue / 4][queue % 4] = new NormalPipeStatic(words.get(k + 2));
                }

                if (words.get(k + 1).equals("End")) {
                    objects[queue / 4][queue % 4] = new EndPipe(words.get(k + 2));
                }
            }
            Object ob = new Object();
            Pane pane = new StackPane();
            //pane.getChildren().add(imageView);
            Scene scene = new Scene(pane, 500, 500);
            primaryStage.setScene(scene);
            primaryStage.setTitle("PipeBallMaze");
            primaryStage.show();

        }
    }

