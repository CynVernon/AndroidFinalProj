package algonquin.cst2335.androidfinalproj.currencyconverter.ui;

import androidx.room.Database;
import androidx.room.RoomDatabase;

/**
 * An abstract class that holds the database.
 */
@Database(entities = {Result.class}, version = 1)
public abstract class ResultsDatabase extends RoomDatabase {

    /**
     * Abstract method that creates a new ResultDao object.
     * @return
     */
    public abstract ResultDAO rDAO();

}
