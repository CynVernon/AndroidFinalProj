package algonquin.cst2335.androidfinalproj.currencyconverter.ui;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Result {


    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="Id")
    public int id;

    @ColumnInfo(name="Amount")
    String amount;

    @ColumnInfo(name="Currency")
    String currency;

    @ColumnInfo(name="NewAmount")
    String newAmount;

    @ColumnInfo(name="NewCurrency")
    String newCurrency;


    public Result(String amount, String currency, String newAmount, String newCurrency )
    {
        this.amount = amount;
        this.currency = currency;
        this.newAmount = newAmount;
        this.newCurrency = newCurrency;
    }
    public Result(){};

    //getter for amount
    public String getAmount(){
        return amount;
    }

    //getter for currency
    public String getCurrency(){
        return currency;
    }

    //getter for new amount
    public String getNewAmount(){ return newAmount; }

    //getter for new currency
    public String getNewCurrency(){ return newCurrency; }
}
