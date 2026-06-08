package main;

import main.baseClasses.*;
import main.dataBase.*;


import java.util.Date;

/**
 * main.FactoryManager
 * כולל ניהול מלאי חומרי גלם, ניהול מוצרים, ניהול הזמנות וניהול לקוחות
 */
public class FactoryManager {
    private QueueAsList ordersQueue;
    private main.dataBase.LinkedList rawMaterialsInventory;
    private ArrayList<Product> productsInventory;
    private ArrayList<Client> clients;
    private ArrayList<Distributor> distributors;
    private int totalOrdersProcessed;
    private int orderIDCounter;

    /**
     * קונסטרקטור - יוצר מנהל מפעל חדש עם מלאים ריקים
     */
    public FactoryManager() {
        this.ordersQueue = new QueueAsList();
        this.rawMaterialsInventory = new main.dataBase.LinkedList();
        this.productsInventory = new ArrayList<>();
        this.clients = new ArrayList<>();
        this.distributors = new ArrayList<>();
        this.totalOrdersProcessed = 0;
        this.orderIDCounter = 1000;
    }

    // ==================== ניהול חומרי גלם ====================

    /**
     * הוספת חומר גלם למלאי
     * @param material חומר הגלם להוספה
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
     * @param material חומר הגלם לבדיקה
     * @return הכמות במלאי
     */
    public double checkRawMaterialStock(RawMaterial material) {
        if (material != null) {
            return material.checkStock();
        }
        return 0;
    }

    /**
     * שימוש בחומר גלם לייצור מוצר
     * @param material חומר הגלם
     * @param amount כמות לשימוש
     * @return true אם ההשימוש הצליח, false אם לא
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

    // ==================== ניהול מוצרים ====================

    /**
     * יצירת מוצר חדש
     * @param name שם המוצר
     * @param productionCost עלות הייצור
     * @param weight משקל המוצר בק"ג
     * @return המוצר שנוצר
     */
    public Product createProduct(String name, double productionCost, double weight) {
        Product product = new Product(name, productionCost, weight);
        productsInventory.add(product);
        System.out.println("✓ מוצר חדש '" + name + "' נוצר בהצלחה");
        return product;
    }

    /**
     * הוספת מוצר למלאי
     * @param product המוצר להוספה
     */
    public void addProductToInventory(Product product) {
        if (product != null) {
            productsInventory.add(product);
            System.out.println("✓ מוצר '" + product.getName() + "' נוסף למלאי");
        } else {
            System.out.println("✗ שגיאה: לא ניתן להוסיף מוצר null");
        }
    }

    /**
     * הסרת מוצר מהמלאי
     * @param product המוצר להסרה
     * @return true אם הוסר בהצלחה
     */
    public boolean removeProductFromInventory(Product product) {
        if (productsInventory.remove(product)) {
            System.out.println("✓ מוצר '" + product.getName() + "' הוסר מהמלאי");
            return true;
        }
        System.out.println("✗ שגיאה: מוצר לא נמצא במלאי");
        return false;
    }

    /**
     * בדיקת מוצרים פגי תוקף
     */
    public void checkExpiredProducts() {
        System.out.println("\n===== בדיקת מוצרים פגי תוקף =====");
        ArrayList<Product> expiredProducts = new ArrayList<>();
        for (Product product : productsInventory) {
            if (product.isExpired()) {
                expiredProducts.add(product);
                System.out.println("⚠ מוצר פג תוקף: " + product.getName());
            }
        }
        if (expiredProducts.isEmpty()) {
            System.out.println("✓ כל המוצרים בתוקף");
        }
    }

    /**
     * קבלת רשימת כל המוצרים במלאי
     * @return ArrayList של המוצרים
     */
    public ArrayList<Product> getProductsInventory() {
        return productsInventory;
    }

    // ==================== ניהול לקוחות ====================

    /**
     * הוספת לקוח חדש
     * @param clientID מזהה הלקוח
     * @param name שם הלקוח
     * @return הלקוח שנוצר
     */
    public Client addClient(int clientID, String name) {
        Client client = new Client(clientID, name);
        clients.add(client);
        System.out.println("✓ לקוח חדש '" + name + "' נוסף בהצלחה");
        return client;
    }

    /**
     * הוספת מפיץ חדש
     * @param clientID מזהה המפיץ
     * @param name שם המפיץ
     * @param area אזור ההפצה
     * @param licenseNumber מספר רישיון
     * @param distributionPrice מחיר הפצה
     * @return המפיץ שנוצר
     */
    public Distributor addDistributor(int clientID, String name, Distributor.Region area,
                                      String licenseNumber, double distributionPrice) {
        Distributor distributor = new Distributor(clientID, name, area, licenseNumber, distributionPrice);
        distributors.add(distributor);
        clients.add(distributor);
        System.out.println("✓ מפיץ חדש '" + name + "' נוסף בהצלחה באזור " + area);
        return distributor;
    }

    /**
     * חיפוש לקוח לפי ID
     * @param clientID מזהה הלקוח
     * @return הלקוח או null אם לא נמצא
     */
    public Client findClientByID(int clientID) {
        for (Client client : clients) {
            if (client.getCliendID() == clientID) {
                return client;
            }
        }
        return null;
    }

    /**
     * קבלת רשימת כל הלקוחות
     * @return ArrayList של הלקוחות
     */
    public ArrayList<Client> getAllClients() {
        return clients;
    }

    /**
     * קבלת רשימת כל המפיצים
     * @return ArrayList של המפיצים
     */
    public ArrayList<Distributor> getAllDistributors() {
        return distributors;
    }

    // ==================== ניהול הזמנות ====================

    /**
     * יצירת הזמנה חדשה
     * @param client הלקוח שמבקש ההזמנה
     * @param orderDate תאריך ההזמנה
     * @return ההזמנה שנוצרה
     */
    public Order createOrder(Client client, Date orderDate) {
        int newOrderID = orderIDCounter++;
        Order order = new Order(client, orderDate, new java.util.LinkedList<>(), newOrderID);
        System.out.println("✓ הזמנה חדשה #" + newOrderID + " נוצרה ללקוח " + client.getName());
        return order;
    }

    /**
     * הוספת הזמנה לתור
     * @param order ההזמנה להוספה
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
     * @return ההזמנה שעובדה או null אם התור ריק
     */
    public Order processNextOrder() {
        Order order = (Order) ordersQueue.poll();
        if (order != null) {
            totalOrdersProcessed++;
            System.out.println("✓ הזמנה #" + order.getOrderId() + " עובדה בהצלחה");
            return order;
        } else {
            System.out.println("ℹ תור ההזמנות ריק");
            return null;
        }
    }

    /**
     * בדיקת ההזמנה הבאה בתור ללא הסרתה
     * @return ההזמנה הבאה או null אם התור ריק
     */
    public Order peekNextOrder() {
        return (Order) ordersQueue.peek();
    }

    /**
     * בדיקת גודל תור ההזמנות
     * @return מספר ההזמנות בתור
     */
    public int getOrdersQueueSize() {
        return ordersQueue.size();
    }

    /**
     * בדיקה האם תור ההזמנות ריק
     * @return true אם ריק
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
        System.out.println("📦 מוצרים במלאי: " + productsInventory.size());
        System.out.println("👥 לקוחות רשומים: " + clients.size());
        System.out.println("🚚 מפיצים רשומים: " + distributors.size());
        System.out.println("📋 הזמנות בתור: " + ordersQueue.size());
        System.out.println("✅ הזמנות מעובדות: " + totalOrdersProcessed);
        System.out.println("===================================\n");
    }

    /**
     * הדפסת רשימת כל המוצרים במלאי
     */
    public void printAllProducts() {
        System.out.println("\n===== רשימת מוצרים במלאי =====");
        if (productsInventory.isEmpty()) {
            System.out.println("אין מוצרים במלאי");
        } else {
            for (int i = 0; i < productsInventory.size(); i++) {
                System.out.println((i + 1) + ". " + productsInventory.get(i));
            }
        }
        System.out.println("=============================\n");
    }

    /**
     * הדפסת רשימת כל הלקוחות
     */
    public void printAllClients() {
        System.out.println("\n===== רשימת לקוחות =====");
        if (clients.isEmpty()) {
            System.out.println("אין לקוחות רשומים");
        } else {
            for (int i = 0; i < clients.size(); i++) {
                System.out.println((i + 1) + ". " + clients.get(i));
            }
        }
        System.out.println("========================\n");
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
            // בגלל שהתור מממוש בעזרת LinkedList, נדפיס את ההזמנה הקדמית
            Order peek = (Order) ordersQueue.peek();
            if (peek != null) {
                System.out.println("ההזמנה הבאה לעיבוד: " + peek);
            }
        }
        System.out.println("=======================\n");
    }

    /**
     * קבלת סיכום ממלא מקום של המנהל
     */
    public String getManagerStatus() {
        return "\n=== סטטוס מנהל המפעל ===" +
                "\n📊 מוצרים: " + productsInventory.size() +
                "\n👥 לקוחות: " + clients.size() +
                "\n🚚 מפיצים: " + distributors.size() +
                "\n📋 הזמנות בתור: " + ordersQueue.size() +
                "\n✅ סה\"כ הזמנות מעובדות: " + totalOrdersProcessed +
                "\n=======================\n";
    }

    /**
     * קבלת סה"כ הזמנות מעובדות
     * @return מספר ההזמנות שעובדו
     */
    public int getTotalOrdersProcessed() {
        return totalOrdersProcessed;
    }

    /**
     * איפוס מנהל המפעל (מחיקת כל הנתונים)
     */
    public void reset() {
        ordersQueue = new QueueAsList();
        rawMaterialsInventory = new main.dataBase.LinkedList();
        productsInventory.clear();
        clients.clear();
        distributors.clear();
        totalOrdersProcessed = 0;
        orderIDCounter = 1000;
        System.out.println("✓ מנהל המפעל אופס");
    }
}