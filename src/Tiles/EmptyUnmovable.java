package Tiles;

import javafx.scene.image.Image;

public class EmptyUnmovable extends Tile implements Fixed {
    @Override
    public boolean isFixed() {
        return true;
    }
    public EmptyUnmovable(){
        setImage(new Image("empty.png"));
}


}
