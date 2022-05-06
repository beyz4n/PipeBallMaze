package Tiles;
import javafx.scene.image.Image;
/* The NormalPipeStatic class is subclass of Tiles and represent pipe statc tiles.
 * Name Surname / Student ID: Beyza Nur Kaya / 150120077
 * Name Surname / Student ID: Sena Ektiricioğlu / 150120047
 */
public class NormalPipeStatic extends Tile implements Fixed {
    @Override
    public boolean isFixed() {
        return true;
    }

    public NormalPipeStatic (String status){
        if(status.equals("Vertical")) {
            setImage(new Image("Assets/VerticalPipeStatic.png"));
            setStatus(status);
        }
        else if(status.equals("Horizontal")) {
            setImage(new Image("Assets/HorizontalPipeStatic.png"));
            setStatus(status);
        }
    }
}
