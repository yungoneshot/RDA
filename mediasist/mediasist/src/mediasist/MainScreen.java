package mediasist;

import javax.swing.*;
import java.awt.*;

public class MainScreen extends JFrame {
    
    public MainScreen() {
        super("MediAsist");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setLayout(null);

        JButton btnLogin = new JButton("Login");
        btnLogin.setBounds(291, 216, 155, 45);
        getContentPane().add(btnLogin);
        btnLogin.addActionListener(e -> {
            new login().setVisible(true);
            dispose();
        });

        JButton btnRegister = new JButton("Register");
        btnRegister.setBounds(294, 273, 155, 45);
        getContentPane().add(btnRegister);
        btnRegister.addActionListener(e -> {
            new register().setVisible(true);
            dispose();
        });

        JButton btnExit = new JButton("Exit");
        btnExit.setBounds(294, 332, 155, 45);
        getContentPane().add(btnExit);
        
        JLabel lblNewLabel = new JLabel("MediAsist");
        lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 24));
        lblNewLabel.setBounds(318, 111, 107, 61);
        getContentPane().add(lblNewLabel);
        btnExit.addActionListener(e -> System.exit(0));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainScreen().setVisible(true));
    }
}