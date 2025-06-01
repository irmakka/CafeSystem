package pocket;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.awt.event.ActionEvent;

public class ManagerManagement extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JTextField textFieldEmail;
	private JTextField textFieldPhoneNumber;
	private JPasswordField passwordField;
	private JTextField textField_3;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ManagerManagement frame = new ManagerManagement();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ManagerManagement() {
		setTitle("Manager Management");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 809, 479);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(10, 29, 217, 391);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel lblName = new JLabel("Name :");
		lblName.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		lblName.setBounds(26, 61, 79, 13);
		panel.add(lblName);

		textField = new JTextField();
		textField.setBounds(111, 59, 96, 19);
		panel.add(textField);
		textField.setColumns(10);

		textFieldEmail = new JTextField();
		textFieldEmail.setBounds(111, 111, 96, 19);
		panel.add(textFieldEmail);
		textFieldEmail.setColumns(10);

		textFieldPhoneNumber = new JTextField();
		textFieldPhoneNumber.setBounds(111, 176, 96, 19);
		panel.add(textFieldPhoneNumber);
		textFieldPhoneNumber.setColumns(10);

		passwordField = new JPasswordField();
		passwordField.setBounds(111, 245, 96, 19);
		panel.add(passwordField);

		JLabel lblNewLabel = new JLabel("Password");
		lblNewLabel.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		lblNewLabel.setBounds(9, 247, 96, 13);
		panel.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Phone Number");
		lblNewLabel_1.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		lblNewLabel_1.setBounds(10, 178, 149, 13);
		panel.add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("Email :");
		lblNewLabel_2.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		lblNewLabel_2.setBounds(26, 113, 45, 13);
		panel.add(lblNewLabel_2);

		JButton btnNewButton = new JButton("Add Manager");
		btnNewButton.setBounds(38, 322, 133, 21);
		panel.add(btnNewButton);

		JLabel lblNewLabel_3 = new JLabel("Add Manager");
		lblNewLabel_3.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblNewLabel_3.setBounds(38, 10, 169, 25);
		panel.add(lblNewLabel_3);

		JPanel panel_1 = new JPanel();
		panel_1.setBounds(286, 29, 217, 391);
		panel_1.setLayout(null);
		contentPane.add(panel_1);

		JLabel lblName_1 = new JLabel("Name :");
		lblName_1.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		lblName_1.setBounds(21, 85, 79, 13);
		panel_1.add(lblName_1);

		textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setBounds(110, 83, 96, 19);
		panel_1.add(textField_3);

		JButton btnDeleteManager = new JButton("Delete Manager");
		btnDeleteManager.setBounds(37, 148, 140, 21);
		panel_1.add(btnDeleteManager);

		JLabel lblNewLabel_3_1 = new JLabel("Delete Manager");
		lblNewLabel_3_1.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblNewLabel_3_1.setBounds(38, 10, 169, 25);
		panel_1.add(lblNewLabel_3_1);

		JPanel panel_1_1 = new JPanel();
		panel_1_1.setBounds(549, 29, 217, 391);
		panel_1_1.setLayout(null);
		contentPane.add(panel_1_1);

		JTextArea textArea = new JTextArea();
		textArea.setBounds(22, 101, 185, 260);
		panel_1_1.add(textArea);

		JButton btnShowManagers = new JButton("Show Managers");
		btnShowManagers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textArea.setText(""); 

				try {
					DBHelper db = DBHelper.getInstance();
					String sql = "SELECT * FROM managers";
					ResultSet rs = db.executeQuery(sql);

					boolean found = false;
					while (rs.next()) {
						String name = rs.getString("username");
						String email = rs.getString("email");
						String phone = rs.getString("phone_number");

						textArea.append("Name: " + name + "\n");
						textArea.append("Email: " + (email != null ? email : "N/A") + "\n");
						textArea.append("Phone: " + (phone != null ? phone : "N/A") + "\n");
						textArea.append("----------------\n");
						found = true;
					}

					if (!found) {
						textArea.setText("No managers found.");
					}

					rs.close();
				} catch (SQLException ex) {
					JOptionPane.showMessageDialog(ManagerManagement.this,
							"Database error: " + ex.getMessage(),
							"Error",
							JOptionPane.ERROR_MESSAGE);
					ex.printStackTrace();
				}
			}
		});
		btnShowManagers.setBounds(38, 59, 140, 21);
		panel_1_1.add(btnShowManagers);

		JLabel lblNewLabel_3_1_1 = new JLabel("Show Managers");
		lblNewLabel_3_1_1.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblNewLabel_3_1_1.setBounds(38, 10, 169, 25);
		panel_1_1.add(lblNewLabel_3_1_1);

		// --- Add Manager Action ---
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = textField.getText().trim();
				String email = textFieldEmail.getText().trim();
				String phone = textFieldPhoneNumber.getText().trim();
				String password = String.valueOf(passwordField.getPassword()).trim();

				if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || password.isEmpty()) {
					JOptionPane.showMessageDialog(ManagerManagement.this,
							"Please fill in all fields.",
							"Input Error",
							JOptionPane.WARNING_MESSAGE);
					return;
				}

				try {
					DBHelper db = DBHelper.getInstance();

					String sql = "INSERT INTO managers (username, email, phone_number, password) VALUES (?, ?, ?, ?)";
					PreparedStatement pstmt = db.getConnection().prepareStatement(sql);
					pstmt.setString(1, name);
					pstmt.setString(2, email);
					pstmt.setString(3, phone);
					pstmt.setString(4, password);

					int rowsInserted = pstmt.executeUpdate();

					if (rowsInserted > 0) {
						JOptionPane.showMessageDialog(ManagerManagement.this,
								"Manager added successfully!",
								"Success",
								JOptionPane.INFORMATION_MESSAGE);

						// Alanları temizle
						textField.setText("");
						textFieldEmail.setText("");
						textFieldPhoneNumber.setText("");
						passwordField.setText("");
					}

					pstmt.close();
				} catch (SQLException ex) {
					JOptionPane.showMessageDialog(ManagerManagement.this,
							"Database error: " + ex.getMessage(),
							"Error",
							JOptionPane.ERROR_MESSAGE);
					ex.printStackTrace();
				}
			}
		});

		// --- Delete Manager Action ---
		btnDeleteManager.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String nameToDelete = textField_3.getText().trim();

				if (nameToDelete.isEmpty()) {
					JOptionPane.showMessageDialog(ManagerManagement.this,
							"Please enter a name to delete.",
							"Input Error",
							JOptionPane.WARNING_MESSAGE);
					return;
				}

				int confirm = JOptionPane.showConfirmDialog(ManagerManagement.this,
						"Are you sure you want to delete manager \"" + nameToDelete + "\"?",
						"Confirm Deletion",
						JOptionPane.YES_NO_OPTION);

				if (confirm != JOptionPane.YES_OPTION) {
					return;
				}

				try {
					DBHelper db = DBHelper.getInstance();

					String sql = "DELETE FROM managers WHERE username = ?";
					PreparedStatement pstmt = db.getConnection().prepareStatement(sql);
					pstmt.setString(1, nameToDelete);

					int rowsDeleted = pstmt.executeUpdate();

					if (rowsDeleted > 0) {
						JOptionPane.showMessageDialog(ManagerManagement.this,
								"Manager deleted successfully!",
								"Success",
								JOptionPane.INFORMATION_MESSAGE);
						textField_3.setText("");
					} else {
						JOptionPane.showMessageDialog(ManagerManagement.this,
								"No manager found with that name.",
								"Not Found",
								JOptionPane.INFORMATION_MESSAGE);
					}

					pstmt.close();
				} catch (SQLException ex) {
					JOptionPane.showMessageDialog(ManagerManagement.this,
							"Database error: " + ex.getMessage(),
							"Error",
							JOptionPane.ERROR_MESSAGE);
					ex.printStackTrace();
				}
			}
		});
	}
}
