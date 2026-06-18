package main.baseClasses;
import main.dataStructures.LinkedList;
import main.dataStructures.LinkedNode;
import main.dataStructures.StackAsList;

import java.time.LocalDateTime;



public class Product implements Valuable
{
    private String name;
    private double productionCost;
    private LocalDateTime expiryDate;
    private double weight;
    private int serialNumber;
    private LinkedList rawMaterials;
    private StackAsList batches;


    public Product(String name, double productionCost, LocalDateTime expiryDate, double weight, RawMaterial[] initialMaterials,int serialNumber) // full constructor
    {
        this.name = name;
        this.productionCost = productionCost;
        this.expiryDate = expiryDate;
        this.weight = weight;
        this.rawMaterials = new LinkedList(); // inits an empty list
        this.batches = new StackAsList(); // inits empty batch stack
        this.serialNumber = serialNumber;
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


    public Product(String name, double productionCost, double weight) //constructor
    {
        this.name = name;
        this.productionCost = productionCost;
        this.weight = weight;
        this.serialNumber = 0;
        this.expiryDate = LocalDateTime.now(); // Defaults to current date
        this.rawMaterials = new LinkedList(); // inits an empty list
        this.batches = new StackAsList(); //inits empty batch stack
    }

    public boolean isExpired() //checks if the product is expired
    {
        return LocalDateTime.now().isAfter((this.expiryDate));
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
            this.rawMaterials.addLast(material);
    }

    // Getters & Setters

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

    public double getBasePrice()
    {
        return this.getProductionCost();
    }

    public int getSerialNumber()
    {
        return this.serialNumber;
    }

    public void setSerialNumber(int serialNumber)
    {
        this.serialNumber = serialNumber;
    }



    public boolean  addBatch(Batch batch) // add batches according to batch expiry dates
    {
        if(batch == null)
        {
            return false;
        }

        int amountNeeded = batch.getQuantity();
        LinkedNode currentMaterialNode = this.rawMaterials.getFirst();

        //checks if there are enough RawMaterials for the batch
        while (currentMaterialNode != null)
        {
            RawMaterial rawMa = (RawMaterial) currentMaterialNode.getData();

            if(rawMa.getQuantityInStock() < amountNeeded)
            {
                System.out.println("Error: Not enough '" + rawMa.getName() + "' in stock.");
                return false;
            }
            currentMaterialNode = currentMaterialNode.getNext();
        }
        //if there are enough RawMaterials it updates the amount of each RawMaterial in the stock
        currentMaterialNode = this.rawMaterials.getFirst();
        while(currentMaterialNode != null)
        {
            RawMaterial rawMa = (RawMaterial) currentMaterialNode.getData();
            rawMa.useMaterial(amountNeeded);
            currentMaterialNode = currentMaterialNode.getNext();
        }

        StackAsList temp = new StackAsList();

        //reorder the Batches in the stack according to the batch date
        while(!batches.isEmpty())
        {
            Batch current = (Batch)batches.peek();

            if(current.getExpiryDate().isBefore(batch.getExpiryDate()))
            {
                temp.push(batches.pop()); // add to stack
            }
            else
            {
                break;
            }
        }

        batches.push(batch);

        while(!temp.isEmpty())
        {
            batches.push(temp.pop());
        }
        return true;
    }


    public boolean isEnoughInStock(int amount)
    {
        return getTotalStock() >= amount;
    }
    public void sellFromStock(int amount)   // Removes the requested quantity from inventory, starting from the oldest batch.
    {
        if(amount <= 0)
        {
            System.out.println("Amount Can't Be Zero Or Negative!");
            return;
        }

        while(amount > 0 && !batches.isEmpty())
        {
            Batch batch = (Batch)batches.peek();

            if(batch.getQuantity() > amount)
            {
                batch.setQuantity(batch.getQuantity() - amount); // reduces the amount of the batch that has been sent to sell
                return;
            }

            amount -= batch.getQuantity(); // removes the batch quantity from the wanted amount

            batches.pop(); //removes batch from stack.
        }
    }

    public int getTotalStock() // calc total stock in the stack
    {
        StackAsList temp = new StackAsList();

        int total = 0;

        while(!batches.isEmpty())
        {
            Batch batch = (Batch)batches.pop(); // removes from original stack

            total += batch.getQuantity();

            temp.push(batch); // adds to temporary stack
        }

        while(!temp.isEmpty())
        {
            batches.push(temp.pop()); //returns the batches back to the original stack
        }

        return total;
    }

    public void printBatches()
    {
        StackAsList temp = new StackAsList();

        while(!batches.isEmpty())
        {
            Batch batch = (Batch)batches.pop(); // removes from original stack

            System.out.println(batch);

            temp.push(batch); // adds to temporary stack
        }

        while(!temp.isEmpty())
        {
            batches.push(temp.pop()); //returns the batches back to the original stack
        }
    }

    public String toString()
    {
        LocalDateTime date = this.getExpiryDate();
        String minuteStr = (date.getMinute() < 10 ? "0" : "") + date.getMinute();
        String hourStr = (date.getHour() < 10 ? "0" : "") + date.getHour();

        return "Product: " + getName() + " | Cost: " + getProductionCost() + Valuable.CURRENCY + " | Weight: " + getWeight() + "kg | Expire: " + date.getDayOfMonth() + "/"
                                                                                                                                               + date.getMonthValue() + "/"
                                                                                                                                               + date.getYear() + " "
                                                                                                                                               + hourStr + ":"
                                                                                                                                               + minuteStr;
    }

    // interface methods:
    // Calculates the final value including tax.
    public double calcFinalValue()
    {
        return this.productionCost * (1 + Valuable.TAX_PERCENT);
    }

}

