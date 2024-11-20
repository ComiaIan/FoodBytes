package User;

import Food.CartService;
import java.sql.*;
import java.util.Scanner;
import db.Database;

public class Customer extends User{
    public Customer (int id, String username) {
        super(id, username);
    }

    public static void showDashboard(User user) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nWelcome, " + getUsername() + "\n");

        Database.viewRestaurants();
        System.out.println("\n");
        Database.viewFoodItems();

        boolean running = true;
        while (running) {
            System.out.println("\n1. Search for food or restaurant");
            System.out.println("2. View cart");
            System.out.println("3. Add to cart");
            System.out.println("4. Remove from cart");
            System.out.println("5. Checkout");
            System.out.println("6. Logout");
            System.out.println("\nChoose an option:");

            try {
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1 -> search();
                    case 2 -> CartService.viewCart(user);
                    case 3 -> CartService.addToCart(user, scanner);
                    case 4 -> CartService.removeFromCart(user, scanner);
                    case 5 -> CartService.checkout(user);
                    case 6 -> {
                        System.out.println("Logging out...");
                        running = false;
                    }
                    default -> System.out.println("Invalid option. Try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number between 1 and 6.");
                showDashboard(user);
            }
        }
    }

    private static void search() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter food or restaurant name: ");
        String query = scanner.nextLine();

        System.out.println("\nSearch Results:");
        try (Connection conn = Database.getConnection()) {
            // Search for restaurants matching the query
            PreparedStatement searchRestaurant = conn.prepareStatement("SELECT id, name FROM restaurants WHERE name LIKE ?");
            searchRestaurant.setString(1, "%" + query + "%");
            ResultSet rsRestaurants = searchRestaurant.executeQuery();
            boolean restaurantFound = false;

            while (rsRestaurants.next()) {
                restaurantFound = true;
                int restaurantId = rsRestaurants.getInt("id");
                String restaurantName = rsRestaurants.getString("name");
                System.out.println("Restaurant: " + restaurantName);

                // Retrieve all food items for the found restaurant
                PreparedStatement searchFoodsAtRestaurant = conn.prepareStatement(
                        "SELECT name, id, price FROM food_items WHERE restaurant_id = ?");
                searchFoodsAtRestaurant.setInt(1, restaurantId);
                ResultSet rsFoods = searchFoodsAtRestaurant.executeQuery();

                // Print header for food items table
                System.out.printf("%-20s %-10s %-10s%n", "Food Name", "ID", "Price");
                System.out.println("------------------------------------------");

                while (rsFoods.next()) {
                    String foodName = rsFoods.getString("name");
                    int foodId = rsFoods.getInt("id");
                    double price = rsFoods.getDouble("price");
                    System.out.printf("%-20s %-10d $%-9.2f%n", foodName, foodId, price);
                }
                System.out.println(); // Print a newline for better readability
            }

            // Search for food items if no restaurants were found
            if (!restaurantFound) {
                PreparedStatement searchFood = conn.prepareStatement(
                        "SELECT f.name, f.id, f.price, r.name AS restaurant_name FROM food_items f " +
                                "JOIN restaurants r ON f.restaurant_id = r.id WHERE f.name LIKE ?");
                searchFood.setString(1, "%" + query + "%");
                ResultSet rsFood = searchFood.executeQuery();

                System.out.printf("%-20s %-10s %-10s%n", "Food Name", "ID", "Price");
                System.out.println("------------------------------------------");

                while (rsFood.next()) {
                    String foodName = rsFood.getString("name");
                    int foodId = rsFood.getInt("id");
                    double price = rsFood.getDouble("price");
                    String restaurantName = rsFood.getString("restaurant_name");
                    System.out.printf("%-20s %-10d $%-9.2f (at %s)%n", foodName, foodId, price, restaurantName);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
