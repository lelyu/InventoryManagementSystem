import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Inventory {
    private static Inventory instance;
    private Map<Integer, Item> items;
    private List<InventoryObserver> observers;

    private Inventory() {
        items = new HashMap<>();
        observers = new ArrayList<>();
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

    public Item getItem(int itemId) {
        return items.get(itemId);
    }

    public Map<Integer, Item> getItems() {
        return items;
    }

    public void addObserver(InventoryObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(InventoryObserver observer) {
        observers.remove(observer);
    }

    public void updateItem(Item item) {
        items.put(item.getId(), item);
        notifyObservers(item);
    }

    private void notifyObservers(Item item) {
        for (InventoryObserver observer : observers) {
            observer.onItemUpdated(item);
        }
    }

}
