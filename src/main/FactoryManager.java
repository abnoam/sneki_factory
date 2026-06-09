package main;

import main.baseClasses.Client;
import main.baseClasses.Order;
import main.baseClasses.Product;
import main.baseClasses.RawMaterial;
import main.dataStructures.LinkedList;
import main.dataStructures.MyBST;
import main.dataStructures.QueueAsList;
import main.dataStructures.StackAsList;

public class FactoryManager
{
    private MyBST clientsTree;

    private QueueAsList ordersQueue;

    private StackAsList alertsStack;

    private LinkedList productsInventory;

    private LinkedList rawMaterialsInventory;

    private int orderIDCounter;

    public FactoryManager()
    {
        clientsTree = new MyBST();

        ordersQueue = new QueueAsList();

        alertsStack = new StackAsList();

        productsInventory = new LinkedList();

        rawMaterialsInventory = new LinkedList();

        orderIDCounter = 1;
    }

    // ==========================
    // CLIENTS
    // ==========================

    public void addClient(Client client)
    {
        if (client != null)
        {
            clientsTree.insert(client);
        }
    }

    public Client findClient(int clientID)
    {
        return clientsTree.searchByID(clientID).getData();
    }

    public void printClients()
    {
        clientsTree.printInOrder();
    }

    public MyBST getClientsTree()
    {
        return clientsTree;
    }

    // ==========================
    // ORDERS
    // ==========================

    public void addOrder(Order order)
    {
        if (order != null)
        {
            ordersQueue.offer(order);
        }
    }

    public Order processNextOrder()
    {
        if (ordersQueue.isEmpty())
        {
            return null;
        }

        return (Order) ordersQueue.poll();
    }

    public Order peekNextOrder()
    {
        if (ordersQueue.isEmpty())
        {
            return null;
        }

        return (Order) ordersQueue.peek();
    }

    public int getNextOrderID()
    {
        return orderIDCounter++;
    }

    public QueueAsList getOrdersQueue()
    {
        return ordersQueue;
    }

    // ==========================
    // PRODUCTS
    // ==========================

    public void addProduct(Product product)
    {
        if (product != null)
        {
            productsInventory.addLast(product);
        }
    }

    public Product removeFirstProduct()
    {
        if (productsInventory.isEmpty())
        {
            return null;
        }

        return (Product) productsInventory.removeFirst();
    }

    public Product getMiddleProduct()
    {
        if (productsInventory.isEmpty())
        {
            return null;
        }

        return (Product) productsInventory.getMiddle();
    }

    public LinkedList getProductsInventory()
    {
        return productsInventory;
    }

    // ==========================
    // RAW MATERIALS
    // ==========================

    public void addRawMaterial(RawMaterial material)
    {
        if (material != null)
        {
            rawMaterialsInventory.addLast(material);
        }
    }

    public RawMaterial removeFirstMaterial()
    {
        if (rawMaterialsInventory.isEmpty())
        {
            return null;
        }

        return (RawMaterial) rawMaterialsInventory.removeFirst();
    }

    public LinkedList getRawMaterialsInventory()
    {
        return rawMaterialsInventory;
    }

    // ==========================
    // ALERT STACK
    // ==========================

    public void addAlert(String alert)
    {
        if (alert != null)
        {
            alertsStack.push(alert);
        }
    }

    public String getLastAlert()
    {
        if (alertsStack.isEmpty())
        {
            return null;
        }

        return (String) alertsStack.peek();
    }

    public String removeLastAlert()
    {
        if (alertsStack.isEmpty())
        {
            return null;
        }

        return (String) alertsStack.pop();
    }

    public boolean hasAlerts()
    {
        return !alertsStack.isEmpty();
    }

    public StackAsList getAlertsStack()
    {
        return alertsStack;
    }

    // ==========================
    // GENERAL
    // ==========================

    public void printSystemStatus()
    {
        System.out.println("========== FACTORY STATUS ==========");
        System.out.println("Clients Tree: Ready");
        System.out.println("Orders Queue Empty: " + ordersQueue.isEmpty());
        System.out.println("Alerts Stack Empty: " + alertsStack.isEmpty());
        System.out.println("====================================");
    }
}