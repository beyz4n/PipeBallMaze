package Tiles;
import javafx.scene.image.Image;

public class EndPipe extends Tile implements Fixed {

    @Override
    public boolean isFixed() {
        return true;
    }

    public EndPipe(String status){
        if(status.equals("Vertical")) {
            setImage(new Image("Assets/EndVertical.png"));
            setStatus(status);
        }
        else if (status.equals("Horizontal")) {
            setImage(new Image("Assets/EndHorizontal.png"));
            setStatus(status);
        }
    }
}
