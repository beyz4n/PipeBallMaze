package Tiles;

import javafx.scene.image.Image;

public class StartPipe extends PipeStatic implements Fixed {
    private double endPointX;
    private double endPointY;

    public StartPipe(String status){
        if(status.equals("Vertical")) {
            setImage(new Image("StarterVertical.png"));
            setStatus(status);
        }
        else if(status.equals("Horizontal")) {
            setImage(new Image("StarterHorizontal.png"));
            setStatus(status);
        }
    }

    public double getEndPointX() {
        return endPointX;
    }

    public void setEndPointX(double endPointX) {
        this.endPointX = endPointX;
    }

    public double getEndPointY() {
        return endPointY;
    }

    public void setEndPointY(double endPointY) {
        this.endPointY = endPointY;
    }
}
