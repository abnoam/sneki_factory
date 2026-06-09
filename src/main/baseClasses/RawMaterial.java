package main.baseClasses;

import java.util.Date;

public abstract class RawMaterial implements Valuable
{
    private int serialNumber;
    private double quantityInStock;
    private Date expirationDate;
    private double purchasePrice;


    public RawMaterial(int serialNumber, double quantityInStock,Date expirationDate, double purchasePrice)      //full constructor
    {
        this.serialNumber = serialNumber;
        this.quantityInStock = quantityInStock;
        this.expirationDate = expirationDate;
        this.purchasePrice = purchasePrice;
    }

    public boolean useMaterial(double amount)        //uses the material and updates the remaining quantity
    {
        if (amount <= 0)
        {
            System.out.println("Amount must be positive");
            return false;
        }

        if (amount > quantityInStock)
        {
            System.out.println("Not enough material in stock");
            return false;
        }

        setQuantityInStock(getQuantityInStock() - amount);
        return true;
    }


    public double checkStock()          //checks stock
    {
        return quantityInStock;
    }

    public boolean isExpired()      //checks expiration date
    {
        Date currentDate = new Date();
        return expirationDate.before(currentDate);
    }

    //implementing valuable functions
     //* שווי חומר הגלם = מחיר רכש * כמות במלאי

    public double calcFinalValue()      //calculate price of ALL units in stock
    {
        return (purchasePrice * quantityInStock) * (1 + TAX_PERCENT);
    }

    public double getBasePrice()        //returns price per unit
    {
        return purchasePrice;
    }

    public double calcProfit(double targetProfitPercentage) //calculates the desirable profit
    {
        return calcFinalValue() * (targetProfitPercentage / 100.0);
    }

    // Getters & Setters

    public int getSerialNumber()
    {
        return serialNumber;
    }

    public void setSerialNumber(int serialNumber)
    {
        this.serialNumber = serialNumber;
    }

    public double getQuantityInStock()
    {
        return quantityInStock;
    }

    public void setQuantityInStock(double quantityInStock)
    {
        this.quantityInStock = quantityInStock;
    }

    public Date getExpirationDate()
    {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate)
    {
        this.expirationDate = expirationDate;
    }

    public double getPurchasePrice()
    {
        return purchasePrice;
    }

    public void setPurchasePrice(double purchasePrice)
    {
        this.purchasePrice = purchasePrice;
    }

    public int getTreeID()
    {
        return this.getSerialNumber();
    }

    public String toString()
    {
        return "main.baseClasses.RawMaterial{" +
                "serialNumber=" + serialNumber +
                ", quantityInStock=" + quantityInStock +
                ", expirationDate=" + expirationDate +
                ", purchasePrice=" + purchasePrice +
                ", expired=" + isExpired() +
                '}';
    }
}