package main.baseClasses;

import java.time.LocalDateTime;

public class SolidRawMaterial extends RawMaterial
{
    private double weightInKg;
    private String palletArrangement;
    private boolean isMoistureSensitive;

    public SolidRawMaterial(int serialNumber, double quantityInStock, LocalDateTime expirationDate, double purchasePrice, double weightInKg, String palletArrangement, boolean isMoistureSensitive){
        super(serialNumber,quantityInStock,expirationDate,purchasePrice);
        this.weightInKg = weightInKg;
        this.palletArrangement = palletArrangement;
        this.isMoistureSensitive = isMoistureSensitive;
    }
    public int calcPackages(){
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
    public String getPalletArrangement() {
        return palletArrangement;
    }
    public void setPalletArrangement(String palletArrangement) {
        this.palletArrangement = palletArrangement;
    }
    public boolean isMoistureSensitive() { return isMoistureSensitive; }
    public void setMoistureSensitive(boolean isMoistureSensitive) { this.isMoistureSensitive = isMoistureSensitive; }
}