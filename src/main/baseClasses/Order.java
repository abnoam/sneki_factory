package main.baseClasses;
import main.dataStructures.LinkedList;
import main.dataStructures.LinkedNode;

import java.util.Date;

public class Order {
    private Client client;
    private Date orderDate;
    private LinkedList productsList;
    private int orderID;

    /**
     * Constructs a new main.baseClasses.Order instance.
     * @param client The client who placed the order.
     * @param orderDate The date the order was created.
     * @param orderID id of the order.
     */
    public Order(Client client, Date orderDate, int orderID)
    {
        this.client = client;
        this.orderDate = orderDate;
        this.orderID = orderID;
        this.productsList = new LinkedList();

    }

    /**
     * Adds a single product to the order.
     * @param product The product to be added.
     */
    public void addProduct(Product product)
    {
        if (product != null)
        {
            this.productsList.addLast(product);
        } else
        {
            throw new IllegalArgumentException("main.baseClasses.Product cannot be null.");
        }
    }

    /**
     * Generates and prints a detailed invoice.
     * Calculates the subtotal, applies tax, and displays the total amount.
     */
    public void generateInvoice()
    {
       System.out.println("========= INVOICE =========");
       System.out.println("main.baseClasses.Order ID: " + this.getOrderId());
       System.out.println("main.baseClasses.Order Date: " + this.getOrderDate());
       System.out.println("Customer: " + this.getClient().getName());
       System.out.println("---------------------------");

       double subTotal = 0;

       LinkedNode current = this.productsList.getFirst();
       while (current != null)
       {
           Product p = (Product) current.getData();

           System.out.println("- " + p.getName() + " (" + p.getWeight() + "kg ): " + p.getBasePrice() + " " + Valuable.CURRENCY);

           subTotal += p.getBasePrice();
           current = current.getNext();
       }

       System.out.println("---------------------------");
       System.out.println("Subtotal = " + subTotal + " " + Valuable.CURRENCY);
        double taxAmount = subTotal * Valuable.TAX_PERCENT;
        System.out.println("VAT (" + (Valuable.TAX_PERCENT * 100) + "%): " + taxAmount + " " + Valuable.CURRENCY);

        double finalTotal = subTotal + taxAmount;
        System.out.println("TOTAL TO PAY: " + finalTotal + " " + Valuable.CURRENCY);
        System.out.println("===========================");
    }

    public int getOrderId()
    {
        return orderID;
    }
    public void setOrderId(int orderID)
    {
        this.orderID = orderID;
    }

    public Client getClient()
    {
        return client;
    }
    public void setClient(Client client)
    {
        this.client = client;
    }

    public LinkedList getProductsList()
    {
        return productsList;
    }

    public Date getOrderDate()
    {
        return orderDate;
    }
    public void setOrderDate(Date orderDate)
    {
        this.orderDate = orderDate;
    }

    public String toString()
    {
        return "Order #" + getOrderId() + " | Customer: " + getClient().getName() + " | Date: " + getOrderDate();
    }

}


































//67//