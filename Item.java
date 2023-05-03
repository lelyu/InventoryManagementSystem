public class Item {
    private int id;
    private String name;
    private double price;
    private int stockLevel;

    public Item(int id, String name, double price, int stockLevel) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stockLevel = stockLevel;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStockLevel() {
        return stockLevel;
    }

    public void setStockLevel(int stockLevel) {
        this.stockLevel = stockLevel;
    }
}
