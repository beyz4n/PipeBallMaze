package Tiles;
import javafx.scene.image.Image;

public class CurvedPipeMovable extends Tile implements Movable {
    String status;
    public CurvedPipeMovable(String status) {
        switch (status) {
            case "00":
                setImage(new Image("Assets/Curve3.png"));
                setStatus(status);
                break;
            case "01":
            setImage(new Image("Assets/Curve4.png"));
                setStatus(status);
            break;
            case "10":
                setImage(new Image("Assets/Curve2.png"));
                setStatus(status);
                break;
            case "11":
                setImage(new Image("Assets/Curve1.png"));
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


    @Override
    public boolean isMovable() {
        return true;
    }


}


