package Tiles;

import javafx.scene.image.Image;

public class CurvedPipeMovable extends Tile implements Movable {
    String status;
    public CurvedPipeMovable(String status) {
        switch (status) {
            case "00":
                setImage(new Image("CurvedPipe_00.png"));
                setStatus(status);
                break;
            case "01":
            setImage(new Image("CurvedPipe_01.png"));
                setStatus(status);
            break;
            case "10":
                setImage(new Image("CurvedPipe_10.png"));
                setStatus(status);
                break;
            case "11":
                setImage(new Image("CurvedPipe_11.png"));
                setStatus(status);
                break;
        }
    }

    @Override
    public boolean isMovable() {
        return true;
    }


    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

}


