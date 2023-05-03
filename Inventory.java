import java.util.HashMap;
import java.util.Map;

public class Inventory {
    private static Inventory instance;
    private Map<Integer, Item> items;

    private Inventory() {
        items = new HashMap<>();
    }

    public static synchronized Inventory getInstance() {
        if (instance == null) {
            instance = new Inventory();
        }
        return instance;
    }

    public boolean addItem(Item item) {
        if (items.containsKey(item.getId())) {
            return false;
        }
        items.put(item.getId(), item);
        return true;
    }

    public void removeItem(int itemId) {
        items.remove(itemId);
    }

    public void updateItem(Item item) {
        items.put(item.getId(), item);
    }

    public Item getItem(int itemId) {
        return items.get(itemId);
    }

    public Map<Integer, Item> getItems() {
        return items;
    }

}
