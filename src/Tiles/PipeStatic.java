package Tiles;

public abstract class PipeStatic extends Tile implements Fixed {
    String status; // ilerde yönünü checklemek için kullanırız
    @Override
    public boolean isFixed() {
        return true;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
}
