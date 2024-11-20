package Food;

import User.User;
import db.Database;
import User.UserService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class CartService {

    public static void addToCart(User user, Scanner scanner) {
        if(!UserService.doesUserExist(user.getId())) {
            System.out.println("Error: User.User Does not exist. Please register first.");
            return;
        }

        Database.viewFoodItems();

        System.out.println("\nEnter food item ID to add: ");
        String itemIdInput = scanner.nextLine();
        int itemId;

        if (itemIdInput == null || itemIdInput.trim().isEmpty()) {
            System.out.println("Invalid input. Please Enter a valid Food item ID");
            return;
        }
        try {
            itemId = Integer.parseInt(itemIdInput);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid numeric Food Item ID.");
            return;
        }

        System.out.print("Enter quantity: ");
        String quantityInput = scanner.nextLine();
        int quantity;

        if (quantityInput == null || quantityInput.trim().isEmpty()) {
            System.out.println("Invalid input. Please enter a valid quantity.");
            return;
        }
        try {
            quantity = Integer.parseInt(quantityInput);
            if (quantity <= 0) {
                System.out.println("Quantity must be greater than zero.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid numeric quantity.");
            return;
        }

        try (Connection conn = Database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO carts (user_id, food_item_id, quantity) VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE quantity = quantity + ?");
            stmt.setInt(1, user.getId());
            stmt.setInt(2, itemId);
            stmt.setInt(3, quantity);
            stmt.setInt(4, quantity);
            stmt.executeUpdate();

            System.out.println("Item added to cart.");
        } catch (SQLException e) {
            System.out.println("Failed to retrieve product ID");
        }
    }

    public static void viewCart(User user) {
        System.out.println("\nYour Cart:");
        double total = 0.0;

        try (Connection conn = Database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(
                    "SELECT f.id, f.name, c.quantity, f.price FROM carts c JOIN food_items f ON c.food_item_id = f.id WHERE c.user_id = ?");
            stmt.setInt(1, user.getId());
            ResultSet rs = stmt.executeQuery();

            // Print header row with separators and fixed widths
            System.out.printf("%-20s | %-10s | %-10s | %-15s | %-15s%n", "Food Name", "Quantity", "Product ID", "Price (PHP)", "Item Total (PHP)");
            System.out.println("------------------------------------------------------------------------------------------");

            // Print each row with separators and fixed column widths
            while (rs.next()) {
                String name = rs.getString("name");
                int quantity = rs.getInt("quantity");
                int productId = rs.getInt("id");
                double price = rs.getDouble("price");
                double itemTotal = price * quantity;
                total += itemTotal;

                System.out.printf("%-20s | %-10d | %-10d | %-15.2f | %-15.2f%n", name, quantity, productId, price, itemTotal);
            }

            System.out.println("------------------------------------------------------------------------------------------");
            System.out.printf("Total Price: %.2fPHP%n", total);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void removeFromCart(User user, Scanner scanner) {
        System.out.print("Enter food item ID to remove: ");
        int itemId = Integer.parseInt(scanner.nextLine());

        try (Connection conn = Database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM carts WHERE user_id = ? AND food_item_id = ?");
            stmt.setInt(1, user.getId());
            stmt.setInt(2, itemId);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Item removed from cart.");
            } else {
                System.out.println("Item not found in cart.");
            }
        } catch (SQLException e) {
            System.out.println("Failed to retrieve product ID");
        }
    }

    public static void checkout(User user) {
        System.out.println("\nChecking out...");

        viewCart(user);

        try (Connection conn = Database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM carts WHERE user_id = ?");
            stmt.setInt(1, user.getId());
            stmt.executeUpdate();

            System.out.println("Checkout complete. Thank you for your order!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
