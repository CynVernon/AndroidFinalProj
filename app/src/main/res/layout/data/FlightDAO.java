package layout.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface FlightDAO {
    @Insert
    long insertFlight(Flight f);
    @Query("SELECT * FROM flight")
    List<Flight> getAllFlights();
    @Delete
    void deleteFlight(Flight f);
}
