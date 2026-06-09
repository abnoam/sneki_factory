package main.baseClasses;
import main.dataStructures.LinkedList;

import java.time.LocalDateTime;



public class Product implements Valuable
{
    private String name;
    private double productionCost;
    private LocalDateTime expiryDate;
    private double weight;
    private LinkedList rawMaterials;

    /**
     * Full constructor for a new Product
     * @param name              The name of the product.
     * @param productionCost    The base cost to produce the item.
     * @param expiryDate        The expiration date.
     * @param weight            The weight of the product in kg.
     * @param initialMaterials  An initial list of raw materials.
     */
    public Product(String name, double productionCost, LocalDateTime expiryDate, double weight, RawMaterial[] initialMaterials) // full constructor
    {
        this.name = name;
        this.productionCost = productionCost;
        this.expiryDate = expiryDate;
        this.weight = weight;
        this.rawMaterials = new LinkedList();

        if (initialMaterials != null)
        {
            for (RawMaterial material : initialMaterials)
            {
                if (material != null)
                {
                    this.rawMaterials.addLast(material);
                }
            }
        }
    }

    /**
     * Minimal constructor for a new Product.
     * @param name           The name of the product.
     * @param productionCost The base cost to produce the item.
     * @param weight         The weight of the product in kg.
     */
    public Product(String name, double productionCost, double weight)
    {
        this.name = name;
        this.productionCost = productionCost;
        this.weight = weight;
        this.expiryDate = LocalDateTime.now(); // Defaults to current date
        this.rawMaterials = new LinkedList(); // inits an empty list
    }
    /**
     * Checks if the current product has passed its expiry date.
     * @return true if expired, false otherwise.
     */
    public boolean isExpired()
    {
        return LocalDateTime.now().isAfter((this.expiryDate));
    }
    /**
     * Updates the weight of the product.
     * @param newWeight The new weight (must be positive).
     */
    public void updateWeight(double newWeight)
    {
        if(newWeight > 0)
            this.weight = newWeight;
        else
            System.out.println("Error: weight must be greater than zero.");
    }
    /**
     * Adds a raw material to the product composition.
     * @param material The material to add.
     */
    public void addRawMaterial(RawMaterial material)
    {
        if(material != null)
            this.rawMaterials.addLast(material);
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

    public void setProductionCost(double productionCost)
    {
        this.productionCost = productionCost;
    }
    
    public LocalDateTime getExpiryDate()
    {
        return expiryDate;
    }

    public void setExpiryDate(LocalDateTime expiryDate)
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

    public LinkedList getRawMaterials()
    {
        return rawMaterials;
    }

    public String toString()
    {
        LocalDateTime date = this.getExpiryDate();

        return "Product: " + getName() + " | Cost: " + getProductionCost() + Valuable.CURRENCY + " | Weight: " + getWeight() + "kg | Expire: " + date.getDayOfMonth() + "/"
                                                                                                                                               + date.getMonthValue() + "/"
                                                                                                                                               + date.getYear() + " "
                                                                                                                                               + date.getHour() + ":"
                                                                                                                                               + date.getMinute();
    }

    // interface methods:
    public double calcFinalValue()
    {
        return this.productionCost * (1 + Valuable.TAX_PERCENT);
    }

    public double getBasePrice()
    {
        return this.getProductionCost();
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
