package main.baseClasses;

import java.time.LocalDateTime;

public abstract class RawMaterial implements Valuable
{
    private String name;
    private int serialNumber;
    private double quantityInStock;
    private double purchasePrice;


    public RawMaterial(String name, int serialNumber, double quantityInStock, double purchasePrice)      //constructor
    {
        this.name = name;
        this.serialNumber = serialNumber;
        this.quantityInStock = quantityInStock;
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

    public void addAmount(double amount)
    {
        if (amount <= 0)
        {
            System.out.println("Amount must be positive");
            return;
        }
        setQuantityInStock(getQuantityInStock() + amount);
    }


    public double checkStock()          //checks stock
    {
        return quantityInStock;
    }


    //implementing valuable functions

    public double calcFinalValue()      //calculate price of ALL units in stock
    {
        return (purchasePrice * quantityInStock);
    }

    public double getBasePrice()        //returns price per unit
    {
        return purchasePrice;
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

    public double getPurchasePrice()
    {
        return purchasePrice;
    }

    public void setPurchasePrice(double purchasePrice)
    {
        this.purchasePrice = purchasePrice;
    }

    public int getID()
    {
        return this.getSerialNumber();
    }

    public String toString()
    {
        return
                "Name: " + name +
                " | Serial Number: " + serialNumber +
                " | Quantity In Stock: " + quantityInStock +
                " | Purchase Price: " + purchasePrice + " " + Valuable.CURRENCY;
    }
}