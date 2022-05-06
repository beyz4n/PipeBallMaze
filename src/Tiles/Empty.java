package Tiles;
import javafx.scene.image.Image;
/* The Empty class is subclass of Tiles and represent empty tile.
 * Name Surname / Student ID: Beyza Nur Kaya / 150120077
 * Name Surname / Student ID: Sena Ektiricioğlu / 150120047
 */
public class Empty extends Tile implements Movable {

    @Override
    public boolean isMovable() {
        return true;
    }
    public Empty(){
        setImage(new Image("Assets/Empty.png"));
        setStatus("Empty");
}


}
