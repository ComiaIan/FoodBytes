package db;
import java.sql.*;

public class Database {
    private static final String URL = "jdbc:mysql://localhost:3306/sqltry";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "Scholastic1";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    public static void viewRestaurants() {
        try (Connection conn = Database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM restaurants");
            ResultSet rs = stmt.executeQuery();

            System.out.printf("%-5s %-20s %-30s%n", "ID", "Name", "Address");
            System.out.println("------------------------------------------------------------");

            // Print each row with fixed column widths
            while (rs.next()) {
                System.out.printf("%-5d %-20s %-30s%n",
                        rs.getInt("id"), rs.getString("name"), rs.getString("address"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void viewFoodItems() {
        try (Connection conn = Database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM food_items");
            ResultSet rs = stmt.executeQuery();

            System.out.printf("%-15s %-5s %-10s %-15s%n", "Food Name", "ID", "Price", "Restaurant ID");
            System.out.println("--------------------------------------------------------");

            while (rs.next()) {
                System.out.printf("%-15s %-5d %-10d %-15s%n",
                        rs.getString("name"), rs.getInt("id"), rs.getInt("price"), rs.getInt("restaurant_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
