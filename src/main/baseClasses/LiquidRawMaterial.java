package main.baseClasses;

import java.time.LocalDateTime;
public class LiquidRawMaterial extends RawMaterial
{
    private double volumeInLiters;

    public LiquidRawMaterial(String name, int serialNumber,double quantityInStock, LocalDateTime expirationDate,double purchasePrice,double volumeInLiters)
    {
        super(name, serialNumber,quantityInStock,expirationDate,purchasePrice);
        this.volumeInLiters = volumeInLiters;

    }

    public double getVolumeInLiters()
    {
        return volumeInLiters;
    }

    public void setVolumeInLiters(double volumeInLiters)
    {
        this.volumeInLiters=volumeInLiters;
    }


    public String toString()
    {
        return "Liquid rawMaterial - " + super.toString()  + " | Volume in Liters: " + volumeInLiters;
    }

}
