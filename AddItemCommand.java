public class AddItemCommand extends InventoryCommand {
    private Item item;

    public AddItemCommand(Inventory inventory, Item item) {
        super(inventory);
        this.item = item;
    }

    @Override
    public void execute() {
        inventory.addItem(item);
    }

    @Override
    public void undo() {
        inventory.removeItem(item.getId());
    }

    public Inventory getInventory() {
        return inventory;
    }
}
