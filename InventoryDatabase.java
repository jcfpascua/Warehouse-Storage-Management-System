import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class InventoryDatabase {
    // Private Field
    private String fileName;

    // Parameterized Constructor
    public InventoryDatabase(String fileName) {
        this.fileName = fileName;
        initializeFile();
    }

    // Method that runs when Main runs.
    // Creates the database when Main runs. Also creates the header for the text file.
    private void initializeFile() {
        File file = new File(fileName);
        try {
            if (!file.exists() || file.length() == 0) {
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
                    writer.write("id\t\tproduct\t\tquantity\t\tprice\t\tstorage");
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Crud Functions
    public void addProduct(Product product) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.write(product.toString());
            writer.newLine();
        }
    }

    public void updateProduct(Product product) throws IOException {
        List<Product> products = getAllProducts();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write("id\t\tproduct\t\tquantity\t\tprice\t\tstorage");
            writer.newLine();

            for (Product p : products) {
                if (p.getProductId().equals(product.getProductId())) {
                    writer.write(product.toString());
                } else {
                    writer.write(p.toString());
                }
                writer.newLine();
            }
        }
    }

    public void deleteProduct(String id) throws IOException {
        List<Product> products = getAllProducts();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write("id\t\tproduct\t\tquantity\t\tprice\t\tstorage");
            writer.newLine();
            for (Product p : products) {
                if (!p.getProductId().equals(id)) {
                    writer.write(p.toString());
                    writer.newLine();
                }
            }
        }
    }

    public List<Product> getAllProducts() throws IOException {
        List<Product> products = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line = reader.readLine(); // Skip the header line
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\t");
                if (parts.length == 4) {
                    String id = parts[0];
                    String name = parts[1];
                    int quantity = Integer.parseInt(parts[2]);
                    double price = Double.parseDouble(parts[3]);
                    String storage = parts[4];
                    products.add(new Product(id, name, quantity, price, storage));
                }
            }
        }
        return products;
    }

    // Checks for UID before operations
    public boolean isIdUnique(String idToCheck) throws IOException {
        List<Product> products = getAllProducts();
        for (Product product : products) {
            if (product.getProductId().equals(idToCheck)) {
                return false; // ID already exists
            }
        }
        return true; // ID is unique
    }

    public String getFileName() {
        return fileName;
    }
}
