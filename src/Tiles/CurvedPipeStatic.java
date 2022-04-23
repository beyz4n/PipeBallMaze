package Tiles;

import javafx.scene.image.Image;

public class CurvedPipeStatic extends Tile implements Fixed {
    String status;
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
    public boolean isFixed() {
        return true;
    }


    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

}