package Tiles;

import javafx.scene.image.Image;

public class EmptyMovable extends Tile implements Movable {
    @Override
    public boolean isMovable() {
        return true;
    }
    public EmptyMovable(){
        setImage(new Image("empty.png"));
    }
}
