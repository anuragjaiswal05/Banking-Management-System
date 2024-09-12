import java.util.Scanner;

public class BankingApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BankingSystem bankingSystem = new BankingSystem();

        while (true) {
            System.out.println("\n1. Register User");
            System.out.println("2. Login");
            System.out.println("3. Create Account");
            System.out.println("4. Deposit");
            System.out.println("5. Withdraw");
            System.out.println("6. Check Balance");
            System.out.println("7. Display Account");
            System.out.println("8. Delete Account");
            System.out.println("9. Logout");
            System.out.println("10. Exit");
            System.out.print("Choose an option: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter user ID: ");
                    String userId = scanner.nextLine();
                    System.out.print("Enter password: ");
                    String password = scanner.nextLine();
                    if (bankingSystem.registerUser(userId, password)) {
                        System.out.println("User registered successfully.");
                    } else {
                        System.out.println("User ID already exists.");
                    }
                    break;
                case 2:
                    System.out.print("Enter user ID: ");
                    String loginId = scanner.nextLine();
                    System.out.print("Enter password: ");
                    String loginPassword = scanner.nextLine();
                    if (bankingSystem.login(loginId, loginPassword)) {
                        System.out.println("Login successful.");
                    } else {
                        System.out.println("Invalid credentials.");
                    }
                    break;
                case 3:
                    System.out.print("Enter account number: ");
                    String accNumber = scanner.nextLine();
                    System.out.print("Enter name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter initial balance: ");
                    double initialBalance = scanner.nextDouble();
                    scanner.nextLine(); // Consume newline
                    System.out.print("Enter account type: ");
                    String accType = scanner.nextLine();
                    if (bankingSystem.createAccount(accNumber, name, initialBalance)) {
                        System.out.println("Account created successfully.");
                    } else {
                        System.out.println("Account number already exists.");
                    }
                    break;
                case 4:
                    System.out.print("Enter account number: ");
                    String depositAcc = scanner.nextLine();
                    System.out.print("Enter amount to deposit: ");
                    double depositAmount = scanner.nextDouble();
                    scanner.nextLine(); // Consume newline
                    if (bankingSystem.deposit(depositAcc, depositAmount)) {
                        System.out.println("Deposit successful.");
                    } else {
                        System.out.println("Deposit failed.");
                    }
                    break;
                case 5:
                    System.out.print("Enter account number: ");
                    String withdrawAcc = scanner.nextLine();
                    System.out.print("Enter amount to withdraw: ");
                    double withdrawAmount = scanner.nextDouble();
                    scanner.nextLine(); // Consume newline
                    if (bankingSystem.withdraw(withdrawAcc, withdrawAmount)) {
                        System.out.println("Withdrawal successful.");
                    } else {
                        System.out.println("Withdrawal failed.");
                    }
                    break;
                case 6:
                    System.out.print("Enter account number: ");
                    String balanceAcc = scanner.nextLine();
                    Double balance = bankingSystem.checkBalance(balanceAcc);
                    if (balance != null) {
                        System.out.println("Account balance: " + balance);
                    } else {
                        System.out.println("Account not found or user not logged in.");
                    }
                    break;
                case 7:
                    System.out.print("Enter account number: ");
                    String displayAcc = scanner.nextLine();
                    bankingSystem.displayAccount(displayAcc);
                    break;
                case 8:
                    System.out.print("Enter account number to delete: ");
                    String deleteAcc = scanner.nextLine();
                    if (bankingSystem.deleteAccount(deleteAcc)) {
                        System.out.println("Account deleted successfully.");
                    } else {
                        System.out.println("Account not found or user not logged in.");
                    }
                    break;
                case 9:
                    if (bankingSystem.logout()) {
                        System.out.println("Logged out successfully.");
                    } else {
                        System.out.println("No user is logged in.");
                    }
                    break;
                case 10:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}
