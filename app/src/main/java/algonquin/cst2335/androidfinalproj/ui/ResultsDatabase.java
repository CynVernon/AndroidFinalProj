package algonquin.cst2335.androidfinalproj.ui;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Result.class}, version = 1)
public abstract class ResultsDatabase extends RoomDatabase {

    public abstract ResultDAO rDAO();

}
