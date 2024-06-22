public class Product {
    // Properties
    private String productId;
    private String productName;
    private int count;
    private double price;
    private String storage;

    // Constructor
    public Product(String productId, String productName, int count, double price, String storage) {
        this.productId = productId;
        this.productName = productName;
        this.count = count;
        this.price = price;
        this.storage = storage;
    }

    // Getters and Setters
    public String getProductId() {
        return productId;
    }
    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getCount() {
        return count;
    }
    public void setCount(int count) {
        this.count = count;
    }

    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }

    public String getStorage() {
        return storage;
    }
    public void setStorage(String storage) {
        this.storage = storage;
    }

    @Override
    public String toString() {
        return productId + "\t\t" + productName + "\t\t" + count + "\t\t" + price + "\t\t" + storage;
    }

}