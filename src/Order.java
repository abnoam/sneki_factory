import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Date;

public class Order {
    private String clientName;
    private Date orderDate;
    private ArrayList<Product> products;
    private int orderID;

    public Order(String clientName, Date orderDate, LinkedList<Product> products, int orderID)
    {
        this.clientName = clientName;
        this.orderDate = orderDate;
        this.orderID = orderID;
        this.products = new ArrayList<>();
    }

    public void addProduct(Product product) {
        if (product != null) {
            this.products.add(product);
        } else {
            System.out.println("Error: Cannot add a null product to the order.");
        }
    }

    public void generateInvoice()
    {
       // kaki
    }

}


































//67//