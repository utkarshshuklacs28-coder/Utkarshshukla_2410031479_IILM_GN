import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

class FoodItem {
    String name;
    double price;
    String imagePath;

    FoodItem(String name, double price, String imagePath) {
        this.name = name;
        this.price = price;
        this.imagePath = imagePath;
    }
}

class Cart {
    java.util.List<FoodItem> items = new ArrayList<>();

    void addItem(FoodItem item) {
        items.add(item);
    }

    double getTotal() {
        double total = 0;
        for (FoodItem i : items) total += i.price;
        return total;
    }

    String getSummary() {
        StringBuilder sb = new StringBuilder();
        for (FoodItem i : items) sb.append(i.name).append(" - ₹").append(i.price).append("\n");
        sb.append("\nTotal: ₹").append(getTotal());
        return sb.toString();
    }
}

public class FoodDeliveryApp extends JFrame {

    private Cart cart = new Cart();
    private JPanel menuPanel = new JPanel();
    private JTextArea cartArea = new JTextArea();

    public FoodDeliveryApp() {
        setTitle("Food Delivery App");
        setSize(900, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Header
        JPanel header = new JPanel();
        header.setBackground(new Color(40, 40, 40));
        JLabel title = new JLabel("🍔 Food Delivery App");
        title.setFont(new Font("Segoe UI", Font.BOLD, 26));
        title.setForeground(Color.WHITE);
        header.add(title);
        add(header, BorderLayout.NORTH);

        // Menu Panel
        menuPanel.setLayout(new GridLayout(0, 2, 15, 15));
        menuPanel.setBorder(new EmptyBorder(15, 15, 15, 15));

        java.util.List<FoodItem> items = Arrays.asList(
                new FoodItem("Pizza", 250, "images/pizza.jpg"),
                new FoodItem("Burger", 120, "images/burger.jpg"),
                new FoodItem("Pasta", 180, "images/pasta.jpg"),
                new FoodItem("Fries", 90, "images/fries.jpg"),
                new FoodItem("Coke", 50, "images/coke.jpg")
        );

        for (FoodItem item : items) {
            menuPanel.add(createFoodCard(item));
        }

        JScrollPane menuScroll = new JScrollPane(menuPanel);

        // Cart Panel
        cartArea.setEditable(false);
        cartArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        JScrollPane cartScroll = new JScrollPane(cartArea);
        cartScroll.setBorder(BorderFactory.createTitledBorder("Cart"));

        // Buttons
        JButton checkoutButton = new JButton("Checkout");
        styleButton(checkoutButton);

        checkoutButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, cart.getSummary(), "Order Summary", JOptionPane.INFORMATION_MESSAGE);
        });

        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.add(cartScroll, BorderLayout.CENTER);
        rightPanel.add(checkoutButton, BorderLayout.SOUTH);

        add(menuScroll, BorderLayout.CENTER);
        add(rightPanel, BorderLayout.EAST);
    }

    private JPanel createFoodCard(FoodItem item) {
        JPanel card = new JPanel();
        card.setLayout(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1, true));

        JLabel imageLabel;
        try {
            ImageIcon icon = new ImageIcon(item.imagePath);
            Image img = icon.getImage().getScaledInstance(150, 120, Image.SCALE_SMOOTH);
            imageLabel = new JLabel(new ImageIcon(img));
        } catch (Exception e) {
            imageLabel = new JLabel("No Image");
        }

        JLabel nameLabel = new JLabel(item.name + " - ₹" + item.price, JLabel.CENTER);
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));

        JButton addBtn = new JButton("Add");
        styleButton(addBtn);

        addBtn.addActionListener(e -> {
            cart.addItem(item);
            updateCart();
        });

        card.add(imageLabel, BorderLayout.CENTER);
        card.add(nameLabel, BorderLayout.NORTH);
        card.add(addBtn, BorderLayout.SOUTH);

        return card;
    }

    private void updateCart() {
        cartArea.setText(cart.getSummary());
    }

    private void styleButton(JButton btn) {
        btn.setFocusPainted(false);
        btn.setBackground(new Color(255, 102, 0));
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FoodDeliveryApp().setVisible(true));
    }
}
