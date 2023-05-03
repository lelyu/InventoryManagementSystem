import java.util.Map;

public class TotalItemsReportGenerator implements ReportGenerator {

    @Override
    public Report generate(Inventory inventory) {
        int totalItems = 0;

        for (Map.Entry<Integer, Item> entry : inventory.getItems().entrySet()) {
            Item item = entry.getValue();
            totalItems += item.getStockLevel();
        }

        String title = "Total Items Report";
        String content = "Total items in stock: " + totalItems;

        return new Report(title, content);
    }
}
