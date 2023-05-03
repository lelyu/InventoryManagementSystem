public class UpdateItemCommand extends InventoryCommand {
    private Item oldItem;
    private Item newItem;

    public UpdateItemCommand(Inventory inventory, Item newItem) {
        super(inventory);
        this.newItem = newItem;
        this.oldItem = inventory.getItem(newItem.getId());
    }

    @Override
    public void execute() {
        inventory.updateItem(newItem);
    }

    @Override
    public void undo() {
        inventory.updateItem(oldItem);
    }
}
