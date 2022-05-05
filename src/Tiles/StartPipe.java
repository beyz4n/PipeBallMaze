package Tiles;

import javafx.scene.image.Image;

public class StartPipe extends Tile implements Fixed {

    @Override
    public boolean isFixed() {
        return true;
    }


    public StartPipe(String status){
        if(status.equals("Vertical")) {
            setImage(new Image("Assets/StarterVertical.png"));
            setStatus(status);
        }
        else if(status.equals("Horizontal")) {
            setImage(new Image("Assets/StarterHorizontal.png"));
            setStatus(status);
        }
    }

}
