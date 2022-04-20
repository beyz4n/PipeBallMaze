package Tiles;

import javafx.scene.image.Image;

public class LinearPipe extends Tile implements Movable {
    public LinearPipe(String status){
        if(status.equals("Vertical"))
            setImage(new Image("Pipe_Vertical.png"));
        else if (status.equals("Horizontal"))
            setImage(new Image("Pipe_Horizontal.png"));
    }

    @Override
    public boolean isMovable() {
        return true;
    }
}
