package main;
import main.baseClasses.*;
import main.dataStructures.*;
import java.util.Date;

/**
 * FactoryManager - מנהל המפעל המרכזי
 * עם עץ חיפוש בינארי לניהול Clients לפי Client ID
 * משתמש בכל מבני הנתונים מהפרויקט בלבד
 */
public class FactoryManager {

    private ClientBSTNode clientsTree;                   // Binary tree of clients bt id
    private QueueAsList ordersQueue;                // Queue of orders
    private LinkedList rawMaterialsInventory;       // List of raw materials in inventory
    private LinkedList productsInventory;           // List of stacks of products in inventory
    private LinkedList distributorsInventory;       // list of distributer
    private int totalOrdersProcessed;               // total processed orders
    private int orderIDCounter;                     // counter for order id


    public FactoryManager() {
        this.clientsTree = null;
        this.ordersQueue = new QueueAsList();
        this.rawMaterialsInventory = new LinkedList();
        this.productsInventory = new LinkedList();
        this.distributorsInventory = new LinkedList();
        this.totalOrdersProcessed = 0;
        this.orderIDCounter = 1000;
    }

    // ==================== ניהול חומרי גלם (LinkedList) ====================

    /**
     * הוספת חומר גלם למלאי
     */
    public void addRawMaterial(RawMaterial material) {
        if (material != null) {
            rawMaterialsInventory.addLast(material);
            System.out.println("✓ חומר גלם #" + material.getSerialNumber() + " נוסף בהצלחה");
        } else {
            System.out.println("✗ שגיאה: לא ניתן להוסיף חומר גלם null");
        }
    }

    /**
     * בדיקת כמות חומר גלם במלאי
     */
    public double checkRawMaterialStock(RawMaterial material) {
        if (material != null) {
            return material.checkStock();
        }
        return 0;
    }

    /**
     * שימוש בחומר גלם לייצור מוצר
     */
    public boolean useMaterial(RawMaterial material, double amount) {
        if (material == null) {
            System.out.println("✗ שגיאה: חומר גלם null");
            return false;
        }
        if (material.isExpired()) {
            System.out.println("✗ שגיאה: חומר גלם #" + material.getSerialNumber() + " פג תוקף");
            return false;
        }
        boolean used = material.useMaterial(amount);
        if (used) {
            System.out.println("✓ שימוש בחומר #" + material.getSerialNumber() + ": " + amount + " יחידות");
        }
        return used;
    }

    // ==================== ניהול מוצרים (LinkedList) ====================

    /**
     * יצירת מוצר חדש
     */
    public Product createProduct(String name, double productionCost, double weight) {
        Product product = new Product(name, productionCost, weight);
        productsInventory.addLast(product);
        System.out.println("✓ מוצר חדש '" + name + "' נוצר בהצלחה");
        return product;
    }

    /**
     * הוספת מוצר למלאי
     */
    public void addProductToInventory(Product product) {
        if (product != null) {
            productsInventory.addLast(product);
            System.out.println("✓ מוצר '" + product.getName() + "' נוסף למלאי");
        } else {
            System.out.println("✗ שגיאה: לא ניתן להוסיף מוצר null");
        }
    }

    /**
     * הסרת המוצר הראשון מהמלאי
     */
    public Product removeFirstProduct() {
        Object product = productsInventory.removeFirst();
        if (product != null) {
            System.out.println("✓ מוצר '" + ((Product)product).getName() + "' הוסר מהמלאי");
            return (Product) product;
        }
        System.out.println("✗ אין מוצרים למחיקה");
        return null;
    }

    /**
     * קבלת המוצר האמצעי בתור (median)
     */
    public Product getMiddleProduct() {
        Object middle = productsInventory.getMiddle();
        if (middle != null) {
            return (Product) middle;
        }
        return null;
    }

    /**
     * בדיקת מוצרים פגי תוקף
     */
    public void checkExpiredProducts() {
        System.out.println("\n===== בדיקת מוצרים פגי תוקף =====");
        boolean foundExpired = false;

        // שלוף את כל המוצרים ובדוק כל אחד
        LinkedList tempList = new LinkedList();
        Object current;
        while ((current = productsInventory.removeFirst()) != null) {
            Product product = (Product) current;
            if (product.isExpired()) {
                System.out.println("⚠ מוצר פג תוקף: " + product.getName());
                foundExpired = true;
            }
            tempList.addLast(product);
        }

        // החזר את כל המוצרים חזרה
        while ((current = tempList.removeFirst()) != null) {
            productsInventory.addLast(current);
        }

        if (!foundExpired) {
            System.out.println("✓ כל המוצרים בתוקף");
        }
    }

    /**
     * ספירת מספר המוצרים במלאי
     */
    public int getProductsCount() {
        int count = 0;
        LinkedList tempList = new LinkedList();
        Object current;
        while ((current = productsInventory.removeFirst()) != null) {
            count++;
            tempList.addLast(current);
        }
        while ((current = tempList.removeFirst()) != null) {
            productsInventory.addLast(current);
        }
        return count;
    }

    // ==================== ניהול Clients (עץ חיפוש בינארי) ====================

    /**
     * הוספת לקוח חדש לעץ
     */
    public Client addClient(int clientID, String name) {
        Client newClient = new Client(clientID, name);
        clientsTree = insertClientIntoTree(clientsTree, newClient);
        System.out.println("✓ לקוח חדש '" + name + "' (ID: " + clientID + ") נוסף לעץ בהצלחה");
        return newClient;
    }

    /**
     * עזר להוספת לקוח לעץ באופן רקורסיבי
     */
    private ClientBSTNode insertClientIntoTree(ClientBSTNode node, Client client) {
        if (node == null) {
            return new ClientBSTNode(client);
        }

        if (client.getClientID() < node.client.getClientID()) {
            node.left = insertClientIntoTree(node.left, client);
        } else if (client.getClientID() > node.client.getClientID()) {
            node.right = insertClientIntoTree(node.right, client);
        } else {
            System.out.println("⚠ לקוח עם ID זה כבר קיים");
        }
        return node;
    }

    /**
     * חיפוש לקוח לפי ID בעץ - O(log n) בממוצע
     */
    public Client findClientByID(int clientID) {
        ClientBSTNode node = searchInTree(clientsTree, clientID);
        if (node != null) {
            return node.client;
        }
        return null;
    }

    /**
     * עזר לחיפוש בעץ באופן רקורסיבי
     */
    private ClientBSTNode searchInTree(ClientBSTNode node, int clientID) {
        if (node == null) {
            return null;
        }

        if (clientID == node.client.getClientID()) {
            return node;
        } else if (clientID < node.client.getClientID()) {
            return searchInTree(node.left, clientID);
        } else {
            return searchInTree(node.right, clientID);
        }
    }

    /**
     * הוספת מפיץ חדש לעץ ובנפרד לרשימה
     */
    public Distributor addDistributor(int clientID, String name, Distributor.Region area,
                                      String licenseNumber, double distributionPrice) {
        Distributor newDistributor = new Distributor(clientID, name, area, licenseNumber, distributionPrice);
        clientsTree = insertClientIntoTree(clientsTree, newDistributor);
        distributorsInventory.addLast(newDistributor);
        System.out.println("✓ מפיץ חדש '" + name + "' (ID: " + clientID + ") נוסף לעץ באזור " + area);
        return newDistributor;
    }

    /**
     * קבלת כל הלקוחות בעץ בסדר In-Order (מהקטן לגדול לפי ID)
     */
    public LinkedList getAllClientsSorted() {
        LinkedList result = new LinkedList();
        inOrderTraversal(clientsTree, result);
        return result;
    }

    /**
     * traversal In-Order של העץ
     */
    private void inOrderTraversal(ClientBSTNode node, LinkedList result) {
        if (node != null) {
            inOrderTraversal(node.left, result);
            result.addLast(node.client);
            inOrderTraversal(node.right, result);
        }
    }

    /**
     * ספירת מספר הלקוחות בעץ
     */
    public int getClientsCount() {
        return countNodes(clientsTree);
    }

    /**
     * עזר לספירת צמתים בעץ
     */
    private int countNodes(ClientBSTNode node) {
        if (node == null) {
            return 0;
        }
        return 1 + countNodes(node.left) + countNodes(node.right);
    }

    /**
     * בדיקה האם לקוח קיים בעץ
     */
    public boolean clientExists(int clientID) {
        return findClientByID(clientID) != null;
    }

    /**
     * הסרת לקוח מהעץ לפי ID
     */
    public boolean removeClient(int clientID) {
        int initialSize = getClientsCount();
        clientsTree = deleteClientFromTree(clientsTree, clientID);
        int finalSize = getClientsCount();

        if (finalSize < initialSize) {
            System.out.println("✓ לקוח עם ID " + clientID + " הוסר מהעץ");
            return true;
        } else {
            System.out.println("✗ לקוח עם ID " + clientID + " לא נמצא");
            return false;
        }
    }

    /**
     * עזר להסרת לקוח מהעץ באופן רקורסיבי
     */
    private ClientBSTNode deleteClientFromTree(ClientBSTNode node, int clientID) {
        if (node == null) {
            return null;
        }

        if (clientID < node.client.getClientID()) {
            node.left = deleteClientFromTree(node.left, clientID);
        } else if (clientID > node.client.getClientID()) {
            node.right = deleteClientFromTree(node.right, clientID);
        } else {
            // צומת זה הוא זה שאנחנו רוצים למחוק
            if (node.left == null) {
                return node.right;
            } else if (node.right == null) {
                return node.left;
            } else {
                // שני בנים - מצא את ה-successor הקטן ביותר מהעץ הימני
                ClientBSTNode minNode = findMin(node.right);
                node.client = minNode.client;
                node.right = deleteClientFromTree(node.right, minNode.client.getClientID());
            }
        }
        return node;
    }

    /**
     * עזר למצוא את הצומת הקטן ביותר בתת-עץ
     */
    private ClientBSTNode findMin(ClientBSTNode node) {
        if (node.left == null) {
            return node;
        }
        return findMin(node.left);
    }

    /**
     * קבלת המפיץ (Distributor) האמצעי ברשימה
     */
    public Distributor getMiddleDistributor() {
        Object middle = distributorsInventory.getMiddle();
        if (middle != null) {
            return (Distributor) middle;
        }
        return null;
    }

    /**
     * ספירת מספר המפיצים
     */
    public int getDistributorsCount() {
        int count = 0;
        LinkedList tempList = new LinkedList();
        Object current;
        while ((current = distributorsInventory.removeFirst()) != null) {
            count++;
            tempList.addLast(current);
        }
        while ((current = tempList.removeFirst()) != null) {
            distributorsInventory.addLast(current);
        }
        return count;
    }

    // ==================== ניהול הזמנות (Queue) ====================

    /**
     * יצירת הזמנה חדשה
     */
    public Order createOrder(Client client, Date orderDate) {
        int newOrderID = orderIDCounter++;
        Order order = new Order(client, orderDate, new java.util.LinkedList<>(), newOrderID);
        System.out.println("✓ הזמנה חדשה #" + newOrderID + " נוצרה ללקוח " + client.getName());
        return order;
    }

    /**
     * הוספת הזמנה לתור
     */
    public void addOrderToQueue(Order order) {
        if (order != null) {
            ordersQueue.offer(order);
            order.getClient().addOrder(order);
            System.out.println("✓ הזמנה #" + order.getOrderId() + " נוספה לתור");
        } else {
            System.out.println("✗ שגיאה: לא ניתן להוסיף הזמנה null");
        }
    }

    /**
     * עיבוד ההזמנה הבאה בתור
     */
    public Order processNextOrder() {
        Object order = ordersQueue.poll();
        if (order != null) {
            totalOrdersProcessed++;
            Order processedOrder = (Order) order;
            System.out.println("✓ הזמנה #" + processedOrder.getOrderId() + " עובדה בהצלחה");
            return processedOrder;
        } else {
            System.out.println("ℹ תור ההזמנות ריק");
            return null;
        }
    }

    /**
     * בדיקת ההזמנה הבאה בתור ללא הסרתה
     */
    public Order peekNextOrder() {
        Object order = ordersQueue.peek();
        return order != null ? (Order) order : null;
    }

    /**
     * בדיקת גודל תור ההזמנות
     */
    public int getOrdersQueueSize() {
        return ordersQueue.size();
    }

    /**
     * בדיקה האם תור ההזמנות ריק
     */
    public boolean isOrdersQueueEmpty() {
        return ordersQueue.isEmpty();
    }

    // ==================== דוחות ודוקומנטציה ====================

    /**
     * הדפסת דוח מצב המלאי
     */
    public void printInventorySummary() {
        System.out.println("\n========== דוח מצב המלאי ==========");
        System.out.println("📦 מוצרים במלאי: " + getProductsCount());
        System.out.println("👥 לקוחות רשומים בעץ: " + getClientsCount());
        System.out.println("🚚 מפיצים רשומים: " + getDistributorsCount());
        System.out.println("📋 הזמנות בתור: " + ordersQueue.size());
        System.out.println("✅ הזמנות מעובדות: " + totalOrdersProcessed);
        System.out.println("===================================\n");
    }

    /**
     * הדפסת רשימת כל המוצרים במלאי
     */
    public void printAllProducts() {
        System.out.println("\n===== רשימת מוצרים במלאי =====");
        LinkedList tempList = new LinkedList();
        Object current;
        int count = 0;

        while ((current = productsInventory.removeFirst()) != null) {
            count++;
            Product product = (Product) current;
            System.out.println(count + ". " + product);
            tempList.addLast(product);
        }

        while ((current = tempList.removeFirst()) != null) {
            productsInventory.addLast(current);
        }

        if (count == 0) {
            System.out.println("אין מוצרים במלאי");
        }
        System.out.println("=============================\n");
    }

    /**
     * הדפסת רשימת כל הלקוחות (ממויינים לפי ID)
     */
    public void printAllClients() {
        System.out.println("\n===== רשימת לקוחות (ממויינים לפי ID) =====");
        LinkedList sortedClients = getAllClientsSorted();
        Object current;
        int count = 0;
        LinkedList tempList = new LinkedList();

        while ((current = sortedClients.removeFirst()) != null) {
            count++;
            Client client = (Client) current;
            System.out.println(count + ". " + client);
            tempList.addLast(client);
        }

        // שחזור הרשימה
        while ((current = tempList.removeFirst()) != null) {
            sortedClients.addLast(current);
        }

        if (count == 0) {
            System.out.println("אין לקוחות רשומים");
        }
        System.out.println("=========================================\n");
    }

    /**
     * הדפסת רשימת כל ההזמנות בתור
     */
    public void printOrdersQueue() {
        System.out.println("\n===== תור ההזמנות =====");
        if (ordersQueue.isEmpty()) {
            System.out.println("תור ההזמנות ריק");
        } else {
            System.out.println("הזמנות בתור: " + ordersQueue.size());
            Object peek = ordersQueue.peek();
            if (peek != null) {
                Order order = (Order) peek;
                System.out.println("ההזמנה הבאה לעיבוד: " + order);
            }
        }
        System.out.println("=======================\n");
    }

    /**
     * קבלת סיכום ממלא מקום של המנהל
     */
    public String getManagerStatus() {
        return "\n=== סטטוס מנהל המפעל ===" +
                "\n📊 מוצרים: " + getProductsCount() +
                "\n👥 לקוחות בעץ: " + getClientsCount() +
                "\n🚚 מפיצים: " + getDistributorsCount() +
                "\n📋 הזמנות בתור: " + ordersQueue.size() +
                "\n✅ סה\"כ הזמנות מעובדות: " + totalOrdersProcessed +
                "\n=======================\n";
    }

    /**
     * קבלת סה"כ הזמנות מעובדות
     */
    public int getTotalOrdersProcessed() {
        return totalOrdersProcessed;
    }

    /**
     * איפוס מנהל המפעל
     */
    public void reset() {
        clientsTree = null;
        ordersQueue = new QueueAsList();
        rawMaterialsInventory = new LinkedList();
        productsInventory = new LinkedList();
        distributorsInventory = new LinkedList();
        totalOrdersProcessed = 0;
        orderIDCounter = 1000;
        System.out.println("✓ מנהל המפעל אופס");
    }

    // ==================== מחלקת עזר - צומת בעץ החיפוש ====================
    /**
     * מחלקה פנימית לייצוג צומת בעץ חיפוש בינארי של Clients
     */
    private static class ClientBSTNode {
        Client client;
        ClientBSTNode left;
        ClientBSTNode right;

        ClientBSTNode(Client client) {
            this.client = client;
            this.left = null;
            this.right = null;
        }
    }
}