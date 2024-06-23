import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main extends JFrame {
    private InventoryDatabase db;
    private JTextField idField, nameField, quantityField, priceField, storageField;
    private JTextArea textArea;

    public Main() {
        db = new InventoryDatabase("inventory.txt");
        createUI();
    }

    private void createUI() {
        setTitle("Warehouse Storage Management System");
        setSize(1000, 450);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Label and Entry fields
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Product ID:"), gbc);

        gbc.gridx = 1;
        idField = new JTextField(10);
        panel.add(idField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Product Name:"), gbc);

        gbc.gridx = 1;
        nameField = new JTextField(10);
        panel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Quantity:"), gbc);

        gbc.gridx = 1;
        quantityField = new JTextField(10);
        panel.add(quantityField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(new JLabel("Price:"), gbc);

        gbc.gridx = 1;
        priceField = new JTextField(10);
        panel.add(priceField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(new JLabel("Storage:"), gbc);

        gbc.gridx = 1;
        storageField = new JTextField(10);
        panel.add(storageField, gbc);


        // Buttons
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        JButton addButton = new JButton("Add Product");
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addProduct();
            }
        });
        panel.add(addButton, gbc);

        gbc.gridy = 6;
        JButton updateButton = new JButton("Update Product");
        updateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateProduct();
            }
        });
        panel.add(updateButton, gbc);

        gbc.gridy = 7;
        JButton deleteButton = new JButton("Delete Product");
        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteProduct();
            }
        });
        panel.add(deleteButton, gbc);

        gbc.gridy = 8;
        JButton viewButton = new JButton("View Products");
        viewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                viewProducts();
            }
        });
        panel.add(viewButton, gbc);

        textArea = new JTextArea(15, 30);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);

        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.add(panel, BorderLayout.NORTH); 

        getContentPane().add(leftPanel, BorderLayout.WEST);
        getContentPane().add(scrollPane, BorderLayout.CENTER);
    }

    // Button Methods
    private void addProduct() {
        String id = idField.getText();
        String name = nameField.getText();
        String quantityStr = quantityField.getText();
        String priceStr = priceField.getText();
        String storage = storageField.getText();
    
        if (id.isEmpty() || name.isEmpty() || quantityStr.isEmpty() || priceStr.isEmpty() || storage.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please complete all fields.");
            return;
        }
    
        int quantity;
        double price;
    
        try {
            quantity = Integer.parseInt(quantityStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid integer for quantity!");
            return;
        }
    
        try {
            price = Double.parseDouble(priceStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid double for price!");
            return;
        }
    
        try {
            if (!db.isIdUnique(id)) {
                JOptionPane.showMessageDialog(this, "Product ID must be unique!");
                return;
            }
    
            Product product = new Product(id, name, quantity, price, storage);
            db.addProduct(product);
            JOptionPane.showMessageDialog(this, "Product added successfully!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error adding product: " + e.getMessage());
        }
    }
    
    

    // TODO: check if productId field is empty (done)
    private void updateProduct() {
        String id = idField.getText();
        String name = nameField.getText();
        String quantityStr = quantityField.getText();
        String priceStr = priceField.getText();
        String storage = storageField.getText();
    
        if (id.isEmpty() || name.isEmpty() || quantityStr.isEmpty() || priceStr.isEmpty() || storage.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please complete all fields.");
            return;
        }
    
        int quantity;
        double price;
    
        try {
            quantity = Integer.parseInt(quantityStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid integer for quantity!");
            return;
        }
    
        try {
            price = Double.parseDouble(priceStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid double for price!");
            return;
        }
    
        Product product = new Product(id, name, quantity, price, storage);
        try {
            db.updateProduct(product);
            JOptionPane.showMessageDialog(this, "Product updated successfully!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error updating product: " + e.getMessage());
        }
    }

    // TODO: check if productId is empty (done)
    private void deleteProduct() {
        String id = idField.getText();

        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a Product ID");
            return;
        }

        try {
            db.deleteProduct(id);
            JOptionPane.showMessageDialog(this, "Product deleted successfully!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error deleting product: " + e.getMessage());
        }
    }

    private void viewProducts() {
        try (BufferedReader reader = new BufferedReader(new FileReader(db.getFileName()))) {
            textArea.setText(""); // Clear the text area
            String line;
            while ((line = reader.readLine()) != null) {
                textArea.append(line + "\n");
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error reading products: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Main().setVisible(true);
            }
        });
    }
}
