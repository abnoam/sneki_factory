package main;

import main.baseClasses.*;

import java.time.LocalDateTime;

public class Main
{
    public static void main(String[] args)
    {
        FactoryManager manager = new FactoryManager();

        System.out.println("================================");
        System.out.println("     SNEKI FACTORY TEST");
        System.out.println("================================");

        // ==========================
        // CLIENTS
        // ==========================

        Client noam = new Client(1001, "Noam");
        Client yossi = new Client(1002, "Yossi");
        Client dan = new Client(1003, "Dan");

        manager.addClient(noam);
        manager.addClient(yossi);
        manager.addClient(dan);

        System.out.println("\n--- CLIENT TREE ---");
        manager.printClients();

        System.out.println("\n--- SEARCH CLIENT ---");
        Client found = manager.findClient(1002);

        if(found != null)
        {
            System.out.println(found);
        }

        // ==========================
        // RAW MATERIALS
        // ==========================

        LiquidRawMaterial oil =
                new LiquidRawMaterial(
                        1,
                        100,
                        LocalDateTime.now().plusMonths(3),
                        5,
                        100,
                        8,
                        "Tank");

        oil.setName("Sunflower Oil");

        SolidRawMaterial corn =
                new SolidRawMaterial(
                        2,
                        200,
                        LocalDateTime.now().plusMonths(6),
                        3,
                        200,
                        "Standard",
                        false);

        corn.setName("Corn");

        manager.addRawMaterial(oil);
        manager.addRawMaterial(corn);

        System.out.println("\n--- RAW MATERIALS ---");
        System.out.println(oil);
        System.out.println(corn);

        System.out.println("\n--- MATERIAL USAGE TEST ---");

        oil.useMaterial(20);

        System.out.println(
                oil.getName() +
                        " remaining stock: " +
                        oil.checkStock());

        // ==========================
        // PRODUCTS
        // ==========================

        Product bamba =
                new Product(
                        "Bamba",
                        8.5,
                        0.08);

        bamba.addRawMaterial(oil);
        bamba.addRawMaterial(corn);

        Product bisli =
                new Product(
                        "Bisli",
                        7.0,
                        0.07);

        bisli.addRawMaterial(corn);

        manager.addProduct(bamba);
        manager.addProduct(bisli);

        System.out.println("\n--- PRODUCTS ---");
        System.out.println(bamba);
        System.out.println(bisli);

        // ==========================
        // ORDER
        // ==========================

        Order order1 =
                new Order(
                        noam,
                        LocalDateTime.now(),
                        manager.getNextOrderID());

        order1.addProduct(bamba);
        order1.addProduct(bisli);

        noam.addOrder(order1);

        manager.addOrder(order1);

        System.out.println("\n--- ORDER CREATED ---");
        System.out.println(order1);

        // ==========================
        // CLIENT QUEUE
        // ==========================

        System.out.println("\n--- CLIENT QUEUE TEST ---");

        System.out.println(
                "Next Order: "
                        + noam.peekNextOrder());

        Order processedClientOrder =
                noam.processNextOrder();

        System.out.println(
                "Processed Client Order: "
                        + processedClientOrder);

        // ==========================
        // FACTORY QUEUE
        // ==========================

        System.out.println("\n--- FACTORY ORDER QUEUE ---");

        System.out.println(
                "Next Factory Order: "
                        + manager.peekNextOrder());

        Order processedFactoryOrder =
                manager.processNextOrder();

        System.out.println(
                "Processed Factory Order: "
                        + processedFactoryOrder);

        // ==========================
        // INVOICE
        // ==========================

        System.out.println("\n--- INVOICE ---");

        processedFactoryOrder.generateInvoice();

        // ==========================
        // STACK
        // ==========================

        System.out.println("\n--- ALERT STACK ---");

        manager.addAlert("Low stock: Oil");
        manager.addAlert("New product added");
        manager.addAlert("Order processed");

        System.out.println(
                "Last Alert: "
                        + manager.getLastAlert());

        System.out.println(
                "Removed Alert: "
                        + manager.removeLastAlert());

        System.out.println(
                "Current Last Alert: "
                        + manager.getLastAlert());

        // ==========================
        // STATUS
        // ==========================

        System.out.println("\n--- FACTORY STATUS ---");

        manager.printSystemStatus();

        System.out.println("\n================================");
        System.out.println("       TEST COMPLETED");
        System.out.println("================================");
    }
}