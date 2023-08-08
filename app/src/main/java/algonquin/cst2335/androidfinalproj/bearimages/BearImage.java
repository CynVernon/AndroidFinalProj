
package algonquin.cst2335.androidfinalproj.bearimages;// BearImage.java

import androidx.lifecycle.LiveData;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity(tableName = "BearImage")
public class BearImage {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "width")
    private int width;

    @ColumnInfo(name = "height")
    private int height;

    @ColumnInfo(name = "imageUrl")
    private String imageUrl;

    public BearImage (int width, int height, String imageUrl) {
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

    public int getHeight() {
        return height;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public LiveData<List<BearImageDatabase>> getAllBearImages() {
        return null;
    }

    public void insertBearImage(BearImageDatabase bearImage) {
    }

    public void deleteBearImage(BearImageDatabase bearImage) {
    }
}
