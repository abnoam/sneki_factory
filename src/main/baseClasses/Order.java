package main.baseClasses;
import main.dataStructures.LinkedList;
import main.dataStructures.LinkedNode;

import java.time.LocalDateTime;

public class Order {
    private Client client;
    private LocalDateTime orderDate;
    private LinkedList productsList;
    private int orderID;

    /**
     * Constructs a new main.baseClasses.Order instance.
     * @param client The client who placed the order.
     * @param orderDate The date the order was created.
     * @param orderID id of the order.
     */
    public Order(Client client, LocalDateTime orderDate, int orderID)
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
            throw new IllegalArgumentException("Product cannot be null.");
        }
    }

    /**
     * Generates and prints a detailed invoice.
     * Calculates the subtotal, applies tax, and displays the total amount.
     */
    public void generateInvoice()
    {
       LocalDateTime date = this.getOrderDate();
       System.out.println("========= INVOICE =========");
       System.out.println("Order ID: " + this.getOrderId());
       System.out.println("Order Date: " + date.getDayOfMonth() + "/"
                                         + date.getMonthValue() + "/"
                                         + date.getYear() + " "
                                         + date.getHour() + ":"
                                         + date.getMinute() );
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
        System.out.print("VAT (" + (Valuable.TAX_PERCENT * 100) + "%): ");
        System.out.printf("%.2f  %s\n",taxAmount , Valuable.CURRENCY);

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

    public LocalDateTime getOrderDate()
    {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate)
    {
        this.orderDate = orderDate;
    }

    public String toString()
    {
        LocalDateTime date = this.getOrderDate();
        return "Order #" + getOrderId() + " | Customer: " + getClient().getName() + " | Date: "
                                                                                            + date.getDayOfMonth() + "/"
                                                                                            + date.getMonthValue() + "/"
                                                                                            + date.getYear() + " "
                                                                                            + date.getHour() + ":"
                                                                                            + date.getMinute();
    }

}


































//67//