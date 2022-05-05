package Tiles;

import javafx.scene.image.Image;

public class NormalPipeStatic extends Tile implements Fixed {
    @Override
    public boolean isFixed() {
        return true;
    }


    public NormalPipeStatic (String status){
        if(status.equals("Vertical")) {
            setImage(new Image("Assets/VerticalPipeStatic.png"));
            setStatus(status);
        }
        else if(status.equals("Horizontal")) {
            setImage(new Image("Assets/HorizontalPipeStatic.png"));
            setStatus(status);
        }
    }

}
