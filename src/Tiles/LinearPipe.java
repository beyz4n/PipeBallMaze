package Tiles;
import javafx.scene.image.Image;
/* The LinearPipe class is subclass of Tiles and represent linear pipe tile.
 * Name Surname / Student ID: Beyza Nur Kaya / 150120077
 * Name Surname / Student ID: Sena Ektiricioğlu / 150120047
 */
public class LinearPipe extends Tile implements Movable {

    public LinearPipe(String status){
        // Set tiles' images
        if(status.equals("Vertical")) {
            setImage(new Image("Assets/VerticalPipe.png"));
            setStatus(status);
        }
        else if (status.equals("Horizontal")){
            setImage(new Image("Assets/HorizontalPipe.png"));
            setStatus(status);
        }

    }

    @Override
    public boolean isMovable() {
        return true;
    }


}
