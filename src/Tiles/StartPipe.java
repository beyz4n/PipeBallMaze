package Tiles;
import javafx.scene.image.Image;
/* The StartPipe class is subclass of Tiles and represent start pipe tile.
 * Name Surname / Student ID: Beyza Nur Kaya / 150120077
 * Name Surname / Student ID: Sena Ektiricioğlu / 150120047
 */
public class StartPipe extends Tile implements Fixed {

    @Override
    public boolean isFixed() {
        return true;
    }


    public StartPipe(String status){
        // Set tiles' images
        if(status.equals("Vertical")) {
            setImage(new Image("Assets/StarterVertical.png"));
            setStatus(status);
        }
        else if(status.equals("Horizontal")) {
            setImage(new Image("Assets/StarterHorizontal.png"));
            setStatus(status);
        }
    }

}
