package main.baseClasses;
import main.dataStructures.LinkedList;
import main.dataStructures.LinkedNode;

import java.time.LocalDateTime;

public class Order {
    private Client client;
    private LocalDateTime orderDate;
    private LinkedList productsList;
    private int orderID;

    public Order(Client client, LocalDateTime orderDate, int orderID)   //constructor
    {
        this.client = client;
        this.orderDate = orderDate;
        this.orderID = orderID;
        this.productsList = new LinkedList();

    }


    public void addProduct(Product product,int quantity)    //adds product to the order
    {
        if(product != null && quantity > 0)
        {
            productsList.addLast(new OrderProduct(product, quantity));
        }
        else
        {
            throw new NullPointerException("Invalid product or quantity provided.");
        }
    }

    public void generateInvoice()   //prints invoice of the processed order
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
           OrderProduct op = (OrderProduct) current.getData();
           Product p = op.getProduct();

           System.out.println("- " + p.getName() + " (" + p.getWeight() + "g/l ): " + p.getBasePrice() + " " + Valuable.CURRENCY + " X " + op.getQuantity());

           subTotal += p.getBasePrice() * op.getQuantity();
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

    // Getters & Setters


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



    public void printOrderDetails()
    {
        LocalDateTime date = this.getOrderDate();
        String minuteStr = (date.getMinute() < 10 ? "0" : "") + date.getMinute();
        String hourStr = (date.getHour() < 10 ? "0" : "") + date.getHour();


        System.out.println("Order #" + orderID +
                " | Customer: " + client.getName() +
                " | Items: " + (productsList != null ? productsList.size() : 0) +
                " | Date: " + date.getDayOfMonth() + "/" + date.getMonthValue() + "/" + date.getYear() + " " + hourStr + ":" + minuteStr);

        System.out.println("Products List:");
        if (productsList != null)
        {
            LinkedNode current = productsList.getFirst();
            while (current != null)
            {
                System.out.println(current.getData().toString());
                current = current.getNext();
            }
        }
    }

    public String toString()
    {
        LocalDateTime date = this.getOrderDate();

        String minuteStr = (date.getMinute() < 10 ? "0" : "") + date.getMinute();
        String hourStr = (date.getHour() < 10 ? "0" : "") + date.getHour();

        return "Order #" + getOrderId() +  " | Customer: " + getClient().getName() + " | Products: " + (productsList != null ? productsList.size() : 0) + " | Date: "
                                                                                            + date.getDayOfMonth() + "/"
                                                                                            + date.getMonthValue() + "/"
                                                                                            + date.getYear() + " "
                                                                                            + hourStr + ":"
                                                                                            + minuteStr;
    }
}