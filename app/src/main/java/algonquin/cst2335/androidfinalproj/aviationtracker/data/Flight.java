package algonquin.cst2335.androidfinalproj.aviationtracker.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Flight {
    @PrimaryKey
    @ColumnInfo(name = "Id")
    protected int id;

    @ColumnInfo(name = "Destination")
    protected String destination;

    @ColumnInfo(name = "Terminal")
    protected String terminal;

    @ColumnInfo(name = "Gate")
    protected String gate;

    @ColumnInfo(name = "Delay")
    protected String delay; //TODO: Should this be a boolean?

    public Flight() {
    }

    public Flight(String des, String t, String g, String del) {
        destination = des;
        terminal = t;
        gate = g;
        delay = del;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getTerminal() {
        return terminal;
    }

    public void setTerminal(String terminal) {
        this.terminal = terminal;
    }

    public String getGate() {
        return gate;
    }

    public void setGate(String gate) {
        this.gate = gate;
    }

    public String getDelay() {
        return delay;
    }

    public void setDelay(String delay) {
        this.delay = delay;
    }
}
