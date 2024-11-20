import User.Admin;
import User.User;
import User.UserService;
import User.Customer;

import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        start();
    }

    private static void start() {
        try{
            while (true) {
                System.out.println("\nWelcome to the Food Delivery System");
                System.out.println("1. Login as Customer\n2. Login as Admin\n3. Register\nChoose an option:");
                int choice = Integer.parseInt(scanner.nextLine());

                System.out.print("Enter username: ");
                String username = scanner.nextLine();
                System.out.print("Enter password: ");
                String password = scanner.nextLine();

                User user;

                switch (choice) {
                    case 1:
                        user = UserService.loginCustomer(username, password);
                        if (user != null) {
                            Customer.showDashboard(user);
                        } else {
                            System.out.println("Invalid customer credentials.");
                            start();
                        }
                        break;
                    case 2:
                        user = UserService.loginAdmin(username, password);
                        if (user != null) {
                            System.out.println("Admin login successful. Welcome, " + username + "!");
                            Admin.adminDashboard(user);
                        } else {
                            System.out.println("Invalid admin credentials.");
                        }
                        break;
                    case 3:
                        String role = "user";
                        boolean registered = UserService.registerUser(username, password, role);
                        if (registered) {
                            System.out.println("Registration successful. You can now log in.");
                        } else {
                            System.out.println("Registration failed. Username already exists.");
                        }
                        break;
                    default:
                        System.out.println("Invalid choice. Try again.");
                }
            }
        } catch (NumberFormatException e){
            System.out.println("Invalid Input. Try again");
            start();
        }
    }
}
