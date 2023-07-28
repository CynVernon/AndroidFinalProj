// BearImage.java

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "bear_images")
public class BearImage {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private int width;
    private int height;
    private String imageUrl;

    public BearImage(int width, int height, String imageUrl) {
        this.width = width;
        this.height = height;
        this.imageUrl = imageUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
