package main.baseClasses;

public class OrderProduct
{
    private Product product;
    private int quantity;
    /**
     * Constructs an OrderProduct with a specific product and quantity.
     */
    public OrderProduct(Product product, int quantity)
    {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct()
    {
        return product;
    }

    public void setProduct(Product product)
    {
        this.product = product;
    }

    public int getQuantity()
    {
        return quantity;
    }

    public void setQuantity(int quantity)
    {
        this.quantity = quantity;
    }
    /** Returns a string summary of the product name and quantity. */
    public String toString()
    {
        return product.getName()
                + " | Quantity: "
                + quantity;
    }
}