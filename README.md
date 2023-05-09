# Inventory Management System

This project is an inventory management system designed for retail stores or warehouses. It keeps track of stock levels of various items and provides functionality for adding, removing, and updating items in the inventory. Notifications are sent when an item's stock reaches a low threshold, and users can generate reports for a high-level overview of the inventory.

## Classes and Objects

### Item

Represents an individual item with properties like ID, name, price, and stock level.

- Attributes: id (int), name (String), price (double), stockLevel (int)

### Inventory

Represents the entire inventory, holding a collection of items and providing methods to manage these items.

- Attributes: instance (static, Inventory), items (Map<int, Item>)
- Methods: getInstance(), addItem(Item), removeItem(int), updateItem(Item), getItem(int), getItems()

### InventoryCommand (Abstract)

Abstract class representing a command to perform an operation on the inventory. 

- Methods: execute(), undo()

#### AddItemCommand

Extends InventoryCommand, representing a command to add an item.

- Attributes: item (Item)
- Methods: getInventory()

#### RemoveItemCommand

Extends InventoryCommand, representing a command to remove an item.

- Attributes: item (Item)

#### UpdateItemCommand

Extends InventoryCommand, representing a command to update an item.

- Attributes: oldItem (Item), newItem (Item)

### LowStockNotifier

Observes the inventory and sends notifications when an item's stock is low.

- Attributes: inventory (Inventory), lowStockThreshold (int)
- Methods: update(Item), notifyLowStock(Item)

### Report

Represents a generated report with specific information about the inventory.

- Attributes: title (String), content (String)
- Methods: getTitle(), setTitle(String), getContent(), setContent(String), toString()

### ReportGenerator (Interface)

Provides methods to generate different types of reports based on specified parameters.

- Methods: generateReport(Inventory)

#### TotalStockValueReportGenerator

Implements ReportGenerator to generate a report with the total value of all stock.

#### TotalItemsReportGenerator

Implements ReportGenerator to generate a report with the total number of items in the inventory.

#### ItemDetailsReportGenerator

Implements ReportGenerator to generate a report with details of all items in the inventory.

## Design Patterns

- Singleton: Ensures that only one instance of the Inventory class is created, maintaining a single, consistent data source.
- Command: Provides better organization, easier undo/redo functionality, and improved maintainability for inventory actions.
- Observer: Notifies relevant parties when an item's stock reaches a low threshold.
- Strategy: Allows for better organization, easier customization, and improved maintainability for generating different types of reports.

Please refer to the source code for a deeper understanding of the system's operation. For any questions or issues, please open an issue in the project's issue tracker.