package algonquin.cst2335.androidfinalproj.currencyconverter.ui;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

/**
 * This interface class contains the DAO object for the database. It contains three methods to do CRUD operations, insert, query and delete.
 */
@Dao
public interface ResultDAO {

    /**
     * Abstract method that will insert an item into the database.
     * @param r A result object
     * @return long value
     */
    @Insert
    public long insertResult(Result r);

    /**
     * Abstract method to select all results from the database and return it as a list object.
     * @return A list of results
     */
    @Query("Select * from Result")
    public List<Result> getAllResults();

    /**
     * Abstract method to delete an item from the database.
     * @param r A result object
     */
    @Delete
    void deleteMessage(Result r);
}
