package main;

import main.baseClasses.*;
import main.dataStructures.LinkedList;
import main.dataStructures.LinkedNode;
import main.dataStructures.QueueAsList;

import java.util.Scanner;
import java.time.LocalDateTime;

public class Menu {
    private final FactoryManager manager;
    private final Scanner scanner;

    public Menu(FactoryManager manager) {
        this.manager = manager;
        this.scanner = new Scanner(System.in);
    }

    public void run() {
        boolean running = true;
        while (running)
        {
            clearScreen();
            System.out.println("\n=== Sneki Factory - Main Menu ===");
            System.out.println("1. Client Management");
            System.out.println("2. Order Management");
            System.out.println("3. Inventory & Batches Management");
            System.out.println("4. Raw Materials Management");
            System.out.println("5. Distributor Management");
            System.out.println("6. Print Product Catalog");
            System.out.println("0. Exit");

            String choice = readString("Select an option: ");

            switch (choice) {
                case "1":
                    clearScreen();
                    clientMenu();
                break;
                case "2":
                    clearScreen();
                    orderMenu();
                break;
                case "3":
                    clearScreen();
                    inventoryMenu();
                break;
                case "4":
                    clearScreen();
                    rawMaterialsMenu();
                break;
                case "5":
                    clearScreen();
                    distributorMenu();
                break;
                case "6":
                    clearScreen();
                    manager.printProductCatalogMatrix();
                break;
                case "67":
                    System.out.print("\u001B[32m");
                    System.out.println("""
                    ███████╗███╗   ██╗███████╗██╗  ██╗██╗
                    ██╔════╝████╗  ██║██╔════╝██║ ██╔╝██║
                    ███████╗██╔██╗ ██║█████╗  █████╔╝ ██║
                    ╚════██║██║╚██╗██║██╔══╝  ██╔═██╗ ██║
                    ███████║██║ ╚████║███████╗██║  ██╗██║
                    ╚══════╝╚═╝  ╚═══╝╚══════╝╚═╝  ╚═╝╚═╝
                    
                    """);


                        System.out.println("===========================\n" +
                                "       Version 67.67\n" +
                                "Tested on 67 different bugs\n" +
                                "   Successfully fixed: 1\n" +
                                "===========================\n");
                        System.out.println(" ██████╗                 ███████╗\n" +
                                            "██╔════╝                  ╚════██║\n" +
                                            "███████╗                     ██╔╝\n" +
                                            "██╔═══██╗                   ██╔╝\n" +
                                            "╚██████╔╝                   ██║\n" +
                                            " ╚═════╝                    ╚═╝\n" +
                                            "     \\                     /     \n" +
                                            "      \\\\                 //\n" +
                                            "       \\\\               //\n" +
                                            "        \\\\      \uD83D\uDE00     //\n" +
                                            "         \\\\    ╱│╲    //\n" +
                                            "          \\\\  ╱ │ ╲  //\n" +
                                            "           \\\\╱  │  ╲//\n" +
                                            "               ╱ ╲\n" +
                                            "              ╱   ╲\n" +
                                            "\n");
                    System.out.print("\u001B[0m");
                break;
                case "0":
                    clearScreen();
                    System.out.println("Shutting down the system. Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    // ==========================
    // 1. CLIENT MENU
    // ==========================
    private void clientMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- Client Management ---");
            System.out.println("1. Add Client");
            System.out.println("2. Delete Client");
            System.out.println("3. Edit Client Details");
            System.out.println("4. Print All Clients");
            System.out.println("5. Show Client Orders");
            System.out.println("6. Back to Main Menu");

            String choice = readString("Select an option: ");

            switch (choice)
            {
                case "1": {
                    int idToAdd = readInt("Enter new Client ID (6 digits): ");
                    if (idToAdd <= 100000 || idToAdd >= 999999)
                    {
                        System.out.println("Error: Client ID must be exactly 6 digits.");
                    }
                    else if (manager.findClient(idToAdd) != null)
                    {
                        System.out.println("Error: Client ID already exists.");
                    }
                    else
                    {
                        String name = readString("Enter Client Name: ");
                        manager.addClient(new Client(idToAdd, name));
                        System.out.println("Client added successfully.");
                    }
                    break;
                }
                case "2": {
                    int idToDel = readInt("Enter Client ID to delete: ");
                    manager.deleteClient(idToDel);
                    break;
                }
                case "3": {
                    int idToEdit = readInt("Enter Client ID to edit: ");
                    Client c = manager.findClient(idToEdit);
                    if (c != null) {
                        String newName = readString("Enter new name (current: " + c.getName() + "): ");
                        c.setName(newName);
                        System.out.println("Client updated successfully.");
                    } else {
                        System.out.println("Error: Client not found.");
                    }
                    break;
                }
                case "4": {
                    System.out.println("\n--- Registered Clients ---");
                    manager.printClients();
                    break;
                }
                case "5":
                {
                    int id = readInt("Enter Client ID: ");

                    Client client = manager.findClient(id);

                    if(client != null)
                    {
                        if(client.getOrdersQueue().isEmpty())
                        {
                            System.out.println(
                                    "This client has no orders.");
                        }
                        else
                        {
                            System.out.println(
                                    "\n=== Orders of "
                                            + client.getName()
                                            + " ===");

                            QueueAsList temp =
                                    new QueueAsList();

                            while(!client.getOrdersQueue().isEmpty())
                            {
                                Order order =
                                        (Order) client.getOrdersQueue().pull();

                                order.printOrderDetails();

                                System.out.println("--------------------------------");

                                temp.offer(order);
                            }

                            while(!temp.isEmpty())
                            {
                                client.getOrdersQueue().offer(
                                        temp.pull());
                            }
                        }
                    }
                    else
                    {
                        System.out.println("Client not found.");
                    }

                    break;
                }

                case "6":
                    back = true;
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    // ==========================
    // 2. ORDER MENU
    // ==========================
    private void orderMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- Order Management ---");
            System.out.println("1. Create New Order");
            System.out.println("2. Process Next Order");
            System.out.println("3. View Next Pending Order");
            System.out.println("4. Back to Main Menu");

            String choice = readString("Select an option: ");

            switch (choice)
            {
                case "1":
                {
                    int clientId = readInt("Enter Client ID for this order: ");

                    Client client = manager.findClient(clientId);

                    if (client != null)
                    {
                        int orderId = manager.getNextOrderID();

                        Order newOrder = new Order(client, LocalDateTime.now(), orderId);

                        int productsAdded = 0;

                        boolean addingProducts = true;

                        System.out.println("\n--- Adding Products to Order #" + orderId + " ---");

                        while (addingProducts) {
                            String pName = readString("Enter product name to add (or type 'done' to finish): ");

                            if (pName.equals("Done"))
                            {
                                addingProducts = false;
                                continue;
                            }

                            Product p = searchProductByName(pName);

                            if (p != null) {
                                int qty = readInt("Enter quantity for " + pName + ": ");

                                if (qty > 0)
                                {
                                    newOrder.addProduct(p, qty);

                                    productsAdded++;

                                    System.out.println(qty + " units of '" + pName + "' added to the order.");
                                } else {
                                    System.out.println(
                                            "Quantity must be greater than zero.");
                                }
                            } else {
                                System.out.println("Error: Product '" + pName + "' not found in inventory.");
                            }
                        }

                        if (productsAdded > 0) {
                            manager.addOrder(newOrder);
                            client.addOrder(newOrder);

                            System.out.println(
                                    "\nOrder #" + orderId + " created successfully for " + client.getName() + ".");
                        } else
                        {
                            System.out.println("\nOrder creation cancelled: No products were added.");
                        }
                    } else {
                        System.out.println("Error: Client not found.");
                    }

                    break;
                }
                case "2":
                {
                    Order processed = manager.processNextOrder();
                    if (processed != null)
                        processed.generateInvoice();
                    break;
                }
                case "3":
                {
                    Order peeked = manager.peekNextOrder();
                    if (peeked != null) {
                        System.out.println("Next pending order: " + peeked.toString());
                    } else {
                        System.out.println("No pending orders.");
                    }
                    break;
                }
                case "4":
                {
                    back = true;
                    break;
                }
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    // ==========================
    // 3. INVENTORY & BATCHES MENU
    // ==========================

    private void inventoryMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- Inventory & Batches Management ---");
            System.out.println("1. Add New Product Line (Requires Raw Materials)");
            System.out.println("2. Delete Product Line");
            System.out.println("3. Add Batch to Existing Product");
            System.out.println("4. View Total Stock Of Product");
            System.out.println("5. Back to Main Menu");

            String choice = readString("Select an option: ");

            switch (choice)
            {
                case "1":
                    int sNum = readInt("Enter Serial Number: ");
                    if (manager.isProductInCatalog(sNum))
                    {
                        System.out.println("Error: Product with Serial Number " + sNum + " already exists!");
                    }
                    else
                    {
                        String pName = readString("Enter product name: ");
                        double cost = readDouble("Enter production cost: ");
                        double weight = readDouble("Enter product weight: ");
                        Product p = new Product(pName, cost, weight);
                        p.setSerialNumber(sNum);

                        System.out.println("--- Assigning Raw Materials to " + pName + " ---");
                        boolean addingMaterials = true;
                        int rawMaterialsCount = 0;

                        while (addingMaterials)
                        {
                            String rawName = readString("Enter raw material name (or 'done' to finish): ");
                            if (rawName.equals("Done"))
                            {
                                addingMaterials = false;
                                continue;
                            }

                            RawMaterial rm = searchRawMaterialByName(rawName);
                            if (rm != null)
                            {
                                p.addRawMaterial(rm);
                                rawMaterialsCount++; // מעדכנים שהוכנס חומר גלם
                                System.out.println("Added '" + rm.getName() + "' to the product's formulation.");
                            } else
                            {
                                System.out.println("Error: Raw Material '" + rawName + "' not found.");
                            }
                        }

                        // ensure there is atleast 1 raw material
                        if (rawMaterialsCount > 0)
                        {
                            manager.addProduct(p);
                            System.out.println("Product line '" + pName + "' created and added to the catalog.");
                        }
                        else
                        {
                            System.out.println("Error: Product creation cancelled. A product must contain at least one raw material.");
                        }
                    }
                    break;

                case "2": // לוגיקת המחיקה החדשה
                    String delName = readString("Enter product name to delete: ");
                    if (manager.deleteProduct(delName))
                    {
                        System.out.println("Product '" + delName + "' was successfully deleted from inventory.");
                    } else {
                        System.out.println("Error: Product '" + delName + "' not found in inventory.");
                    }
                    break;

                case "3":
                    Product toAddBatch = searchProductByName(readString("Enter product name to add a batch: "));
                    if (toAddBatch != null)
                    {
                        int batchQty = readInt("Enter batch quantity: ");
                        int daysToExpire = readInt("Enter days until expiration: ");
                        Batch newBatch = new Batch(batchQty, LocalDateTime.now().plusDays(daysToExpire));
                        if(toAddBatch.addBatch(newBatch))
                        {
                            System.out.println("Batch added successfully to " + toAddBatch.getName() + ".");
                            System.out.println("Updated Total Stock: " + manager.getProductTotalStock(toAddBatch));
                        }
                    }
                    else
                    {
                        System.out.println("Product not found.");
                    }
                    break;

                case "4":
                    Product toView = searchProductByName(readString("Enter product name to view batches: "));
                    if (toView != null) {
                        manager.printProductStock(toView);
                    } else {
                        System.out.println("Product not found.");
                    }
                    break;

                case "5":
                    back = true;
                    break;

                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    // ==========================
    // 4. RAW MATERIALS MENU
    // ==========================
    private void rawMaterialsMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- Raw Materials Management ---");
            System.out.println("1. Add Liquid Material");
            System.out.println("2. Add Solid Material");
            System.out.println("3. Back to Main Menu");

            String choice = readString("Select an option: ");

            switch (choice) {
                case "1":
                    int sNum = readInt("Enter Serial Number: ");
                    double qty = readDouble("Enter Quantity (Liters): ");
                    double price = readDouble("Enter Purchase Price: ");
                    String lName = readString("Enter Material Name: ");

                    LiquidRawMaterial liquid = new LiquidRawMaterial(lName, sNum, qty, LocalDateTime.now().plusMonths(3), price, 5.0);
                    manager.addRawMaterial(liquid);
                    System.out.println("Liquid material '" + lName + "' added.");
                    break;
                case "2":
                    int sNumS = readInt("Enter Serial Number: ");
                    double qtyS = readDouble("Enter Quantity (Kg): ");
                    double priceS = readDouble("Enter Purchase Price: ");
                    String sName = readString("Enter Material Name: ");

                    SolidRawMaterial solid = new SolidRawMaterial(sName, sNumS, qtyS, LocalDateTime.now().plusMonths(6), priceS, qtyS);
                    manager.addRawMaterial(solid);
                    System.out.println("Solid material '" + sName + "' added.");
                    break;
                case "3":
                    back = true;
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    // ==========================
    // 5. DISTRIBUTOR MENU
    // ==========================
    private void distributorMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- Distributor Management ---");
            System.out.println("1. Add Distributor");
            System.out.println("2. Record New Shipment");
            System.out.println("3. Back to Main Menu");

            String choice = readString("Select an option: ");

            switch (choice) {
                case "1":
                    int dId = readInt("Enter Distributor ID: ");
                    String dName = readString("Enter Distributor Name: ");
                    String license = readString("Enter License Number: ");
                    double price = readDouble("Enter Distribution Price: ");

                    Distributor dist = new Distributor(dId, dName, license, price);
                    manager.addClient(dist);
                    System.out.println("Distributor added successfully to the clients tree.");
                    break;
                case "2":
                    int searchId = readInt("Enter Distributor ID: ");
                    Client found = manager.findClient(searchId);
                    if (found instanceof Distributor) {
                        ((Distributor) found).recordNewShipment();
                        System.out.println("New shipment recorded for distributor: " + found.getName() + ".");
                    } else if (found != null) {
                        System.out.println("Error: Client found, but it is not registered as a Distributor.");
                    } else {
                        System.out.println("Error: Distributor not found.");
                    }
                    break;
                case "3":
                    back = true;
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    // ==========================
    // UTILITY METHODS
    // ==========================

    private Product searchProductByName(String name) {
        LinkedList inventory = manager.getproductsCatalog();
        if (inventory == null || inventory.isEmpty()) return null;

        LinkedNode current = inventory.getFirst();
        while (current != null) {
            Product p = (Product) current.getData();
            if (p.getName().equalsIgnoreCase(name)) {
                return p;
            }
            current = current.getNext();
        }
        return null;
    }

    private RawMaterial searchRawMaterialByName(String name)
    {
        LinkedList inventory = manager.getRawMaterialsInventory();
        if (inventory == null || inventory.isEmpty()) return null;

        LinkedNode current = inventory.getFirst();
        while (current != null) {
            RawMaterial rm = (RawMaterial) current.getData();
            if (rm.getName() != null && rm.getName().equalsIgnoreCase(name)) {
                return rm;
            }
            current = current.getNext();
        }
        return null;
    }

    private String readString(String s)
    {
        System.out.print(s);
        String input = scanner.nextLine().trim();
        while (input.isEmpty())
        {
            input = scanner.nextLine().trim();
        }

        return formatName(input);
    }

    private String formatName(String input)
    {
        if (input == null || input.isEmpty()) // ensure the input is not empty
        {
            return input;
        }

        char firstLetter = input.charAt(0); //get first char from the string
        firstLetter = Character.toUpperCase(firstLetter); // turn char to uppercase

        if (input.length() == 1)
        {
            return String.valueOf(firstLetter); //converts a single char to string.
        }

        return firstLetter + input.substring(1).toLowerCase();
    }

    private int readInt(String s)
    {
        while (true)
        {
            System.out.print(s);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Error: Please enter a valid integer number.");
            }
        }
    }

    private double readDouble(String s)
    {
        while (true)
        {
            System.out.print(s);
            try
            {
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Error: Please enter a valid decimal number.");
            }
        }
    }

    public static void clearScreen()
    {
        System.out.println();
        System.out.println();

        System.out.println("❖================▽▼▽================❖");

        System.out.println();
    }
}