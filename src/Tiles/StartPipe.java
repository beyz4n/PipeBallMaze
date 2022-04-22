package Tiles;

import javafx.scene.image.Image;

public class StartPipe extends PipeStatic implements Fixed {

    public StartPipe(String status){
        if(status.equals("Vertical")) {
            setImage(new Image("StarterVertical.jpg"));
            setStatus(status);
        }
        else if(status.equals("Horizontal")) {
            setImage(new Image("StarterHorizontal.jpg"));
            setStatus(status);
        }
    }

}
