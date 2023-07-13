package algonquin.cst2335.androidfinalproj.ui;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ResultDAO {

    @Insert
    public long insertResult(Result r);

    @Query("Select * from Result")
    public List<Result> getAllResults();

    @Delete
    void deleteMessage(Result r);
}
