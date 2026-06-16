package main.baseClasses;

import java.time.LocalDateTime;

public class SolidRawMaterial extends RawMaterial
{
    private double weightInKg;

    public SolidRawMaterial(String name, int serialNumber, double quantityInStock, LocalDateTime expirationDate, double purchasePrice, double weightInKg)
    {
        super(name, serialNumber,quantityInStock,expirationDate,purchasePrice);
        this.weightInKg = weightInKg;
    }

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