public abstract class InventoryCommand {
    protected Inventory inventory;

    public InventoryCommand(Inventory inventory) {
        this.inventory = inventory;
    }

    public abstract void execute();

    public abstract void undo();
}
