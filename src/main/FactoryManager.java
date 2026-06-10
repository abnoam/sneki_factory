package main;

import main.baseClasses.Order;
import main.baseClasses.Client;
import main.baseClasses.Product;
import main.baseClasses.RawMaterial;
import main.dataStructures.LinkedList;
import main.dataStructures.QueueAsList;
import main.dataStructures.MyBST;


public class FactoryManager
{
    private MyBST clientsTree; // client BST sorted by clientID

    private QueueAsList ordersQueue;

    private LinkedList productsInventory;

    private LinkedList rawMaterialsInventory;

    private int orderIDCounter;

    public FactoryManager()
    {
        clientsTree = new MyBST();

        ordersQueue = new QueueAsList();

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
        if (clientsTree.searchByID(clientID) == null)
        {
            return null;
        }

        return clientsTree.searchByID(clientID).getData();
    }

    public void deleteClient(int clientID)
    {
        clientsTree.delete(clientID);
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

    public boolean deleteProduct(String name)
    {
        if (productsInventory == null || productsInventory.isEmpty()) {
            return false;
        }

        boolean found = false;
        LinkedList newInventory = new LinkedList();
        main.dataStructures.LinkedNode current = productsInventory.getFirst();

        while (current != null) {
            Product p = (Product) current.getData();
            // אם זה לא המוצר שאנחנו רוצים למחוק, נוסיף אותו לרשימה החדשה
            if (!p.getName().equalsIgnoreCase(name)) {
                newInventory.addLast(p);
            } else {
                found = true; // מצאנו ודילגנו עליו (נמחק)
            }
            current = current.getNext();
        }

        // אם מצאנו ומחקנו, נעדכן את הרשימה הראשית
        if (found) {
            productsInventory = newInventory;
        }
        return found;
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
    // PRODUCT STOCK MANAGEMENT
    // ==========================

    public boolean sellProduct(Product product, int quantity)
    {
        if (product == null)
        {
            return false;
        }

        return product.sellFromStock(quantity);
    }

    public void printProductStock(Product product)
    {
        if (product != null)
        {
            System.out.println("\n=== " + product.getName() + " Stock ===");
            product.printBatches();
            System.out.println("Total Stock: " + product.getTotalStock());
        }
    }

    public int getProductTotalStock(Product product)
    {
        if (product == null)
        {
            return 0;
        }

        return product.getTotalStock();
    }
}