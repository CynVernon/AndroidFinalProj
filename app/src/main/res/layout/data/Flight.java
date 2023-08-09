package layout.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Flight {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") //TODO: Will I even use the primary key?
    public int id;

    @ColumnInfo(name = "Code")
    protected String code;

    @ColumnInfo(name = "Destination")
    protected String destination;

    @ColumnInfo(name = "Terminal")
    protected String terminal;

    @ColumnInfo(name = "Gate")
    protected String gate;

    @ColumnInfo(name = "Delay")
    protected String delay; //TODO: Should this be a boolean?


    public Flight(){}

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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
