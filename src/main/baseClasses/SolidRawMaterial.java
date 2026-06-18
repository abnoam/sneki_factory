package main.baseClasses;

import java.time.LocalDateTime;

public class SolidRawMaterial extends RawMaterial
{
    private double weightInKg;

    public SolidRawMaterial(String name, int serialNumber, double quantityInStock, double purchasePrice, double weightInKg)   //constructor
    {
        super(name, serialNumber,quantityInStock,purchasePrice);
        this.weightInKg = weightInKg;
    }

    // Getters & Setters

    public double getWeightInKg(){
        return weightInKg;
    }
    public void setWeightInKg(double weightInKg){
        this.weightInKg=weightInKg;
    }

    public String toString()
    {
        return "Solid rawMaterial - " + super.toString()  + " | Weight in KG: " + weightInKg;
    }
}