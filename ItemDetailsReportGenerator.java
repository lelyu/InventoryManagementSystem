import java.util.Map;

public class ItemDetailsReportGenerator implements ReportGenerator {

    @Override
    public Report generate(Inventory inventory) {
        StringBuilder contentBuilder = new StringBuilder();

        contentBuilder.append(String.format("%-10s %-20s %-10s %-10s%n", "ID", "Name", "Price", "Stock Level"));

        for (Map.Entry<Integer, Item> entry : inventory.getItems().entrySet()) {
            Item item = entry.getValue();
            contentBuilder.append(String.format("%-10d %-20s $%-9.2f %-10d%n",
                    item.getId(),
                    item.getName(),
                    item.getPrice(),
                    item.getStockLevel()));
        }

        String title = "Item Details Report";
        String content = contentBuilder.toString();

        return new Report(title, content);
    }
}
