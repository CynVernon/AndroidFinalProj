package algonquin.cst2335.androidfinalproj.aviationtracker.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface FlightDAO {

    @Query("SELECT * FROM flight") //TODO add "WHERE airport = XXX" or whatever
    List<Flight> getAllFlights();
}
