package main;

import main.baseClasses.*;

import java.time.LocalDateTime;

public class Main
{
    public static void main(String[] args)
    {
        FactoryManager manager = new FactoryManager();

        System.out.println("\n❖================▽▼▽================❖");
        System.out.println("     FACTORY SYSTEM     ");
        System.out.println("❖================▽▼▽================❖\n");

        // ==========================================
        // ABSTRACT CLASS + INHERITANCE DEMO
        // ==========================================

        RawMaterial corn = new SolidRawMaterial("Corn", 1001, 500, LocalDateTime.now().plusMonths(12),
                                                15, 250,
                                                "Pallet", true);

        RawMaterial oil = new LiquidRawMaterial("Vegetable Oil", 1002, 800, LocalDateTime.now().plusMonths(6),
                                                10, 500,
                                                20, "Tank");



        RawMaterial nuts = new SolidRawMaterial("nuts", 1003,
                                                67, LocalDateTime.now().plusMonths(7),
                                                10.67, 500,
                                                "Pallet", false);



        manager.addRawMaterial(corn);
        manager.addRawMaterial(oil);

        System.out.println("=== RAW MATERIALS ===");
        System.out.println(corn);
        System.out.println(oil);

        // ==========================================
        // 1D ARRAY DEMO
        // ==========================================

        RawMaterial[] materials_for_bamba = {corn, oil};
        RawMaterial[] materials_for_peanut_butter = {oil, nuts};

        Product bamba = new Product(
                "Bamba",
                2.5,
                LocalDateTime.now().plusYears(2),
                30,
                materials_for_bamba , 1001);

        Product veg_oil = new Product(
                "Sunflower oil",
                10,
                LocalDateTime.now().plusYears(3),
                1,
                materials_for_peanut_butter, 1002);

        // ==========================================
        // STACK DEMO (BATCHES)
        // ==========================================

        bamba.addBatch(new Batch(50, LocalDateTime.now().plusMonths(1)));

        bamba.addBatch(new Batch(100, LocalDateTime.now().plusMonths(6)));

        bamba.addBatch(new Batch(30, LocalDateTime.now().plusDays(10)));

        manager.addProduct(bamba);
        manager.addProduct(veg_oil);

        System.out.println("\n=== PRODUCT STOCK (STACK) ===");
        manager.printProductStock(bamba);

        // ==========================================
        // INTERFACE DEMO
        // ==========================================

        System.out.println("\n=== VALUABLE INTERFACE ===");
        System.out.printf("%s final value: %.2f %s\n", bamba.getName(), bamba.calcFinalValue(), Valuable.CURRENCY);

        System.out.printf("%s stock value: %.2f %s\n", corn.getName(), corn.calcFinalValue(), Valuable.CURRENCY);

        // ==========================================
        // BST DEMO
        // ==========================================

        Client client1 = new Client(1, "Noam");
        Client tempClient = new Client(99, "Agam");
        Distributor distributor = new Distributor(2, "Shufersal Distributor",
                                                   Distributor.Region.CENTER, "2025", 1500);

        manager.addClient(client1);
        manager.addClient(tempClient);
        manager.addClient(distributor);

        System.out.println("\n=== CLIENT TREE (BST) ===");
        manager.printClients();

        System.out.println("\n=== TESTING CLIENT DELETION ===");
        System.out.println("Attempting to delete client ID: " + client1.getClientID() + " (who has an order)...\n");
        manager.deleteClient(client1.getClientID());
        
        System.out.println("\nAttempting to delete client ID: " + tempClient.getClientID() + " (who has NO orders)...\n");
        manager.deleteClient(tempClient.getClientID());
        
        System.out.println("\n=== CLIENT TREE AFTER DELETION ATTEMPTS ===");
        manager.printClients();

        // ==========================================
        // QUEUE DEMO
        // ==========================================

        Order order1 = new Order(client1, LocalDateTime.now(), manager.getNextOrderID());

        order1.addProduct(bamba);
        order1.addProduct(veg_oil);

        manager.addOrder(order1);

        System.out.println("\n=== ORDERS QUEUE ===");
        System.out.println("Next Order:");
        System.out.println(manager.peekNextOrder());

        System.out.println("\nProcessing Order...");
        System.out.println(manager.processNextOrder());

        // ==========================================
        // 2D ARRAY & LINKED LIST
        // ==========================================

        manager.printProductCatalogMatrix();



        // ==========================================
        // EXCEPTIONS DEMO
        // ==========================================

        System.out.println("\n=== EXCEPTION HANDLING ===");
        try
        {
            System.out.println("Attempting to add a null Product to order1...");
            order1.addProduct(null);
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