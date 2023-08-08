// BearImageDatabase.java
package algonquin.cst2335.androidfinalproj.bearimages;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.Executor;

@Database(entities = {BearImageDatabase.class}, version = 1, exportSchema = false)
public abstract class BearImageDatabase extends RoomDatabase {

    public static Executor databaseWriteExecutor;

    public BearImageDatabase(int imageWidth, int imageHeight, String imageUrl) {
    }

    public abstract BearImageDao bearImageDao();

    private static BearImageDatabase instance;

    public static synchronized BearImageDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            BearImageDatabase.class, "bear_image_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
