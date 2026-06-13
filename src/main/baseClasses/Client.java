package main.baseClasses;
import main.dataStructures.QueueAsList;


public class Client
{
    private int clientID;
    private String name;
    private QueueAsList ordersQueue;

    public Client(int clientID, String name)
    {
        this.clientID = clientID;
        this.name = name;
        this.ordersQueue = new QueueAsList();
    }

    public void addOrder(Order newOrder)        //adds order to the queue
    {
        if(newOrder == null)
        {
            throw new NullPointerException("Cannot add a null order to the queue.");
        }

        this.ordersQueue.offer(newOrder);
    }

    public Order processNextOrder()     //removes order from queue amd returns it
    {
        return (Order) this.ordersQueue.pull();
    }

    public Order peekNextOrder()
    {
        return (Order) this.ordersQueue.peek(); // returns the order from the queue without removing it
    }

    public int getClientID()
    {
        return clientID;
    }

    public void setClientID(int clientID)
    {
        this.clientID = clientID;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public QueueAsList getOrdersQueue()
    {
        return ordersQueue;
    }

    public String toString()
    {
        return "Client #" + getClientID() + " | Name: " + getName() + " | Pending Orders: " + ordersQueue.size();
    }
}
