package User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import db.Database;

public class Admin extends User {
    public Admin(int id, String username) {
        super(id, username);
    }
    static Scanner scanner = new Scanner(System.in);

    public static void adminDashboard(User user) {
        boolean running = true;
        while (running) {
            if (user instanceof Admin admin) {
                System.out.println("\nAdmin Dashboard\n");
                Database.viewRestaurants();
                System.out.println("\n");
                Database.viewFoodItems();
                System.out.println("\n1. Add a restaurant ");
                System.out.println("2. Remove a restaurant ");
                System.out.println("3. Add a food item");
                System.out.println("4. Remove a food item");
                System.out.println("5. Log out");
                System.out.println("\nChoose an option: ");

                try {
                    int input = Integer.parseInt(scanner.nextLine());
                    switch (input) {
                        case 1:
                            admin.addRestaurant();
                            break;
                        case 2:
                            admin.removeRestaurant();
                            break;
                        case 3:
                            admin.addFoodItem();
                            break;
                        case 4:
                            admin.removeFoodItem();
                            break;
                        case 5:
                            System.out.print("Logging out...\n");
                            running = false;
                            break;
                        default:
                            System.out.println("Invalid input Try again");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a number between 1 and 5.");
                }
            }
        }
    }
    public void addRestaurant() {
        System.out.print("Enter the name of the restaurant: ");
        String name = scanner.nextLine();
        System.out.print("Enter the address of the restaurant: ");
        String location = scanner.nextLine();

        try (Connection conn = Database.getConnection()) {
            int nextId = getNextAvailableRestaurantId(conn);

            // Insert with the manually assigned ID
            String query = "INSERT INTO restaurants (id, name, address) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setInt(1, nextId);
            stmt.setString(2, name);
            stmt.setString(3, location);

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Restaurant added successfully with ID: " + nextId);
            } else {
                System.out.println("Failed to add the restaurant.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeRestaurant() {
        Database.viewRestaurants();
        System.out.print("Enter the ID of the restaurant to remove. ");
        int restaurantId = scanner.nextInt();

        try (Connection conn = Database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM restaurants WHERE id = ?");
            stmt.setInt(1, restaurantId);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Restaurant removed successfully");
            } else {
                System.out.println("No restaurant found with the given ID");
            }
        }  catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addFoodItem() {
        Database.viewRestaurants();
        System.out.print("Enter the name of the food item: ");
        String name = scanner.nextLine();
        System.out.print("Enter the price of the food item: ");
        double price = Double.parseDouble(scanner.nextLine());
        System.out.println("Enter the ID of the restaurant this item belongs to: ");
        int restaurantId = Integer.parseInt(scanner.nextLine());

        try (Connection conn = Database.getConnection()) {
            int newId = getNextAvailableFoodId(conn);
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO food_items (restaurant_id, name, price) VALUES (?, ?, ?)");
            stmt.setInt(1, restaurantId);
            stmt.setString(2, name);
            stmt.setDouble(3, price);
            stmt.executeUpdate();
            System.out.println("New Food Item Added Successfully with ID: " + newId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeFoodItem() {
        Database.viewFoodItems();
        System.out.println("Enter the ID of the food item to remove: ");
        int foodItemId = Integer.parseInt(scanner.nextLine());

        try(Connection conn = Database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM food_items WHERE id = ?");
            stmt.setInt(1, foodItemId);
            int rowsAffected = stmt.executeUpdate();

            if(rowsAffected > 0) {
                System.out.print("Food item removed successfully");
            } else {
                System.out.println("No food item found with the given ID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int getNextAvailableRestaurantId(Connection conn) throws SQLException {
        String query = "SELECT COALESCE(MAX(id), 0) + 1 AS next_id FROM restaurants";
        PreparedStatement stmt = conn.prepareStatement(query);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            return rs.getInt("next_id");
        } else {
            throw new SQLException("Failed to retrieve the next ID.");
        }
    }

    public static int getNextAvailableFoodId(Connection conn) throws SQLException {
        String query = "SELECT COALESCE(MAX(id), 0) + 1 AS next_id FROM food_items";
        PreparedStatement stmt = conn.prepareStatement(query);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            return rs.getInt("next_id");
        } else {
            throw new SQLException("Failed to retrieve the next ID.");
        }
    }
}
