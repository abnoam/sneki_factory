
import java.util.Date;
public class LiquidRawMaterial extends RawMaterial{
    private double volumeInLiters;
    private double storageTemperature;

    public LiquidRawMaterial(int serialNumber,double quantityInStock, Date expirationDate,double purchasePrice,double volumeInLiters,double storageTemperature){
        super(serialNumber,quantityInStock,expirationDate,purchasePrice);
        this.volumeInLiters = volumeInLiters;
        this.storageTemperature = storageTemperature;
    }
    public double getVolumeInLiters(){
        return volumeInLiters;
    }
    public void setVolumeInLiters(double volumeInLiters){
        this.volumeInLiters=volumeInLiters;
    }
    public double getStorageTemperature(){
        return storageTemperature;
    }
    public void setStorageTemperature(double storageTemperature){
        this.storageTemperature=storageTemperature;
    }
}
