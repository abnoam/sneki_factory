package main;

public class Main {
    public static void main(String[] args) {
        // 1. Initialize the central factory manager
        FactoryManager manager = new FactoryManager();

        // 2. Initialize the menu interface, passing the manager to it
        Menu menu = new Menu(manager);

        // 3. Start the application loop
        menu.run();
    }
}