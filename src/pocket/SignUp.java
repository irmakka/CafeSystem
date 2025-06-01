package pocket;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class SignUp extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textFieldUserName;
	private JTextField textFieldEmail;
	private JTextField textFieldPhoneNumber;
	private JPasswordField passwordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SignUp frame = new SignUp();
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
	public SignUp() {
		setTitle("Signup");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 423, 469);
		contentPane = new JPanel();
		contentPane.setBackground(Color.PINK);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblSignUp = new JLabel(" Sign Up");
		lblSignUp.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblSignUp.setBounds(135, 33, 165, 40);
		contentPane.add(lblSignUp);
		
		JLabel lblUserName = new JLabel("UserName:");
		lblUserName.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		lblUserName.setBounds(44, 89, 74, 13);
		contentPane.add(lblUserName);
		
		textFieldUserName = new JTextField();
		textFieldUserName.setBounds(135, 83, 96, 19);
		contentPane.add(textFieldUserName);
		textFieldUserName.setColumns(10);
		
		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		lblEmail.setBounds(61, 142, 79, 13);
		contentPane.add(lblEmail);
		
		textFieldEmail = new JTextField();
		textFieldEmail.setBounds(135, 140, 96, 19);
		contentPane.add(textFieldEmail);
		textFieldEmail.setColumns(10);
		
		JLabel lblPhoneNumber = new JLabel("Phone Number:");
		lblPhoneNumber.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		lblPhoneNumber.setBounds(28, 191, 101, 13);
		contentPane.add(lblPhoneNumber);
		
		JLabel lblPassword = new JLabel("Password :");
		lblPassword.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		lblPassword.setBounds(28, 245, 79, 13);
		contentPane.add(lblPassword);
		
		textFieldPhoneNumber = new JTextField();
		textFieldPhoneNumber.setColumns(10);
		textFieldPhoneNumber.setBounds(135, 189, 96, 19);
		contentPane.add(textFieldPhoneNumber);
		
		
		passwordField = new JPasswordField();
		passwordField.setBounds(135, 243, 96, 19);
		contentPane.add(passwordField);
		
		JButton btnSignUp = new JButton("Signup");
		btnSignUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String username=textFieldUserName.getText();
				String email= textFieldEmail.getText();
				String phone_number=textFieldPhoneNumber.getText();
				char[] passwordChars = passwordField.getPassword();
			      String password = new String(passwordChars); 
				if(username.isEmpty()||email.isEmpty()||phone_number.isEmpty()||password.isEmpty()) {
					JOptionPane.showMessageDialog(SignUp.this, "Fill all the tasks");
				}
				else {
					 try {
						 DBHelper db = DBHelper.getInstance();
						 String sql = "INSERT INTO customers (username,email,phone_number,password) VALUES (?, ?, ?,?)";
						 int result = db.executeUpdate(sql, username,  email,phone_number,password);
						                    
						  if(result > 0) {
						  JOptionPane.showMessageDialog(SignUp.this,"successfully!");
						  textFieldUserName.setText("");
						  textFieldEmail.setText("");
						textFieldPhoneNumber.setText("");
						 passwordField.setText("");
				 }
				} 
				       catch (SQLException ex) {
					JOptionPane.showMessageDialog(SignUp.this, 
					"Database error: " + ex.getMessage(),  "Error",  JOptionPane.ERROR_MESSAGE);
				     ex.printStackTrace();
				 }}
					
					
				}
		});
		btnSignUp.setBounds(44, 310, 85, 21);
		contentPane.add(btnSignUp);
	
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Login login=new Login();
				login.setVisible(true);
				dispose();
			}
		});
		btnLogin.setBounds(161, 310, 85, 21);
		contentPane.add(btnLogin);
	}

}
