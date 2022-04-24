package Tiles;

import javafx.scene.image.Image;

public class NormalPipeStatic extends PipeStatic implements Fixed {
    private double startPointX;
    private double startPointY;
    private double endPointX;
    private double endPointY;
    public NormalPipeStatic (String status){
        if(status.equals("Vertical")) {
            setImage(new Image("VerticalPipeStatic.png"));
            setStatus(status);
        }
        else if(status.equals("Horizontal")) {
            setImage(new Image("HorizontalPipeStatic.png"));
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
