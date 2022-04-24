package Tiles;
//
import javafx.scene.image.Image;

public class EndPipe extends PipeStatic implements Fixed {
    private double startPointX;
    private double startPointY;


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
    public double getStartPointX() {
        return startPointX;
    }

    public void setStartPointX(double startPointX) {
        this.startPointX = startPointX;
    }



}
