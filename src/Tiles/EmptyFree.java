package Tiles;
import javafx.scene.image.Image;
/** The EmptyFree class is subclass of Tiles and represent empty free tile.
 * Name Surname / Student ID: Beyza Nur Kaya / 150120077
 * Name Surname / Student ID: Sena Ektiricioğlu / 150120047
 */
public class EmptyFree extends Tile implements Fixed {

    public EmptyFree(){
        // Set tiles' images
        setImage(new Image("Assets/EmptyFree.png"));
        setStatus("EmptyFree");
    }

    @Override
    public boolean isFixed() {
        return false;
    }
}
