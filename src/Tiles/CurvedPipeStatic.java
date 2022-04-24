package Tiles;

import javafx.scene.image.Image;

public class CurvedPipeStatic extends Tile implements Fixed {
    String status;
    private double startPointX;
    private double startPointY;
    private double endPointX;
    private double endPointY;
    public CurvedPipeStatic(String status) {
        switch (status) {
            case "00":
                setImage(new Image("Curve3Static.png"));
                setStatus(status);
                break;
            case "01":
                setImage(new Image("Curve4Static.png"));
                setStatus(status);
                break;
            case "10":
                setImage(new Image("Curve2Static.png"));
                setStatus(status);
                break;
            case "11":
                setImage(new Image("Curve1Static.png"));
                setStatus(status);
                break;
        }
    }

    @Override
    public String getStatus() {
        return status;
    }

    @Override
    public void setStatus(String status) {
        this.status = status;
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



    @Override
    public boolean isFixed() {
        return true;
    }


}