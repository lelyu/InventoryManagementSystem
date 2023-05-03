public class LowStockNotifier {
    private Inventory inventory;
    private int lowStockThreshold;

    public LowStockNotifier(Inventory inventory, int lowStockThreshold) {
        this.inventory = inventory;
        this.lowStockThreshold = lowStockThreshold;
    }

    public void update(Item item) {
        if (item.getStockLevel() <= lowStockThreshold) {
            notifyLowStock(item);
        }
    }

    private void notifyLowStock(Item item) {
        System.out.println("Low stock alert for item: " + item.getName() + " (ID: " + item.getId()
                + "). Current stock: " + item.getStockLevel());
        // Add any additional logic for notifying relevant parties (e.g., sending an
        // email or SMS)
    }
}
