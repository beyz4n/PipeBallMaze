package Tiles;

import javafx.scene.image.Image;

public class LinearPipe extends Tile implements Movable {
    public LinearPipe(String status){
        if(status.equals("Vertical"))
            setImage(new Image("VerticalPipe.png"));
        else if (status.equals("Horizontal"))
            setImage(new Image("HorizontalPipe.png"));
    }

    @Override
    public boolean isMovable() {
        return true;
    }
}
