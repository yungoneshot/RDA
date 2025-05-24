package mediasist;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Statistika extends JFrame {
	public Statistika() {
	}
    private Connection connection;
    private int currentUserId;
    private String currentUsername;

    private JLabel avgLabel, minLabel, maxLabel;

    public Statistika(int userId, String username) {
        this.currentUserId = userId;
        this.currentUsername = username;

        setTitle("Statistika glukoze");
        setSize(400, 300);
        getContentPane().setLayout(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        connectToDatabase();

        JLabel titleLabel = new JLabel("Statistika za korisnika: " + username);
        titleLabel.setBounds(50, 20, 300, 30);
        getContentPane().add(titleLabel);

        avgLabel = new JLabel("Prosjek: ");
        avgLabel.setBounds(50, 70, 300, 30);
        getContentPane().add(avgLabel);

        minLabel = new JLabel("Min: ");
        minLabel.setBounds(50, 110, 300, 30);
        getContentPane().add(minLabel);

        maxLabel = new JLabel("Max: ");
        maxLabel.setBounds(50, 150, 300, 30);
        getContentPane().add(maxLabel);

        JButton btnBack = new JButton("Natrag");
        btnBack.setBounds(50, 200, 120, 30);
        btnBack.addActionListener(e -> dispose());
        getContentPane().add(btnBack);

        loadAndDisplayStats();
        setVisible(true);
    }

    private void connectToDatabase() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://ucka.veleri.hr:3306/dsepic?useSSL=false&serverTimezone=UTC";
            connection = DriverManager.getConnection(url, "dsepic", "11");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Database connection failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void loadAndDisplayStats() {
        List<Double> values = new ArrayList<>();
        try {
            String query = "SELECT Vrijednost_mjerenja FROM MJERENJA WHERE ID_user = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, currentUserId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                try {
                    values.add(Double.parseDouble(rs.getString("Vrijednost_mjerenja")));
                } catch (NumberFormatException ignored) {}
            }

            if (values.isEmpty()) {
                avgLabel.setText("Prosjek: N/A");
                minLabel.setText("Min: N/A");
                maxLabel.setText("Max: N/A");
                return;
            }

            double sum = 0, min = Double.MAX_VALUE, max = Double.MIN_VALUE;
            for (double val : values) {
                sum += val;
                if (val < min) min = val;
                if (val > max) max = val;
            }

            double avg = sum / values.size();
            avgLabel.setText(String.format("Prosjek: %.1f mg/dL", avg));
            minLabel.setText(String.format("Min: %.1f mg/dL", min));
            maxLabel.setText(String.format("Max: %.1f mg/dL", max));

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Greška pri dohvaćanju podataka: " + e.getMessage());
        }
    }
}
