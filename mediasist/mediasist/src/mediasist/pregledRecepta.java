package mediasist;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.sql.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class pregledRecepta extends JDialog{
	
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	JTextArea textAreaPopis;
	private int currentUserId;
    private String currentUsername;

    public static void main(String[] args) {
		try {
			pregledRecepta dialog = new pregledRecepta(1, "testUser");
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public pregledRecepta(int userId, String username) {
		this.currentUsername = username;
        this.currentUserId = userId;
        
		setTitle("Popis recepta");
		setBounds(100, 100, 770, 395);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(21, 11, 325, 301);
		contentPanel.add(scrollPane);
		
		textAreaPopis = new JTextArea();
		scrollPane.setViewportView(textAreaPopis);
		
		JButton btnNazad = new JButton("Nazad");
		btnNazad.addActionListener(e -> {
			dispose();
            new AppPage(currentUsername, currentUserId).setVisible(true);
		});
		btnNazad.setBounds(655, 312, 89, 23);
		contentPanel.add(btnNazad);
		popisRecepta();
}
		public void popisRecepta() {
			try {
				Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
				Connection conn = DriverManager.
					getConnection("jdbc:mysql://ucka.veleri.hr:3306/dsepic?user=dsepic&password=11");
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT Ime_lijeka, Doza_lijeka, Upute_recept, Datum_recept FROM RECEPT");
				while (rs.next()) {
					String Ime_lijeka = rs.getString(1);
					String Doza_lijeka = rs.getString(2);
					String Upute_recept = rs.getString(3);
					String Datum_recept = rs.getString(4);
					
					textAreaPopis.append(Ime_lijeka+" "+Doza_lijeka+" "+Upute_recept+" "+Datum_recept+ "\n");
				}
				conn.close();
			} catch (Exception ex) {
				System.out.println(ex.toString());
			}

		}
}

