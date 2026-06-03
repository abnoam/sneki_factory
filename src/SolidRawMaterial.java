import java.util.Date;

public class SolidRawMaterial extends RawMaterial{
    private double weightInKg;
    private String palletArrangement;

    public SolidRawMaterial(int serialNumber, double quantityInStock, Date expirationDate, double purchasePrice, double weightInKg, String palletArrangement){
        super(serialNumber,quantityInStock,expirationDate,purchasePrice);
        this.weightInKg = weightInKg;
        this.palletArrangement = palletArrangement;
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
}









































//67//