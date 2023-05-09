import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Inventory inventory = Inventory.getInstance();
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n--- Inventory Management System ---");

        int lowStockThreshold = -1;
        while (lowStockThreshold < 0) {
            try {
                System.out.print("\nEnter low stock threshold for items in the inventory: ");
                lowStockThreshold = scanner.nextInt();
                scanner.nextLine(); // Consume newline left-over
                if (lowStockThreshold < 0) {
                    System.out.println("Invalid input. Threshold must be a non-negative integer.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid non-negative integer.");
                scanner.nextLine(); // Consume invalid input
            }
        }

        LowStockNotifier lowStockNotifier = new LowStockNotifier(inventory, lowStockThreshold);
        inventory.addObserver(lowStockNotifier);
        ;

        while (true) {
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
                    addItem(scanner, inventory, lowStockNotifier);
                    break;
                case 2:
                    // Remove item
                    System.out.println("\n--- Remove Item ---");
                    removeItem(scanner, inventory);
                    break;
                case 3:
                    // Update item
                    System.out.println("\n--- Update Item ---");
                    updateItem(scanner, inventory, lowStockNotifier);
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

    public static void addItem(Scanner scanner, Inventory inventory, LowStockNotifier lowStockNotifier) {
        while (true) {
            try {
                System.out.print("\nEnter item ID (or type 'back' to go back): ");
                String input = scanner.nextLine();
                if (input.equalsIgnoreCase("back")) {
                    return;
                }

                int id = Integer.parseInt(input);
                if (inventory.getItem(id) != null) {
                    System.out.println("ID already exists. Returning to the main menu.");
                    return;
                }

                System.out.print("Enter item name (or type 'back' to go back): ");
                String name = scanner.nextLine();
                if (name.equalsIgnoreCase("back")) {
                    return;
                }

                System.out.print("Enter item price (or type 'back' to go back): ");
                input = scanner.nextLine();
                if (input.equalsIgnoreCase("back")) {
                    return;
                }
                double price = Double.parseDouble(input);
                if (price < 0) {
                    throw new IllegalArgumentException("Price cannot be negative.");
                }

                System.out.print("Enter item stock level (or type 'back' to go back): ");
                input = scanner.nextLine();
                if (input.equalsIgnoreCase("back")) {
                    return;
                }
                int stockLevel = Integer.parseInt(input);
                if (stockLevel < 0) {
                    throw new IllegalArgumentException("Stock level cannot be negative.");
                }

                Item item = new Item(id, name, price, stockLevel);
                InventoryCommand command = new AddItemCommand(inventory, item);
                boolean itemAdded = ((AddItemCommand) command).getInventory().addItem(item);
                if (itemAdded) {
                    command.execute();
                    lowStockNotifier.update(item);
                    System.out.println("\nItem added successfully.\n");
                } else {
                    System.out.println("Unable to add item. Returning to the main menu.");
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
                        "\nEnter the ID of the item you want to remove, or type 'back' to return to the main menu:");
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

                System.out.println("\nItem removed successfully.\n");
                break;
            } catch (NumberFormatException e) {
                System.out.println(
                        "Invalid input. Please enter a valid item ID or type 'back' to return to the main menu.");
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid non-negative integer.");
                scanner.nextLine(); // Consume invalid input
            }
        }
    }

    public static void updateItem(Scanner scanner, Inventory inventory, LowStockNotifier lowStockNotifier) {
        while (true) {
            try {
                System.out.print("\nEnter item ID (or type 'back' to go back): ");
                String input = scanner.nextLine();

                if (input.equalsIgnoreCase("back")) {
                    return;
                }

                int id = Integer.parseInt(input);
                if (inventory.getItem(id) == null) {
                    System.out.println("Item with given ID doesn't exist. Returning to the main menu.");
                    return;
                }

                System.out.print("Enter new item name (or type 'back' to go back): ");
                String name = scanner.nextLine();
                if (name.equalsIgnoreCase("back")) {
                    return;
                }

                System.out.print("Enter new item price (or type 'back' to go back): ");
                input = scanner.nextLine();
                if (input.equalsIgnoreCase("back")) {
                    return;
                }
                double price = Double.parseDouble(input);
                if (price < 0) {
                    throw new IllegalArgumentException("Price cannot be negative.");
                }

                System.out.print("Enter new item stock level (or type 'back' to go back): ");
                input = scanner.nextLine();
                if (input.equalsIgnoreCase("back")) {
                    return;
                }
                int stockLevel = Integer.parseInt(input);
                if (stockLevel < 0) {
                    throw new IllegalArgumentException("Stock level cannot be negative.");
                }

                Item newItem = new Item(id, name, price, stockLevel);
                InventoryCommand command = new UpdateItemCommand(inventory, newItem);
                command.execute();
                System.out.println("\nItem updated successfully.\n");
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
            System.out.println("\nSelect report type:");
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
