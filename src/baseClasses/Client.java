package baseClasses;

import java.util.Queue;
import java.util.LinkedList;

public class Client
{
    private int clientID;
    private String name;
    private Queue<Order> ordersQueue;

    public Client(int clientID, String name)
    {
        this.clientID = clientID;
        this.name = name;
        this.ordersQueue = new LinkedList<Order>();
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
        return this.ordersQueue.poll();
    }

    public Order peekNextOrder()
    {
        return this.ordersQueue.peek(); // returns the order from the queue without removing it
    }

    public int getCliendID()
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

    public Queue<Order> getOrdersQueue()
    {
        return ordersQueue;
    }

    public String toString()
    {
        return "baseClasses.Client #" + getCliendID() + " | Name: " + getName() + " | Pending Orders: " + ordersQueue.size();
    }
}
