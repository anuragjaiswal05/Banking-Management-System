import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

class Account {
    private String accountNumber;
    private String name;
    private double balance;
    private List<String> transactionHistory;

    public Account(String accountNumber, String name, double initialBalance) {
        this.accountNumber = accountNumber;
        this.name = name;
        this.balance = initialBalance;
        this.transactionHistory = new ArrayList<>();
        addTransaction("Account created with balance: " + initialBalance);
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getName() {
        return name;
    }

    public double getBalance() {
        return balance;
    }

    public boolean deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            addTransaction("Deposited: " + amount);
            return true;
        }
        return false;
    }

    public boolean withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            addTransaction("Withdrew: " + amount);
            return true;
        }
        return false;
    }

    public List<String> getTransactionHistory() {
        return transactionHistory;
    }

    private void addTransaction(String transaction) {
        transactionHistory.add(transaction);
    }

    @Override
    public String toString() {
        return "Account Number: " + accountNumber + ", Name: " + name + ", Balance: " + balance;
    }
}


class BankingSystem {
    private Map<String, Account> accounts = new HashMap<>();
    private Map<String, String> users = new HashMap<>(); // User ID and Password
    private String loggedInUser = null;

    public boolean createAccount(String accountNumber, String name, double initialBalance) {
        if (!accounts.containsKey(accountNumber)) {
            accounts.put(accountNumber, new Account(accountNumber, name, initialBalance));
            return true;
        }
        return false;
    }

    public boolean login(String userId, String password) {
        if (users.containsKey(userId) && users.get(userId).equals(password)) {
            loggedInUser = userId;
            return true;
        }
        return false;
    }

    public boolean registerUser(String userId, String password) {
        if (!users.containsKey(userId)) {
            users.put(userId, password);
            return true;
        }
        return false;
    }

    public boolean deposit(String accountNumber, double amount) {
        if (loggedInUser != null) {
            Account account = accounts.get(accountNumber);
            if (account != null) {
                return account.deposit(amount);
            }
        }
        return false;
    }

    public boolean withdraw(String accountNumber, double amount) {
        if (loggedInUser != null) {
            Account account = accounts.get(accountNumber);
            if (account != null) {
                return account.withdraw(amount);
            }
        }
        return false;
    }

    public Double checkBalance(String accountNumber) {
        if (loggedInUser != null) {
            Account account = accounts.get(accountNumber);
            if (account != null) {
                return account.getBalance();
            }
        }
        return null;
    }

    public void displayAccount(String accountNumber) {
        if (loggedInUser != null) {
            Account account = accounts.get(accountNumber);
            if (account != null) {
                System.out.println(account);
                System.out.println("Transaction History:");
                for (String transaction : account.getTransactionHistory()) {
                    System.out.println(transaction);
                }
            } else {
                System.out.println("Account not found.");
            }
        } else {
            System.out.println("Please login first.");
        }
    }

    public boolean logout() {
        if (loggedInUser != null) {
            loggedInUser = null;
            return true;
        }
        return false;
    }
}

public class bapp {
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
            System.out.println("8. Logout");
            System.out.println("9. Exit");
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
                    userId = scanner.nextLine();
                    System.out.print("Enter password: ");
                    password = scanner.nextLine();
                    if (bankingSystem.login(userId, password)) {
                        System.out.println("Login successful.");
                    } else {
                        System.out.println("Login failed.");
                    }
                    break;

                case 3:
                    System.out.print("Enter account number: ");
                    String accNum = scanner.nextLine();
                    System.out.print("Enter name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter initial balance: ");
                    double initialBalance = scanner.nextDouble();
                    if (bankingSystem.createAccount(accNum, name, initialBalance)) {
                        System.out.println("Account created successfully.");
                    } else {
                        System.out.println("Account already exists.");
                    }
                    break;
                
                case 4:
                    System.out.print("Enter account number: ");
                    accNum = scanner.nextLine();
                    System.out.print("Enter amount to deposit: ");
                    double depositAmount = scanner.nextDouble();
                    if (bankingSystem.deposit(accNum, depositAmount)) {
                        System.out.println("Deposit successful.");
                    } else {
                        System.out.println("Deposit failed.");
                    }
                    break;

                case 5:
                    System.out.print("Enter account number: ");
                    accNum = scanner.nextLine();
                    System.out.print("Enter amount to withdraw: ");
                    double withdrawAmount = scanner.nextDouble();
                    if (bankingSystem.withdraw(accNum, withdrawAmount)) {
                        System.out.println("Withdrawal successful.");
                    } else {
                        System.out.println("Withdrawal failed.");
                    }
                    break;

                case 6:
                    System.out.print("Enter account number: ");
                    accNum = scanner.nextLine();
                    Double balance = bankingSystem.checkBalance(accNum);
                    if (balance != null) {
                        System.out.println("Balance: " + balance);
                    } else {
                        System.out.println("Account not found.");
                    }
                    break;

                case 7:
                    System.out.print("Enter account number: ");
                    accNum = scanner.nextLine();
                    bankingSystem.displayAccount(accNum);
                    break;

                case 8:
                    if (bankingSystem.logout()) {
                        System.out.println("Logged out successfully.");
                    } else {
                        System.out.println("No user is logged in.");
                    }
                    break;

                case 9:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}
