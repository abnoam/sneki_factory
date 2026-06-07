import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Date;

public class Order {
    private Client client;
    private Date orderDate;
    private ArrayList<Product> productsList;
    private int orderID;

    public Order(Client client, Date orderDate, LinkedList<Product> products, int orderID)
    {
        this.client = client;
        this.orderDate = orderDate;
        this.orderID = orderID;
        this.productsList = new ArrayList<Product>();
    }

    public void addProduct(Product product)
    {
        if (product != null)
        {
            this.productsList.add(product);
        } else
        {
            System.out.println("Error: Cannot add a null product to the order.");
        }
    }


    public void generateInvoice()
    {
       System.out.println("========= INVOICE =========");
       System.out.println("Order ID: " + this.getOrderId());
       System.out.println("Order Date: " + this.getOrderDate());
       System.out.println("Customer: " + this.getClient().getName());
       System.out.println("---------------------------");

       double subTotal = 0;

       //add link logic

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
        return "Order #" + getOrderId() + " | Customer: " + getClient().getName() + " | Date: " + getOrderDate();
    }

}


































//67//