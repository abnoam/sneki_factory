import java.util.Queue;
import java.util.LinkedList;

public class Client
{
    private int cliendID;
    private String name;
    private Queue<Order> ordersQueue;

    public Client(int clientID, String name)
    {
        this.cliendID = clientID;
        this.name = name;
        this.ordersQueue = new LinkedList<Order>();
    }

    public void addOrder(Order newOrder)
    {
        if(newOrder != null)
        {
            this.ordersQueue.offer(newOrder);
        }
    }

    public Order processNextOrder()
    {
        return this.ordersQueue.poll();
    }

    public Order peekNextOrder()
    {
        return this.ordersQueue.peek();
    }

    public int getCliendID()
    {
        return cliendID;
    }
    public void setClientID(int clientID)
    {
        this.cliendID = clientID;
    }

    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name = name;
    }

    public Queue<Order> getOrdersQueue()
    {
        return ordersQueue;
    }

    public String toString()
    {
        return "Client #" + getCliendID() + " | Name: " + getName() + " | Pending Orders: " + ordersQueue.size();
    }
}
