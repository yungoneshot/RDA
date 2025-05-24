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

public class pregledPacijenata extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	JTextArea textAreaPopis;
	private int currentUserId;
    private String currentUsername;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			pregledPacijenata dialog = new pregledPacijenata(1, "testUser");
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public pregledPacijenata(int userId, String username) {
		this.currentUserId = userId;
        this.currentUsername = username;
        
		setTitle("Popis pacijenata");
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
		
		JButton btnunosPacijenta = new JButton("Unos pacijenta");
		btnunosPacijenta.addActionListener(e ->{
			dispose();
			new UnosPacijent(currentUserId, currentUsername).setVisible(true);
		});
		btnunosPacijenta.setBounds(462, 312, 89, 23);
		contentPanel.add(btnunosPacijenta);
		
		
		
		
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
		}
		popisPacijenata();
	}
	public void popisPacijenata() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			Connection conn = DriverManager.
					getConnection("jdbc:mysql://ucka.veleri.hr:3306/dsepic?user=dsepic&password=11");
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM PACIJENT");
			while (rs.next()) {
				int id_user =rs.getInt(1);
				String Ime_pacijent = rs.getString(2);
				String Prezime_pacijent = rs.getString(3);
				String DOB_Pacijent = rs.getString(4);
				String Spol_pacijent = rs.getString(5);
				
				textAreaPopis.append(id_user+" "+Ime_pacijent+" "+Prezime_pacijent+" "+DOB_Pacijent+" "+Spol_pacijent+ "\n");
			}
			conn.close();
		} catch (Exception ex) {
			System.out.println(ex.toString());
		}

	}
	}