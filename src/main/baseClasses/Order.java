package main.baseClasses;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Date;

public class Order {
    private Client client;
    private Date orderDate;
    private ArrayList<Product> productsList;
    private int orderID;

    /**
     * Constructs a new main.baseClasses.Order instance.
     * @param client The client who placed the order.
     * @param orderDate The date the order was created.
     * @param products A main.dataBase.LinkedList containing the products for the order.
     * @param orderID id of the order.
     */
    public Order(Client client, Date orderDate, LinkedList<Product> products, int orderID)
    {
        this.client = client;
        this.orderDate = orderDate;
        this.orderID = orderID;
        this.productsList = new ArrayList<Product>();
     /*  if (products != null) {
            this.productsList.addAll(products);
        }*/
    }

    /**
     * Adds a single product to the order.
     * @param product The product to be added.
     */
    public void addProduct(Product product)
    {
        if (product != null)
        {
            this.productsList.add(product);
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


       for(Product p : this.productsList)
       {
           System.out.println("- " + p.getName() + " (" + p.getWeight() + "kg): " + p.getBasePrice() + " " + Valuable.CURRENCY);

           subTotal += p.getBasePrice();
       }
       System.out.println("------------------------");
       System.out.println("Subtotal = " + subTotal + " " + Valuable.CURRENCY);
        double taxAmount = subTotal * Valuable.TAX_PERCENT;
        System.out.println("VAT (" + (Valuable.TAX_PERCENT * 100) + "%): " + taxAmount + " " + Valuable.CURRENCY);

        double finalTotal = subTotal + taxAmount;
        System.out.println("TOTAL TO PAY: " + finalTotal + " " + Valuable.CURRENCY);
        System.out.println("=============================");
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

    public ArrayList<Product> getProductsList()
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
        return "main.baseClasses.Order #" + getOrderId() + " | Customer: " + getClient().getName() + " | Date: " + getOrderDate();
    }

}


































//67//