package algonquin.cst2335.androidfinalproj.aviationtracker.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Flight.class}, version=1)
public abstract class FlightDatabase extends RoomDatabase {
    public abstract FlightDAO fDAO();
}
