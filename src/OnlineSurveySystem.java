import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class OnlineSurveySystem {
    public static void main(String[] args) {
        LoginForm loginForm = new LoginForm();
        loginForm.setVisible(true);
    }
}

class LoginForm extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;

    public LoginForm() {
        setTitle("Login Form");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);
        loginButton = new JButton("Login");

        panel.add(new JLabel("Username:"));
        panel.add(usernameField);
        panel.add(new JLabel("Password:"));
        panel.add(passwordField);
        panel.add(loginButton);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle login authentication
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                boolean authenticated = authenticate(username, password);
                if (authenticated) {
                    // Open main application window
                    SurveyCreationForm surveyCreationForm = new SurveyCreationForm();
                    surveyCreationForm.setVisible(true);
                    dispose(); // Close login form
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid username or password", "Login Failed", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        add(panel);
    }

    private boolean authenticate(String username, String password) {
        // Perform authentication logic (e.g., check against database)
        // For simplicity, hardcoding a sample authentication
        return username.equals("admin") && password.equals("admin");
    }
}

class SurveyCreationForm extends JFrame {
    private JButton createSurveyButton;

    public SurveyCreationForm() {
        setTitle("Survey Creation");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        createSurveyButton = new JButton("Create Survey");

        panel.add(createSurveyButton);

        createSurveyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle survey creation
                Survey survey = new Survey();
                // Logic for creating survey
                JOptionPane.showMessageDialog(null, "Survey Created Successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        add(panel);
    }
}

class Survey {
    // Survey implementation
}

class DatabaseManager {
    private static final String URL = "jdbc:mysql://localhost:3306/survey";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }
}