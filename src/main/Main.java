package main;

import main.baseClasses.*;

import java.time.LocalDateTime;

public class Main
{
    public static void main(String[] args)
    {
        FactoryManager manager = new FactoryManager();

        System.out.println("❖================▽▼▽================❖");
        System.out.println("     FACTORY SYSTEM     ");
        System.out.println("❖================▽▼▽================❖\n");

        // ==========================================
        // ABSTRACT CLASS + INHERITANCE DEMO
        // ==========================================

        RawMaterial corn = new SolidRawMaterial("Corn", 1001, 500, LocalDateTime.now().plusMonths(12),
                                                15, 30);

        RawMaterial oil = new LiquidRawMaterial("Vegetable Oil", 1002, 300, LocalDateTime.now().plusMonths(6),
                                                10, 35);

        RawMaterial peanuts = new SolidRawMaterial("Peanuts", 1003, 67, LocalDateTime.now().plusMonths(7),
                                                10.67, 25);

        RawMaterial honey = new LiquidRawMaterial("Honey", 1004, 5, LocalDateTime.now().plusYears(10),
                                                  15, 30);

        RawMaterial cocoa = new SolidRawMaterial("Cocoa", 1005, 120, LocalDateTime.now().plusMonths(8),
                                                10.67, 25);

        RawMaterial amba = new LiquidRawMaterial("amba", 1006, 5, LocalDateTime.now().plusYears(10),
                15, 1000);


        manager.addRawMaterial(corn);
        manager.addRawMaterial(oil);
        manager.addRawMaterial(peanuts);
        manager.addRawMaterial(honey);
        manager.addRawMaterial(cocoa);
        manager.addRawMaterial(amba);

        manager.printRawMaterial();


        RawMaterial[] materials_for_bamba = {corn, oil};
        RawMaterial[] materials_for_corn = {corn};
        RawMaterial[] materials_for_veg_oil = {oil};
        RawMaterial[] materials_for_amba_filled_bamba = {corn, oil, amba, peanuts};

        Product bamba = new Product(
                "Bamba",
                2.5,
                LocalDateTime.now().plusYears(2),
                30,
                materials_for_bamba , 1001);

        Product veg_oil = new Product(
                "Vegetable oil",
                10,
                LocalDateTime.now().plusYears(3),
                1,
                materials_for_veg_oil, 1002);

        Product packed_corn = new Product(
                "Packed corn",
                10,
                LocalDateTime.now().plusYears(3),
                1,
                materials_for_corn, 1003);

        Product amba_filled_bamba = new Product(
                "Amba filled bamba",
                3.5,
                LocalDateTime.now().plusYears(2),
                30,
                materials_for_amba_filled_bamba , 1067);

        // ==========================================
        // STACK DEMO
        // ==========================================

        bamba.addBatch(new Batch(50, LocalDateTime.now().plusMonths(1)));

        bamba.addBatch(new Batch(100, LocalDateTime.now().plusMonths(6)));

        bamba.addBatch(new Batch(30, LocalDateTime.now().plusDays(10)));

        veg_oil.addBatch(new Batch(50, LocalDateTime.now().plusDays(10)));

        manager.addProduct(bamba);
        manager.addProduct(veg_oil);
        manager.addProduct(packed_corn);
        manager.addProduct(amba_filled_bamba);

        System.out.println("\n=== PRODUCT STOCK ===");
        manager.printProductStock(bamba);
        System.out.println("Checking if the bamba is expired: " + bamba.isExpired());

        // ==========================================
        // INTERFACE DEMO
        // ==========================================

        System.out.println("\n=== VALUABLE INTERFACE ===");
        System.out.printf("%s final value: %.2f %s\n", bamba.getName(), bamba.calcFinalValue(), Valuable.CURRENCY);

        System.out.printf("%s stock value: %.2f %s\n", corn.getName(), corn.calcFinalValue(), Valuable.CURRENCY);

        // ==========================================
        // BST DEMO
        // ==========================================

        Client client1 = new Client(676767, "Noam");
        Client tempClient = new Client(123456, "nahum nehemya robert chikel");
        Distributor distributor1 = new Distributor(770154, "Pitzotzya't Voloch", "2025", 1500);

        manager.addClient(client1);
        manager.addClient(tempClient);
        manager.addClient(distributor1);

        System.out.println("\n=== CLIENT TREE ===");
        manager.printClients();

        System.out.println("\nDeleting client ID: " + tempClient.getClientID());
        manager.deleteClient(tempClient.getClientID());

        System.out.println("\n=== CLIENT TREE AFTER DELETE ===");
        manager.printClients();

        // ==========================================
        // QUEUE DEMO
        // ==========================================

        Order order1 = new Order(client1, LocalDateTime.now(), manager.getNextOrderID());
        Order specialOrder = new Order(distributor1, LocalDateTime.now(), manager.getNextOrderID());

        order1.addProduct(bamba, 100);
        order1.addProduct(veg_oil, 50);
        specialOrder.addProduct(amba_filled_bamba, 10000);

        System.out.println("\nCreating an order for: " + client1.getName());
        manager.addOrder(order1); // adds order to factory order queue
        client1.addOrder(order1); // adds order to the client queue
        System.out.println(client1);

        System.out.println("\nCreating an order for: " + distributor1.getName());
        manager.addOrder(specialOrder);
        distributor1.addOrder(specialOrder);
        System.out.println(distributor1);


        System.out.println("\n=== ORDER INFO ===");
        order1.generateInvoice();


        System.out.println("\nAttempting to delete client with orders: ");
        manager.deleteClient(client1.getClientID());

        System.out.println("\n=== ORDERS QUEUE ===");
        System.out.println("Next Order:");
        System.out.println(manager.peekNextOrder());

        // ==========================================
        // 2D ARRAY DEMO
        // ==========================================

        manager.printProductCatalogMatrix();

        // ==========================================
        // EXCEPTIONS DEMO
        // ==========================================

        System.out.println("\n=== EXCEPTION HANDLING ===");
        try
        {
            System.out.println("Attempting to add a null Product to order1...");
            order1.addProduct(null, 0);
        }
        catch (Exception e)
        {
            System.out.println("Caught Exception in Order: " + e.getMessage() + "\n");
        }

        try
        {
            System.out.println("Attempting to add a null Order to Client1... ");
            client1.addOrder(null);
        }
        catch (Exception e)
        {
            System.out.println("Caught Exception in Client: " + e.getMessage());
        }

        System.out.println("\n❖================▽▼▽================❖");
        System.out.println("       DEMO COMPLETED");
        System.out.println("❖================▽▼▽================❖\n");

        Menu menu = new Menu(manager);
        menu.run();
    }
}