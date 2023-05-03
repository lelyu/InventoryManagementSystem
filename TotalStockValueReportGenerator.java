import java.util.Map;

public class TotalStockValueReportGenerator implements ReportGenerator {

    @Override
    public Report generate(Inventory inventory) {
        double totalValue = 0;

        for (Map.Entry<Integer, Item> entry : inventory.getItems().entrySet()) {
            Item item = entry.getValue();
            totalValue += item.getPrice() * item.getStockLevel();
        }

        String title = "Total Stock Value Report";
        String content = "Total stock value: $" + totalValue;

        return new Report(title, content);
    }
}
