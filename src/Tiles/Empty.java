package Tiles;

import javafx.scene.image.Image;

public class Empty extends Tile implements Movable {
    @Override
    public boolean isMovable() {
        return true;
    }
    public Empty(){
        setImage(new Image("empty.png"));
}


}
