package User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import db.Database;

public class UserService {
    public static User loginCustomer(String username, String password) {
        try (Connection conn = Database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(
                    "SELECT id, username, role FROM users WHERE username = ? AND password = ? AND role = 'user'"
            );
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("id");
                return new Customer(id, username);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Return null if authentication fails
    }

    public static User loginAdmin(String username, String password) {
        try (Connection conn = Database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(
                    "SELECT id, username, role FROM users WHERE username = ? AND password = ? AND role = 'admin'"
            );
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("id");
                return new Admin(id, username);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Return null if authentication fails
    }

    public static boolean registerUser(String username, String password, String role) {
        try (Connection conn = Database.getConnection()) {
            // Check if the username already exists
            PreparedStatement checkStmt = conn.prepareStatement("SELECT id FROM users WHERE username = ?");
            checkStmt.setString(1, username);
            ResultSet checkResult = checkStmt.executeQuery();

            if (checkResult.next()) {
                return false; // Username already exists
            }

            // Insert a new user
            PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO users (username, password, role) VALUES (?, ?, ?)"
            );
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, role); // Either 'customer' or 'admin'
            stmt.executeUpdate();
            return true; // Registration successful
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Registration failed
    }

    public static boolean doesUserExist(int userId) {
        try (Connection conn = Database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users WHERE id = ?");
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}