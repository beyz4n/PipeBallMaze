package Tiles;
import javafx.scene.image.Image;
/* The CurvedPipeMovable class is subclass of Tiles and represent start movable curved pipe tile.
 * Name Surname / Student ID: Beyza Nur Kaya / 150120077
 * Name Surname / Student ID: Sena Ektiricioğlu / 150120047
 */
public class CurvedPipeMovable extends Tile implements Movable {

    public CurvedPipeMovable(String status) {
        // Set tiles' images
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
    public boolean isMovable() {
        return true;
    }

}


