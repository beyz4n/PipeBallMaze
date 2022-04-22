package Tiles;

import javafx.scene.image.Image;

public class EmptyFree extends Tile implements Movable {
    @Override
    public boolean isMovable() {
        return true;
    }
    public EmptyFree(){
        setImage(new Image("empty.png"));
    }
}
