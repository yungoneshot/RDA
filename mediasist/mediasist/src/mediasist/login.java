package mediasist;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class login extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JLabel statusLabel;

    public login() {
        super("User Login");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setLayout(null);

        JLabel lblUsername = new JLabel("Username:");
        lblUsername.setBounds(255, 200, 80, 25);
        getContentPane().add(lblUsername);

        usernameField = new JTextField();
        usernameField.setBounds(355, 200, 180, 25);
        getContentPane().add(usernameField);

        JLabel lblPassword = new JLabel("Password:");
        lblPassword.setBounds(255, 240, 80, 25);
        getContentPane().add(lblPassword);

        passwordField = new JPasswordField();
        passwordField.setBounds(355, 240, 180, 25);
        getContentPane().add(passwordField);

        JButton btnLogin = new JButton("Login");
        btnLogin.setBounds(355, 290, 100, 30);
        getContentPane().add(btnLogin);

        JButton btnBack = new JButton("Back");
        btnBack.setBounds(255, 290, 80, 30);
        getContentPane().add(btnBack);

        statusLabel = new JLabel();
        statusLabel.setBounds(50, 200, 300, 25);
        getContentPane().add(statusLabel);

        btnLogin.addActionListener(e -> attemptLogin());
        btnBack.addActionListener(e -> {
            dispose();
            new MainScreen().setVisible(true);
        });
    }

    private void attemptLogin() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            statusLabel.setText("Please enter both username and password.");
            return;
        }

        final String DB_URL = "jdbc:mysql://ucka.veleri.hr:3306/dsepic?useSSL=false&serverTimezone=UTC";
        final String DB_USER = "dsepic";
        final String DB_PW = "11";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PW)) {
            String sql = "SELECT id_user, username FROM users WHERE username = ? AND password = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String loggedInUsername = rs.getString("username");
                int userId = rs.getInt("id_user");
                dispose();
                new AppPage(loggedInUsername, userId).setVisible(true);
            } else {
                statusLabel.setText("Invalid credentials.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            statusLabel.setText("Database error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new login().setVisible(true));
    }
}