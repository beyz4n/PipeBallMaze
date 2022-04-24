package Tiles;

import javafx.scene.image.Image;


public abstract class Tile {
   // private String name;
   // private String location;
   private Image image;

  private String status;

   public String status() {
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

   /*
   public String getName() {
      return name;
   }
   public void setName(String name) {
      this.name = name;
   }

   public String getLocation() {
      return location;
   }

   public void setLocation(String location) {
      this.location = location;
   }
    */
   //(kullanmıyoruz diye name'le locationu commendledim(Sena)
}
