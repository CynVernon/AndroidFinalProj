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

    /**
     * The get method that returns the amount.
     * @return The amount converted
     */
    public String getAmount(){
        return amount;
    }

    /**
     * The get method that returns the old currency.
     * @return The old currency
     */
    public String getCurrency(){
        return currency;
    }

    /**
     * The get method that returns the new converted amount.
     * @return The converted amount
     */
    public String getNewAmount(){ return newAmount; }

    /**
     * The get method that returns the new currency.
     * @return The currency to convert into
     */
    public String getNewCurrency(){ return newCurrency; }
}
