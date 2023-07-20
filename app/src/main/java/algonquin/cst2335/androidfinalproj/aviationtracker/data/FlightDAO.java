package algonquin.cst2335.androidfinalproj.aviationtracker.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface FlightDAO {
    @Insert
    long insertFlight(Flight f); //TODO: Do I need to insert a flight?

    @Query("SELECT * FROM flight")
    List<Flight> getAllFlights();

    @Delete
    void deleteFlight(Flight f); //TODO: Do I need to delete flights?
}
