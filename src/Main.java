import Tiles.EmptyMovable;
import Tiles.EmptyUnmovable;
import Tiles.StartPipe;
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
    public static void main (String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {

        File file = new File("input.txt");
        Scanner input = new Scanner(file);
        ArrayList<String> lines = new ArrayList<>();
        while(input.hasNext()){
            lines.add(input.next());
        }
        ArrayList<String> words = new ArrayList<>();
        for (int i = 0; i < lines.size(); i++){
            String[] splitLine = lines.get(i).split(",");
            for (int j = 0; j < splitLine.length; j++){
                words.add(splitLine[j]);
            }
        }
        // aaa
        // bbb
/*

        Object[][] objects = new Object[4][4];
        for (int k = 0; k < words.size(); k+=3){
            //these are too wrong, will be corrected later!
            if(words.get(k + 1).equals("Starter"))
            objects[k % 4][k/4] = new StartPipe(words.get(k +2));
            if(words.get(k + 1).equals("Empty")) {
                if (words.get(k + 2).equals("Free"))
                    objects[k % 4][k / 4] = new EmptyUnmovable();
                if (words.get(k + 2).equals("None"))
                    objects[k % 4][k / 4] = new EmptyMovable();
            }
            if(words.get(k + 1).equals("Pipe"))
                objects[k % 4][k/4] = new StartPipe(words.get(k +2));
            if(words.get(k + 1).equals("PipeStatic"))
                objects[k % 4][k/4] = new StartPipe(words.get(k +2));
            if(words.get(k + 1).equals("End"))
                objects[k % 4][k/4] = new StartPipe(words.get(k +2));
        }


        /*
        Tiles.PipeStatic pipe1 = new Tiles.PipeStatic();
        ImageView imageView = new ImageView(pipe1.getImage());

        CurvedPipe emp1 = new CurvedPipe("11");
        ImageView imageView = new ImageView(emp1.getImage());
         */
        Pane pane = new StackPane();
        //pane.getChildren().add(imageView);
        Scene scene = new Scene(pane, 500,500);
        primaryStage.setScene(scene);
        primaryStage.setTitle("abc");
        primaryStage.show();
        //as
        //added a new comment to test

    }
}
