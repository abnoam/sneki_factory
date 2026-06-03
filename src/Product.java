import java.util.ArrayList;
import java.util.Date;
//67
//noam 67
public class Product implements Valuable
{
    private String name;
    private double productionCost;
    private Date expiryDate;
    private double weight;
    private ArrayList<RawMaterial> rawMaterials;


    public Product(String name, double productionCost, Date expiryDate, double weight) // full constructor
    {
        this.name = name;
        this.productionCost = productionCost;
        this.expiryDate = expiryDate;
        this.weight = weight;
        this.rawMaterials = new ArrayList<>(); // inits an empty list
    }

    
    public Product(String name, double productionCost, double weight)
    {
        this.name = name;
        this.productionCost = productionCost;
        this.weight = weight;
        this.expiryDate = new Date(); // gets today`s date
        this.rawMaterials = new ArrayList<>(); // inits an empty list
    }

    public boolean isExpired()
    {
        Date currentDay = new Date();
        return currentDay.after(this.expiryDate);
    }

    public void updateWeight(double newWeight)
    {
        if(newWeight > 0)
            this.weight = newWeight;
        else
            System.out.println("Error: weight must be greater than zero.");
    }

    public void addRawMaterial(RawMaterial material)
    {
        if(material != null)
            this.rawMaterials.add((material));
    }

    public String getName()
    {
        return this.name;
    }
    public void setName (String name)
    {
        this.name = name;
    }

    public double getProductionCost()
    {
        return productionCost;
    }


    //67//
    public void setProductionCost(double productionCost)
    {
        this.productionCost = productionCost;
    }
    
    public Date getExpiryDate()
    {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate)
    {
        this.expiryDate = expiryDate;
    }

    public double getWeight()
    {
        return weight;
    }
    public void setWeight(double weight)
    {
        this.weight = weight;
    }

    public ArrayList<RawMaterial> getRawMaterials()
    {
        return rawMaterials;
    }

    public String toString()
    {
        return "Product: " + getName() + " | Cost: " + getProductionCost() + CURRENCY + " | Weight: " + getWeight() + "kg | Expire: " + getExpiryDate();
    }

    // interface methods:
    public double calcFinalValue()
    {
        return this.productionCost * (1 + TAX_PERCENT);
    }

    public double getBasePrice()
    {
        return this.getProductionCost();
    }

    public double getMarketValue()
    {
        double finalValue = this.calcFinalValue();
        return finalValue = 1.2;
    }

    public double calcProfit(double targetProfitPercentage)
    {
        if (targetProfitPercentage >= 100.0 || targetProfitPercentage < 0)
        {
            System.out.println("Error: Invalid profit percentage.");
            return this.calcFinalValue();
        }

        double costWithTax = this.calcFinalValue(); // get total production cost
        double marginFactor = targetProfitPercentage / 100.0; // calc requested profit percentage in dicimal factor
        return costWithTax / (1.0 - marginFactor); // calc the target price using: TotalCost/(1 - Margin Factor)
    }
}
