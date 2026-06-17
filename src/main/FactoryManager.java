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
        Client client = findClient(clientID);
        
        if (client == null)
        {
            System.out.println("Client with ID " + clientID + " not found.");
            return;
        }
        
        if (!client.getOrdersQueue().isEmpty())
        {
            System.out.println("Cannot delete client \"" + client.getName() + 
                             "\" (ID: " + clientID + "). Client still has " + 
                             client.getOrdersQueue().size() + " pending order(s).");
            return;
        }
        
        clientsTree.delete(clientID);
        System.out.println("Client \"" + client.getName() + "\" (ID: " + clientID + ") has been successfully deleted.");
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

        Order processed = (Order) ordersQueue.peek();
        Client client = processed.getClient();
        LinkedNode currentProductNode = processed.getProductsList().getFirst();
        while (currentProductNode != null)
        {
            OrderProduct op = (OrderProduct) currentProductNode.getData();
            if (!op.getProduct().isEnoughInStock(op.getQuantity())){
                System.out.println("\nNot enough " + op.getProduct().getName() + " in stock");
                return null;
            }
            currentProductNode = currentProductNode.getNext();
        }

         currentProductNode = processed.getProductsList().getFirst();
         while (currentProductNode != null)
         {
             OrderProduct op = (OrderProduct) currentProductNode.getData();
             sellProduct(op);
             currentProductNode = currentProductNode.getNext();
         }
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
            productsCatalog.addLast(product);
        }
    }

    public boolean deleteProduct(String name)
    {
        if (productsCatalog == null || productsCatalog.isEmpty()) {
            return false;
        }

        boolean found = false;
        LinkedList newInventory = new LinkedList();
        main.dataStructures.LinkedNode current = productsCatalog.getFirst();

        while (current != null)
        {
            Product p = (Product) current.getData();
            //
            if (!p.getName().equals(name))
            {
                newInventory.addLast(p);
            } else {
                found = true; // מצאנו ודילגנו עליו (נמחק)
            }
            current = current.getNext();
        }

        // אם מצאנו ומחקנו, נעדכן את הרשימה הראשית
        if (found) {
            productsCatalog = newInventory;
        }
        return found;
    }

    public Product removeFirstProduct()
    {
        if (productsCatalog.isEmpty())
        {
            return null;
        }

        return (Product) productsCatalog.removeFirst();
    }

    public Product getMiddleProduct()
    {
        if (productsCatalog.isEmpty())
        {
            return null;
        }

        return (Product) productsCatalog.getMiddle();
    }

    public LinkedList getproductsCatalog()
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

        // מתחילים מהאיבר הראשון ברשימה
       LinkedNode current = rawMaterialsInventory.getFirst();
        int counter = 1;

        // עוברים על הרשימה כל עוד לא הגענו לסופה
        while (current != null)
        {
            RawMaterial rm = (RawMaterial) current.getData();
            System.out.println("#" + counter + ". " + rm.toString());

            // מתקדמים לאיבר הבא
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
        if (productsCatalog == null || productsCatalog.isEmpty()) {
            System.out.println("Inventory is empty. No products to display.");
            return;
        }

        int size = productsCatalog.size();

        // יצירת מערך דו-מימדי: שורות = מספר המוצרים + 1 (לכותרות), עמודות = 4
        String[][] catalogTable = new String[size + 1][4];

        // הכנסת כותרות לשורה הראשונה
        catalogTable[0][0] = "Product Name";
        catalogTable[0][1] = "Serial Number";
        catalogTable[0][2] = "Price";
        catalogTable[0][3] = "Weight (Grams/Liters)";

        main.dataStructures.LinkedNode current = productsCatalog.getFirst();
        int row = 1;
        int dummySKU = 1000;

        // מילוי המערך הדו-מימדי מתוך האובייקטים
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

        // הדפסה טבלאית ומעוצבת של המערך הדו-מימדי
        System.out.println("\n=== PRODUCT CATALOG ===");
        for (int i = 0; i < catalogTable.length; i++) {
            // עיצוב עמודות קבוע כדי שייראה כמו טבלה
                System.out.printf("%-20s | %-10s | %-10s | %-10s\n",
                        catalogTable[i][0], catalogTable[i][1], catalogTable[i][2], catalogTable[i][3]);


            // קו מפריד אחרי הכותרות
            if (i == 0) {
                System.out.println("-------------------------------------------------------------");
            }
        }
    }

    public boolean isProductInCatalog(int serialNumber)
    {
        LinkedNode current = getproductsCatalog().getFirst();
        while (current != null) {
            Product p = (Product) current.getData();
            if (p.getSerialNumber() == serialNumber) {
                return true;
            }
            current = current.getNext();
        }
        return false;
    }
}