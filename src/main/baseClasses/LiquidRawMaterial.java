package main.baseClasses;

import java.time.LocalDateTime;
public class LiquidRawMaterial extends RawMaterial
{
    private double volumeInLiters;
    private double storageTemperature;
    private String containerType;

    public LiquidRawMaterial(String name, int serialNumber,double quantityInStock, LocalDateTime expirationDate,double purchasePrice,double volumeInLiters,double storageTemperature,String containerType)
    {
        super(name, serialNumber,quantityInStock,expirationDate,purchasePrice);
        this.volumeInLiters = volumeInLiters;
        this.storageTemperature = storageTemperature;
        this.containerType = containerType;
    }

    public double calcWorkingTemp()
    {
        return storageTemperature + 2; // working temp is 2 degrees above storage temp
    }

    public double caldDischargTime()
    {
        return volumeInLiters / 10.0; // pump rate of 10L per minute
    }

    public double getVolumeInLiters()
    {
        return volumeInLiters;
    }

    public void setVolumeInLiters(double volumeInLiters)
    {
        this.volumeInLiters=volumeInLiters;
    }

    public double getStorageTemperature()
    {
        return storageTemperature;
    }

    public void setStorageTemperature(double storageTemperature)
    {
        this.storageTemperature=storageTemperature;
    }

    public String getContainerType()
    {
        return containerType;
    }

    public void setContainerType(String containerType)
    {
        this.containerType = containerType;
    }

    public String toString()
    {
        return "Liquid rawMaterial - " + super.toString()  + " | Volume in Liters: " + volumeInLiters;
    }

}
