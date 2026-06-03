import java.util.Date;

public abstract class RawMaterial implements Valuable
{
    private int serialNumber;
    private double quantityInStock;
    private Date expirationDate;
    private double purchasePrice;


    public RawMaterial(int serialNumber, double quantityInStock,Date expirationDate, double purchasePrice)      //full constructor
    {
        this.serialNumber = serialNumber;
        this.quantityInStock = quantityInStock;
        this.expirationDate = expirationDate;
        this.purchasePrice = purchasePrice;
    }

    /**
     * שימוש בכמות מסוימת של חומר גלם
     */
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

    /**
     * בדיקת תוקף
     */
    public boolean isExpired()
    {
        Date currentDate = new Date();
        return expirationDate.before(currentDate);
    }

    /**
     * מימוש הממשק Valuable
     * שווי חומר הגלם = מחיר רכש * כמות במלאי
     */
    @Override
    public double calcFinalValue()
    {
        return purchasePrice * quantityInStock;
    }

    //67//
    @Override
    public double getBasePrice()
    {
        return purchasePrice;
    }

    @Override
    public double calcProfit(double targetProfitPercentage)
    {
        return calcFinalValue() * (targetProfitPercentage / 100.0);
    }

    // Getters & Setters

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

    public Date getExpirationDate()
    {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate)
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

    @Override
    public String toString()
    {
        return "RawMaterial{" +
                "serialNumber=" + serialNumber +
                ", quantityInStock=" + quantityInStock +
                ", expirationDate=" + expirationDate +
                ", purchasePrice=" + purchasePrice +
                ", expired=" + isExpired() +
                '}';
    }
}