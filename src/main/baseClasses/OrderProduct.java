package main.baseClasses;

public class OrderProduct
{
    private Product product;
    private int quantity;

    public OrderProduct(Product product, int quantity)  //constructor
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

    public String toString()
    {
        return product.getName()
                + " | Quantity: "
                + quantity;
    }
}