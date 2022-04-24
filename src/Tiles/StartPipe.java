package Tiles;

import javafx.scene.image.Image;

public class StartPipe extends PipeStatic implements Fixed {
    private double startPointX;
    private double startPointY;
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

    public double getStartPointX() {
        return startPointX;
    }

    public void setStartPointX(double startPointX) {
        this.startPointX = startPointX;
    }

    public double getStartPointY() {
        return startPointY;
    }

    public void setStartPointY(double startPointY) {
        this.startPointY = startPointY;
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
