public class RemoveItemCommand extends InventoryCommand {
    private Item item;

    public RemoveItemCommand(Inventory inventory, int itemId) {
        super(inventory);
        this.item = inventory.getItem(itemId);
    }

    @Override
    public void execute() {
        inventory.removeItem(item.getId());
    }

    @Override
    public void undo() {
        inventory.addItem(item);
    }
}
