import java.sql.*;
import java.util.Scanner;

public class BankingSystem {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/BankingSystem";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "password";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- Banking Management System ---");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    register();
                    break;
                case 2:
                    login();
                    break;
                case 3:
                    System.out.println("Thank you for using the system.");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void register() {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Scanner scanner = new Scanner(System.in)) {

            System.out.print("Enter username: ");
            String username = scanner.nextLine();

            System.out.print("Enter password: ");
            String password = scanner.nextLine();

            String hashedPassword = hashPassword(password);

            String query = "INSERT INTO Users (username, password) VALUES (?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            stmt.setString(2, hashedPassword);

            stmt.executeUpdate();
            System.out.println("Registration successful!");

        } catch (SQLException e) {
            System.out.println("Error during registration: " + e.getMessage());
        }
    }

    private static void login() {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Scanner scanner = new Scanner(System.in)) {

            System.out.print("Enter username: ");
            String username = scanner.nextLine();

            System.out.print("Enter password: ");
            String password = scanner.nextLine();

            String query = "SELECT id, password, balance FROM Users WHERE username = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String storedPassword = rs.getString("password");
                if (verifyPassword(password, storedPassword)) {
                    int userId = rs.getInt("id");
                    double balance = rs.getDouble("balance");

                    System.out.println("Login successful!");
                    userMenu(userId, balance);
                } else {
                    System.out.println("Invalid password.");
                }
            } else {
                System.out.println("User not found.");
            }

        } catch (SQLException e) {
            System.out.println("Error during login: " + e.getMessage());
        }
    }

    private static void userMenu(int userId, double balance) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- User Menu ---");
            System.out.println("1. View Balance");
            System.out.println("2. Deposit Funds");
            System.out.println("3. Withdraw Funds");
            System.out.println("4. View Transactions");
            System.out.println("5. Logout");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("Current balance: " + balance);
                    break;
                case 2:
                    balance = depositFunds(userId, balance);
                    break;
                case 3:
                    balance = withdrawFunds(userId, balance);
                    break;
                case 4:
                    viewTransactions(userId);
                    break;
                case 5:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static double depositFunds(int userId, double balance) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Scanner scanner = new Scanner(System.in)) {

            System.out.print("Enter amount to deposit: ");
            double amount = scanner.nextDouble();

            balance += amount;

            String updateBalance = "UPDATE Users SET balance = ? WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(updateBalance);
            stmt.setDouble(1, balance);
            stmt.setInt(2, userId);
            stmt.executeUpdate();

            String recordTransaction = "INSERT INTO Transactions (user_id, transaction_type, amount) VALUES (?, 'Deposit', ?)";
            stmt = conn.prepareStatement(recordTransaction);
            stmt.setInt(1, userId);
            stmt.setDouble(2, amount);
            stmt.executeUpdate();

            System.out.println("Deposit successful! New balance: " + balance);

        } catch (SQLException e) {
            System.out.println("Error during deposit: " + e.getMessage());
        }

        return balance;
    }

    private static double withdrawFunds(int userId, double balance) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Scanner scanner = new Scanner(System.in)) {

            System.out.print("Enter amount to withdraw: ");
            double amount = scanner.nextDouble();

            if (amount > balance) {
                System.out.println("Insufficient balance.");
                return balance;
            }

            balance -= amount;

            String updateBalance = "UPDATE Users SET balance = ? WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(updateBalance);
            stmt.setDouble(1, balance);
            stmt.setInt(2, userId);
            stmt.executeUpdate();

            String recordTransaction = "INSERT INTO Transactions (user_id, transaction_type, amount) VALUES (?, 'Withdraw', ?)";
            stmt = conn.prepareStatement(recordTransaction);
            stmt.setInt(1, userId);
            stmt.setDouble(2, amount);
            stmt.executeUpdate();

            System.out.println("Withdrawal successful! New balance: " + balance);

        } catch (SQLException e) {
            System.out.println("Error during withdrawal: " + e.getMessage());
        }

        return balance;
    }

    private static void viewTransactions(int userId) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT transaction_type, amount, timestamp FROM Transactions WHERE user_id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, userId);

            ResultSet rs = stmt.executeQuery();
            System.out.println("\n--- Transaction History ---");
            while (rs.next()) {
                String type = rs.getString("transaction_type");
                double amount = rs.getDouble("amount");
                Timestamp timestamp = rs.getTimestamp("timestamp");

                System.out.println(type + " - Rs. " + amount + " on " + timestamp);
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving transactions: " + e.getMessage());
        }
    }

    private static String hashPassword(String password) {
        // Dummy hashing method (use libraries like BCrypt for real projects)
        return Integer.toString(password.hashCode());
    }

    private static boolean verifyPassword(String password, String hashedPassword) {
        return hashPassword(password).equals(hashedPassword);
    }
}
