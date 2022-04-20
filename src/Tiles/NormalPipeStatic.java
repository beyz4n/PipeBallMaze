package Tiles;

import javafx.scene.image.Image;

public class NormalPipeStatic extends PipeStatic implements Fixed {
    public NormalPipeStatic (String status){
        if(status.equals("Vertical"))
            setImage(new Image("PipeStatic_Vertical.png"));
        else if(status.equals("Horizontal"))
            setImage(new Image("PipeStatic_Horizontal.png"));
    }
}
