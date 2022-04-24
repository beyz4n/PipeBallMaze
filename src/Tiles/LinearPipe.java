package Tiles;

import javafx.scene.image.Image;

public class LinearPipe extends Tile implements Movable {
    private double startPointX;
    private double startPointY;
    private double endPointX;
    private double endPointY;
    public LinearPipe(String status){
        if(status.equals("Vertical")) {
            setImage(new Image("VerticalPipe.png"));
            setStatus(status);
        }
        else if (status.equals("Horizontal")){
            setImage(new Image("HorizontalPipe.png"));
            setStatus(status);
        }

    }

    @Override
    public boolean isMovable() {
        return true;
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
