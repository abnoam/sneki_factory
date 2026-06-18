package main;

import main.baseClasses.*;
import main.dataStructures.LinkedList;
import main.dataStructures.LinkedNode;
import main.dataStructures.QueueAsList;
import main.dataStructures.MyBST;


public class FactoryManager
{
    private MyBST clientsTree; // client BST sorted by clientID

    private QueueAsList ordersQueue;

    private LinkedList productsCatalog;

    private LinkedList rawMaterialsInventory;

    private int orderIDCounter;

    public FactoryManager()
    {
        clientsTree = new MyBST();

        ordersQueue = new QueueAsList();

        productsCatalog = new LinkedList();

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
        // find the client by ID
        Client client = findClient(clientID);

        // check if the client exists in the system
        if (client == null)
        {
            System.out.println("Client with ID " + clientID + " not found.");
            return;
        }

        // prevent deletion if the client has pending orders
        if (!client.getOrdersQueue().isEmpty())
        {
            System.out.println("Cannot delete client \"" + client.getName() + 
                             "\" (ID: " + clientID + "). Client still has " + 
                             client.getOrdersQueue().size() + " pending order(s).");
            return;
        }

        // delete the client from the BST and print confirmation
        clientsTree.delete(clientID);
        System.out.println("Client \"" + client.getName() + "\" (ID: " + clientID + ") has been successfully deleted.");
    }

    public void printClients()
    {
        clientsTree.printInOrder();
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
        // check if there are any pending orders
        if (ordersQueue.isEmpty())
        {
            return null;
        }

        // look at next order (without removing it)
        Order processed = (Order) ordersQueue.peek();
        Client client = processed.getClient();
        LinkedNode currentProductNode = processed.getProductsList().getFirst();

        // varify sufficient stock for all items in the order
        while (currentProductNode != null)
        {
            OrderProduct op = (OrderProduct) currentProductNode.getData();
            if (!op.getProduct().isEnoughInStock(op.getQuantity())){
                System.out.println("\nNot enough " + op.getProduct().getName() + " in stock");
                return null; // abort the process if any product is out of stock
            }
            currentProductNode = currentProductNode.getNext();
        }

         currentProductNode = processed.getProductsList().getFirst();

        // deduct the products from the inventory with sell product function
         while (currentProductNode != null)
         {
             OrderProduct op = (OrderProduct) currentProductNode.getData();
             sellProduct(op);
             currentProductNode = currentProductNode.getNext();
         }

         // remove the completed order from the factory and client queues
        ordersQueue.pull();
        client.getOrdersQueue().pull();

        System.out.println("\nOrder #" + processed.getOrderId() + " Processed\n");
        return processed;
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



    // ==========================
    // PRODUCTS
    // ==========================

    public void addProduct(Product product)
    {
        if (product != null)
        {
            productsCatalog.addLast(product);
        }
    }

    public boolean deleteProduct(String name)
    {
        if(isProductInPendingOrders(name))
        {
            System.out.println("Error: Cannot delete product '" + name + "'. It is currently a part of a pending order.");
            return false;
        }
        // check if catalog is empty
        if (productsCatalog == null || productsCatalog.isEmpty())
        {
            return false;
        }

        boolean found = false;
        LinkedList newInventory = new LinkedList(); // creates a temporary list
        LinkedNode current = productsCatalog.getFirst();

        // move through the current catalog
        while (current != null)
        {
            Product p = (Product) current.getData();
            // if it`s not the product we wanted to delete, keep it in temp list
            if (!p.getName().equals(name))
            {
                newInventory.addLast(p);
            }
            else
            {
                found = true; // found the product, delete by skipping it
            }
            current = current.getNext();
        }

        // replace the old list with the temporary if a deletion occured
        if (found)
        {
            productsCatalog = newInventory;
        }
        else
        {
            System.out.println("Error: Product '" + name + "' not found in inventory.");
        }

        return found;
    }


    public boolean isProductNameAvailable(String name)
    {
        if(name == null)
        {
            System.out.println("Name Cannot be NULL!");
            return false;
        }
        LinkedNode productNode = productsCatalog.getFirst();
        while(productNode != null)
        {
            Product product = (Product) productNode.getData();
            if (product.getName().equals(name))
            {
                System.out.println("Error: Product with Name " + name + " already exists!");
                return false;
            }
            productNode = productNode.getNext();
        }
        return true;
    }

    public LinkedList getProductsCatalog()
    {
        return productsCatalog;
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

    public void printRawMaterial()
    {
        if (rawMaterialsInventory == null || rawMaterialsInventory.isEmpty())
        {
            System.out.println("Raw materials inventory is currently empty.");
            return;
        }

        System.out.println("\n=== RAW MATERIALS INVENTORY ===");

        // get the first node of the raw material linked list
       LinkedNode current = rawMaterialsInventory.getFirst();
        int counter = 1;

        // move through the list until the end of it
        while (current != null)
        { //print the raw material info, according to its toString function
            RawMaterial rm = (RawMaterial) current.getData();
            System.out.println("#" + counter + ". " + rm.toString());

            // move to the next node
            current = current.getNext();
            counter++;
        }
        System.out.print("\n");
        System.out.println("===============================\n");
    }

    public LinkedList getRawMaterialsInventory()
    {
        return rawMaterialsInventory;
    }

    private boolean isProductInPendingOrders(String productName)
    {
        if (ordersQueue == null || ordersQueue.isEmpty())
        {
            return false;
        }

        boolean isFound = false;
        QueueAsList tempQueue = new QueueAsList(); // temp queue

        // empty the original queue, check all orders and fill temp queue
        while (!ordersQueue.isEmpty())
        {
            Order currentOrder = (Order) ordersQueue.pull();

            // check all orders
            LinkedNode currentProductNode = currentOrder.getProductsList().getFirst();
            while (currentProductNode != null)
            {
                OrderProduct op = (OrderProduct) currentProductNode.getData();
                if (op.getProduct().getName().equalsIgnoreCase(productName))
                {
                    isFound = true; // if product is found in order
                }
                currentProductNode = currentProductNode.getNext();
            }

            tempQueue.offer(currentOrder); // save the order in temp queue
        }

        // return all orders to the queue in the same order it been
        while (!tempQueue.isEmpty())
        {
            ordersQueue.offer(tempQueue.pull());
        }

        return isFound;
    }

    // ==========================
    // PRODUCT STOCK MANAGEMENT
    // ==========================

    public void sellProduct(OrderProduct orderProduct)
    {
        if (orderProduct == null)
        {
            return;
        }
        orderProduct.getProduct().sellFromStock(orderProduct.getQuantity());
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

    public void printProductCatalogMatrix()
    {
        if (productsCatalog == null || productsCatalog.isEmpty())
        {
            System.out.println("Inventory is empty. No products to display.");
            return;
        }

        int size = productsCatalog.size();

        // creating a 2D array, with 4 columns and size according to the amount of products.
        String[][] catalogTable = new String[size + 1][4];

        // init headlines for the first row
        catalogTable[0][0] = "Product Name";
        catalogTable[0][1] = "Serial Number";
        catalogTable[0][2] = "Price";
        catalogTable[0][3] = "Weight (Grams/Liters)";

        main.dataStructures.LinkedNode current = productsCatalog.getFirst();
        int row = 1;

        // fill the 2D array with products info
        while (current != null)
        {
            Product p = (Product) current.getData();

            catalogTable[row][0] = p.getName();
            catalogTable[row][1] = String.valueOf(p.getSerialNumber());
            catalogTable[row][2] = String.format("%.2f", p.calcFinalValue());
            catalogTable[row][3] = String.format("%.2f", p.getWeight());

            row++;
            current = current.getNext();
        }

        // print 2D array according to the design
        System.out.println("\n=== PRODUCT CATALOG ===");
        for (int i = 0; i < catalogTable.length; i++) {
            // design the arrays print
                System.out.printf("%-20s | %-10s | %-10s | %-10s\n",
                        catalogTable[i][0], catalogTable[i][1], catalogTable[i][2], catalogTable[i][3]);

            if (i == 0)
            {
                System.out.println("-------------------------------------------------------------");
            }
        }
    }

    public boolean isProductInCatalog(int serialNumber)
    {
        // get the first product in the linked list
        LinkedNode current = getProductsCatalog().getFirst();
        while (current != null)
        {
            Product p = (Product) current.getData();
            if (p.getSerialNumber() == serialNumber) //if product is found (by serialNumber)
            {
                return true;
            }
            current = current.getNext(); // moves through the list
        }
        return false; // if product is not found
    }
}