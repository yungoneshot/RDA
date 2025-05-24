package mediasist;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.text.SimpleDateFormat;

public class UnosPacijent extends JFrame {
	private JTextField imeField;
	private JTextField prezimeField;
	private JTextField dobField;
	private JTextField spolField;
	private JLabel statusLabel;
	private int currentUserId;
	private String currentUsername;

	public UnosPacijent(int userId, String username) {
		super("Add Patient");
		this.currentUserId = userId;
		this.currentUsername = username;

		setSize(800, 600);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		getContentPane().setLayout(null);

		JLabel lblIme = new JLabel("Ime:");
		lblIme.setBounds(50, 30, 190, 25);
		getContentPane().add(lblIme);

		imeField = new JTextField();
		imeField.setBounds(250, 30, 200, 25);
		getContentPane().add(imeField);

		JLabel lblPrezime = new JLabel("Prezime:");
		lblPrezime.setBounds(50, 70, 190, 25);
		getContentPane().add(lblPrezime);

		prezimeField = new JTextField();
		prezimeField.setBounds(250, 70, 200, 25);
		getContentPane().add(prezimeField);

		JLabel lblDob = new JLabel("Datum rođenja(YYYY-MM-DD):");
		lblDob.setBounds(50, 110, 190, 25);
		getContentPane().add(lblDob);

		dobField = new JTextField();
		dobField.setBounds(250, 110, 200, 25);
		dobField.setToolTipText("YYYY-MM-DD format");
		getContentPane().add(dobField);

		JLabel lblSpol = new JLabel("Spol (M/Ž):");
		lblSpol.setBounds(50, 150, 190, 25);
		getContentPane().add(lblSpol);

		spolField = new JTextField();
		spolField.setBounds(250, 150, 200, 25);
		getContentPane().add(spolField);

		JButton btnSave = new JButton("Save");
		btnSave.setBounds(160, 240, 100, 30);
		getContentPane().add(btnSave);
		btnSave.addActionListener(e -> saveData());

        JButton btnBack = new JButton("Back");
        btnBack.setBounds(50, 240, 100, 30);
        getContentPane().add(btnBack);
        btnBack.addActionListener(e -> {
            dispose();
            new AppPage(currentUsername, currentUserId).setVisible(true);
		});

		statusLabel = new JLabel();
		statusLabel.setBounds(50, 290, 400, 25);
		getContentPane().add(statusLabel);
	}

	private void saveData() {
		try {
			String ime = imeField.getText().trim();
			String prezime = prezimeField.getText().trim();
			String dob = dobField.getText().trim();
			String spol = spolField.getText().trim();

			if (ime.isEmpty() || prezime.isEmpty() || dob.isEmpty() || spol.isEmpty()) {
				statusLabel.setText("Please fill all fields");
				return;
			}

			new SimpleDateFormat("yyyy-MM-dd").parse(dob);

			if (!spol.equalsIgnoreCase("M") && !spol.equalsIgnoreCase("Ž")) {
				statusLabel.setText("Unos za spol mora biti M ili Ž");
				return;
			}

			final String DB_URL = "jdbc:mysql://ucka.veleri.hr:3306/dsepic?useSSL=false&serverTimezone=UTC";
			final String DB_USER = "dsepic";
			final String DB_PW = "11";

			try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PW)) {
				String sql = "INSERT INTO PACIJENT (Ime_pacijent, Prezime_pacijent, DOB_Pacijent, Spol_pacijent, id_user) VALUES (?, ?, ?, ?, ?)";
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setString(1, ime);
				ps.setString(2, prezime);
				ps.setString(3, dob);
				ps.setString(4, spol);
				ps.setInt(5, currentUserId);

				int rows = ps.executeUpdate();
				if (rows > 0) {
					statusLabel.setText("Pacijent pohranjen u BP!");
					clearFields();
				} else {
					statusLabel.setText("Error.");
				}
			}
		} catch (java.text.ParseException e) {
			statusLabel.setText("Invalid format. Use YYYY-MM-DD");
		} catch (Exception ex) {
			ex.printStackTrace();
			statusLabel.setText("Error: " + ex.getMessage());
		}
	}

	private void clearFields() {
		imeField.setText("");
		prezimeField.setText("");
		dobField.setText("");
		spolField.setText("");
	}
	
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new UnosPacijent(1, "TestKorisnik"));
    }
}