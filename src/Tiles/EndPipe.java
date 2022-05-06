package Tiles;
import javafx.scene.image.Image;
/* The EndPipe class is subclass of Tiles and represent end pipe tile.
 * Name Surname / Student ID: Beyza Nur Kaya / 150120077
 * Name Surname / Student ID: Sena Ektiricioğlu / 150120047
 */
public class EndPipe extends Tile implements Fixed {

    @Override
    public boolean isFixed() {
        return true;
    }

    public EndPipe(String status){
        if(status.equals("Vertical")) {
            setImage(new Image("Assets/EndVertical.png"));
            setStatus(status);
        }
        else if (status.equals("Horizontal")) {
            setImage(new Image("Assets/EndHorizontal.png"));
            setStatus(status);
        }
    }
}
