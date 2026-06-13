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
    public int calcPackages()
    {
        return (int)(weightInKg / 0.5); // a package weight is 0.5 kg.
    }
    public double calcWastageFactor(){
        return weightInKg * 0.95; //5% Wastage.
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