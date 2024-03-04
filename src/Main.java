import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

// User class to represent each user of the ATM
class User {
    private String userID;
    private String userPIN;
    private double accountBalance;

    // Constructor
    public User(String userID, String userPIN, double accountBalance) {
        this.userID = userID;
        this.userPIN = userPIN;
        this.accountBalance = accountBalance;
    }

    // Getters and setters
    public String getUserID() {
        return userID;
    }

    public String getUserPIN() {
        return userPIN;
    }

    public double getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(double accountBalance) {
        this.accountBalance = accountBalance;
    }
}

// ATM class to encapsulate ATM functionalities
class ATM {
    Map<String, User> users;

    // Constructor
    public ATM() {
        users = new HashMap<>();
    }

    // Method to add a user to the ATM
    public void addUser(User user) {
        users.put(user.getUserID(), user);
    }

    // Method to check balance
    public double checkBalance(String userID) {
        if (users.containsKey(userID)) {
            return users.get(userID).getAccountBalance();
        } else {
            return -1; // Indicates user not found
        }
    }

    // Method to withdraw money
    public void withdraw(String userID, double amount) {
        if (users.containsKey(userID)) {
            User user = users.get(userID);
            if (amount <= user.getAccountBalance()) {
                user.setAccountBalance(user.getAccountBalance() - amount);
                System.out.println("Withdrawal successful. Remaining balance: $" + user.getAccountBalance());
            } else {
                System.out.println("Insufficient funds");
            }
        } else {
            System.out.println("User not found");
        }
    }

    // Method to deposit money
    public void deposit(String userID, double amount) {
        if (users.containsKey(userID)) {
            User user = users.get(userID);
            user.setAccountBalance(user.getAccountBalance() + amount);
            System.out.println("Deposit successful. New balance: $" + user.getAccountBalance());
        } else {
            System.out.println("User not found");
        }
    }
}

// Main class
public class Main {
    public static void main(String[] args) {
        // Create ATM object
        ATM atm = new ATM();

        // Sample user data
        User user1 = new User("123456", "7890", 1000.00);
        User user2 = new User("987654", "0987", 500.00);

        // Add users to the ATM
        atm.addUser(user1);
        atm.addUser(user2);

        // Create Scanner object to read user input
        Scanner scanner = new Scanner(System.in);

        // Prompt user to enter userID and PIN
        System.out.println("Welcome to the ATM. Please enter your userID:");
        String userIDInput = scanner.nextLine();
        System.out.println("Please enter your PIN:");
        String pinInput = scanner.nextLine();

        // Authenticate user
        if (atm.checkBalance(userIDInput) != -1 && pinInput.equals(atm.users.get(userIDInput).getUserPIN())) {
            System.out.println("Authentication successful.");

            // Loop for repeating options process
            boolean exit = false;
            while (!exit) {
                // Display options
                System.out.println("\nChoose an option:");
                System.out.println("1. Check Balance");
                System.out.println("2. Withdraw Money");
                System.out.println("3. Deposit Money");
                System.out.println("4. Exit");
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        System.out.println("Your current balance: $" + atm.checkBalance(userIDInput));
                        break;
                    case 2:
                        System.out.println("Enter the amount to withdraw:");
                        double withdrawAmount = scanner.nextDouble();
                        atm.withdraw(userIDInput, withdrawAmount);
                        break;
                    case 3:
                        System.out.println("Enter the amount to deposit:");
                        double depositAmount = scanner.nextDouble();
                        atm.deposit(userIDInput, depositAmount);
                        break;
                    case 4:
                        System.out.println("Exiting. Thank you!");
                        exit = true; // Exit the loop
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        } else {
            System.out.println("Invalid userID or PIN. Please try again.");
        }

        // Close the scanner
        scanner.close();
    }
}
