package mediasist;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class MainScreen extends JFrame {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainScreen main = new MainScreen();
            main.setVisible(true);
        });
    }

    public MainScreen() {
        setTitle("MediAsist - Main Screen");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window

        // Main panel with GridBagLayout
        JPanel panel = new JPanel(new GridBagLayout());
        getContentPane().add(panel);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Title label
        JLabel titleLabel = new JLabel("Welcome to MediAsist", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        gbc.gridy = 0;
        panel.add(titleLabel, gbc);

        // Login button
        JButton loginButton = new JButton("Login");
        gbc.gridy = 1;
        panel.add(loginButton, gbc);

        // Register button
        JButton registerButton = new JButton("Register");
        gbc.gridy = 2;
        panel.add(registerButton, gbc);

        // Button actions
        loginButton.addActionListener((ActionEvent e) -> {
            try {
                dispose(); // Close main screen
                login.main(null); // Open login screen
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error opening Login screen.");
            }
        });

        registerButton.addActionListener((ActionEvent e) -> {
            try {
                dispose(); // Close main screen
                register.main(null); // Open register screen
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error opening Register screen.");
            }
        });
    }
}