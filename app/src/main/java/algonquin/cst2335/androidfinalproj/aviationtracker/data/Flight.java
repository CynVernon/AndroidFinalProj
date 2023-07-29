package algonquin.cst2335.androidfinalproj.aviationtracker.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Flight {
    @PrimaryKey
    @ColumnInfo(name = "Flight No.") //TODO: Will I even use the primary key?
    protected int primary;

    @ColumnInfo(name = "Destination")
    protected String destination;

    @ColumnInfo(name = "Terminal")
    protected String terminal;

    @ColumnInfo(name = "Gate")
    protected String gate;

    @ColumnInfo(name = "Delay")
    protected String delay; //TODO: Should this be a boolean?

    public Flight(String des, String t, String g, String del) {
        destination = des;
        terminal = t;
        gate = g;
        delay = del;
    }

    public Flight(){}

    public String getDestination() {
        return destination;
    }

    public String getTerminal() {
        return terminal;
    }

    public String getGate() {
        return gate;
    }

    public String getDelay() {
        return delay;
    }
}
