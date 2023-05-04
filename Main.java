import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Inventory inventory = Inventory.getInstance();
        LowStockNotifier lowStockNotifier = new LowStockNotifier(inventory, 5);
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- Inventory Management System ---");
            System.out.println("1. Add item");
            System.out.println("2. Remove item");
            System.out.println("3. Update item");
            System.out.println("4. Generate report");
            System.out.println("5. Exit");
            System.out.print("\nChoose an option (1-5): ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline left-over

            switch (choice) {
                case 1:
                    // Add item
                    System.out.println("\n--- Add Item ---");
                    addItem(scanner, inventory);
                    break;
                case 2:
                    // Remove item
                    System.out.println("\n--- Remove Item ---");
                    removeItem(scanner, inventory);
                    break;
                case 3:
                    // Update item
                    System.out.println("\n--- Update Item ---");
                    updateItem(scanner, inventory);
                    break;
                case 4:
                    // Generate report
                    System.out.println("\n--- Generate Report ---");
                    generateReport(scanner, inventory);
                    break;
                case 5:
                    // Exit
                    System.out.println("\nExiting...");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("\nInvalid option. Please choose a number between 1 and 5.");
            }
        }
    }

    public static void addItem(Scanner scanner, Inventory inventory) {
        while (true) {
            try {
                System.out.print("Enter item ID (or type 'back' to go back): ");
                String input = scanner.nextLine();

                if (input.equalsIgnoreCase("back")) {
                    return;
                }

                int id = Integer.parseInt(input);

                System.out.print("Enter item name: ");
                String name = scanner.nextLine();

                System.out.print("Enter item price: ");
                double price = scanner.nextDouble();
                if (price < 0) {
                    throw new IllegalArgumentException("Price cannot be negative.");
                }

                System.out.print("Enter item stock level: ");
                int stockLevel = scanner.nextInt();
                if (stockLevel < 0) {
                    throw new IllegalArgumentException("Stock level cannot be negative.");
                }
                scanner.nextLine(); // Consume newline left-over

                Item item = new Item(id, name, price, stockLevel);
                InventoryCommand command = new AddItemCommand(inventory, item);
                boolean itemAdded = ((AddItemCommand) command).getInventory().addItem(item);
                if (itemAdded) {
                    command.execute();
                } else {
                    System.out.println("ID already exists. Returning to the main menu.");
                }

                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static void removeItem(Scanner scanner, Inventory inventory) {
        while (true) {
            try {
                System.out.println(
                        "Enter the ID of the item you want to remove, or type 'back' to return to the main menu:");
                String input = scanner.next();
                if (input.equalsIgnoreCase("back")) {
                    break;
                }

                int id = Integer.parseInt(input);
                if (inventory.getItem(id) == null) {
                    System.out.println("Item not found. Please try again.");
                    continue;
                }

                InventoryCommand command = new RemoveItemCommand(inventory, id);
                command.execute();

                System.out.println("Item removed successfully.");
                break;
            } catch (NumberFormatException e) {
                System.out.println(
                        "Invalid input. Please enter a valid item ID or type 'back' to return to the main menu.");
            }
        }
    }

    public static void updateItem(Scanner scanner, Inventory inventory) {
        while (true) {
            try {
                System.out.print("Enter item ID (or type 'back' to go back): ");
                String input = scanner.nextLine();

                if (input.equalsIgnoreCase("back")) {
                    return;
                }

                int id = Integer.parseInt(input);

                System.out.print("Enter new item name: ");
                String name = scanner.nextLine();

                System.out.print("Enter new item price: ");
                double price = scanner.nextDouble();
                if (price < 0) {
                    throw new IllegalArgumentException("Price cannot be negative.");
                }

                System.out.print("Enter new item stock level: ");
                int stockLevel = scanner.nextInt();
                if (stockLevel < 0) {
                    throw new IllegalArgumentException("Stock level cannot be negative.");
                }
                scanner.nextLine(); // Consume newline left-over

                Item newItem = new Item(id, name, price, stockLevel);
                InventoryCommand command = new UpdateItemCommand(inventory, newItem);
                command.execute();

                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static Report generateReport(Scanner scanner, Inventory inventory) {
        ReportGenerator reportGenerator = null;

        while (reportGenerator == null) {
            System.out.println("Select report type:");
            System.out.println("1. Total Stock Value");
            System.out.println("2. Total Items");
            System.out.println("3. Item Details");
            System.out.println(
                    "Enter the number corresponding to the report type, or type 'back' to return to the main menu:");

            String input = scanner.next();
            scanner.nextLine();

            if (input.equalsIgnoreCase("back")) {
                return null;
            }

            switch (input) {
                case "1":
                    reportGenerator = new TotalStockValueReportGenerator();
                    break;
                case "2":
                    reportGenerator = new TotalItemsReportGenerator();
                    break;
                case "3":
                    reportGenerator = new ItemDetailsReportGenerator();
                    break;
                default:
                    System.out.println("Invalid selection. Please try again.");
            }
        }
        Report generatedReport = reportGenerator.generate(inventory);
        System.out.println(generatedReport);

        return generatedReport;
    }

}
