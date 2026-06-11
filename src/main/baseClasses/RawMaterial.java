package main.baseClasses;

import java.time.LocalDateTime;

public abstract class RawMaterial implements Valuable
{
    private String name;
    private int serialNumber;
    private double quantityInStock;
    private LocalDateTime expirationDate;
    private double purchasePrice;


    public RawMaterial(String name, int serialNumber, double quantityInStock,LocalDateTime expirationDate, double purchasePrice)      //full constructor
    {
        this.name = name;
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
        return expirationDate.isBefore(LocalDateTime.now());
    }

    //implementing valuable functions
     //* שווי חומר הגלם = מחיר רכש * כמות במלאי

    public double calcFinalValue()      //calculate price of ALL units in stock
    {
        return (purchasePrice * quantityInStock);
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
    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name=name;
    }

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

    public LocalDateTime getExpirationDate()
    {
        return expirationDate;
    }

    public void setExpirationDate(LocalDateTime expirationDate)
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
        LocalDateTime date = this.getExpirationDate();
        return
                "name: " + name +
                " | serialNumber: " + serialNumber +
                " | quantityInStock: " + quantityInStock +
                " | expirationDate: " + date.getDayOfMonth() + "/"
                                    + date.getMonthValue() + "/"
                                    + date.getYear() + " "
                                    + date.getHour() + ":"
                                    + date.getMinute() +
                " | purchasePrice: " + purchasePrice +
                " | expired: " + isExpired();
    }
}