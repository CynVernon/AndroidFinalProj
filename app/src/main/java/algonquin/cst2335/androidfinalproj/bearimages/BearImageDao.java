// BearImageDao.java
package algonquin.cst2335.androidfinalproj.bearimages;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface BearImageDao {

    @Insert
    void insertBearImage(BearImageDatabase bearImage);

    @Delete
    void deleteBearImage(BearImageDatabase bearImage);

    @Query("SELECT * FROM bearimage")
    LiveData<List<BearImageDatabase>> getAllBearImages();
}
