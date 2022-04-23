package Tiles;

import javafx.scene.image.Image;

public class EndPipe extends PipeStatic implements Fixed {

    public EndPipe(String status){
        if(status.equals("Vertical")) {
            setImage(new Image("EndVertical.png"));
            setStatus(status);
        }
        else if (status.equals("Horizontal")) {
            setImage(new Image("EndHorizontal.png"));
            setStatus(status);
        }
    }


}
