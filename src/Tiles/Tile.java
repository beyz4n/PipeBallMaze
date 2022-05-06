package Tiles;
import javafx.scene.image.Image;

/* The abstract Tile class is the superclass of all other tile types.
 * Each tile has image and status property.
 * Name Surname / Student ID: Beyza Nur Kaya / 150120077
 * Name Surname / Student ID: Sena Ektiricioğlu / 150120047
 */

public abstract class Tile {
   private Image image; // image data field represent image of this tile.
   private String status; // status data field represent status, which is vertical, horizontal, 00, 01, 10, 11, corresponding to their direction.

   public String getStatus() {
      return status;
   }

   public void setStatus(String attribute) {
      this.status = attribute;
   }

   public void setImage(Image image) {
      this.image = image;
   }

   public Image getImage() {
      return image;
   }

}
