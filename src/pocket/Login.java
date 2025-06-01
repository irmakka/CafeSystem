package pocket;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class Login extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPasswordField passwordFieldUser;
	private JTextField textFieldUsername;
	private JTextField textFieldManagerUsername;
	private JPasswordField passwordFieldManager;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
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
	public Login() {
		setTitle("Login Page");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 617, 469);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 175, 175));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblLogin = new JLabel("Login");
		lblLogin.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblLogin.setBounds(270, 28, 161, 39);
		contentPane.add(lblLogin);
		
		JPanel panel = new JPanel();
		panel.setBounds(21, 139, 253, 231);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.setBounds(40, 171, 68, 21);
		panel.add(btnLogin);
		
		JButton btnSignup = new JButton("SignUp");
		btnSignup.setBounds(134, 171, 85, 21);
		panel.add(btnSignup);
		
		JLabel lblNewLabel = new JLabel("Password :");
		lblNewLabel.setBounds(40, 119, 68, 13);
		panel.add(lblNewLabel);
		lblNewLabel.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		
		passwordFieldUser = new JPasswordField();
		passwordFieldUser.setBounds(134, 117, 101, 19);
		panel.add(passwordFieldUser);
		
		textFieldUsername = new JTextField();
		textFieldUsername.setBounds(134, 58, 96, 19);
		panel.add(textFieldUsername);
		textFieldUsername.setColumns(10);
		
		JLabel lblUsername = new JLabel("Username :");
		lblUsername.setBounds(40, 56, 68, 21);
		panel.add(lblUsername);
		lblUsername.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		
		JLabel lblUserEnterance = new JLabel("User Enterance");
		lblUserEnterance.setBounds(82, 104, 150, 24);
		contentPane.add(lblUserEnterance);
		lblUserEnterance.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(327, 139, 227, 225);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblManagerName = new JLabel("Username: ");
		lblManagerName.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		lblManagerName.setBounds(28, 58, 71, 13);
		panel_1.add(lblManagerName);
		
		textFieldManagerUsername = new JTextField();
		textFieldManagerUsername.setBounds(109, 56, 96, 19);
		panel_1.add(textFieldManagerUsername);
		textFieldManagerUsername.setColumns(10);
		
		JLabel lblManagerPassword = new JLabel("Password:");
		lblManagerPassword.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		lblManagerPassword.setBounds(26, 122, 89, 21);
		panel_1.add(lblManagerPassword);
		
		JButton btnManagerLogin = new JButton("Login");
		btnManagerLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String username=textFieldManagerUsername.getText();
				 char[] passwordChars = passwordFieldManager.getPassword();
			      String password = new String(passwordChars); 

				 if(username.isEmpty()||password.isEmpty()) {
					 JOptionPane.showMessageDialog(Login.this,"Username and password can't be empty.");
				 }
				 else {
					 try {
	                 DBHelper db = DBHelper.getInstance();
	                 String sql = "Select * from managers where username=? AND password=?";
	                 ResultSet rs = db.executeQuery(sql, username, password);
	                 if(rs.next()) {
	                   JOptionPane.showMessageDialog(Login.this, "Enterence is successful!");
	                     
	                     int userId = rs.getInt("id");
	                     String fullName = rs.getString("username");
	                     textFieldManagerUsername.setText("");
	                     passwordFieldManager.setText("");
	                     Yönetim management= new Yönetim();
	                     management.setVisible(true);	                     
	                     dispose();
	                 } else {
	                     JOptionPane.showMessageDialog(Login.this, 
	                         "Wrong username and password is wrong!", 
	                         "Wrong Enterance", 
	                         JOptionPane.ERROR_MESSAGE);
	                 }
	                
	           }
				  catch(SQLException ex) {
			            JOptionPane.showMessageDialog(Login.this, 
			                "Database Error: " + ex.getMessage(), 
			                "Error", 
			                JOptionPane.ERROR_MESSAGE);
			            ex.printStackTrace();
			        } finally {
			            Arrays.fill(passwordChars, '0');
			        }
				 }			
			}
		});
		btnManagerLogin.setBounds(82, 171, 68, 21);
		panel_1.add(btnManagerLogin);
		
		passwordFieldManager = new JPasswordField();
		passwordFieldManager.setBounds(110, 122, 95, 19);
		panel_1.add(passwordFieldManager);
		
		JLabel lblManagerEnterance = new JLabel("Manager  Enterance");
		lblManagerEnterance.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		lblManagerEnterance.setBounds(373, 104, 150, 24);
		contentPane.add(lblManagerEnterance);
		btnSignup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SignUp signup =new SignUp();
				signup.setVisible(true);
				dispose();
			}
		});
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			 String username=textFieldUsername.getText();
			 char[] passwordChars = passwordFieldUser.getPassword();
		      String password = new String(passwordChars); 

			 if(username.isEmpty()||password.isEmpty()) {
				 JOptionPane.showMessageDialog(Login.this,"Username and password can't be empty.");
			 }
			 else {
				 try {
                 DBHelper db = DBHelper.getInstance();
                 String sql = "Select * from customers where username=? AND password=?";
                 ResultSet rs = db.executeQuery(sql, username, password);
                 if(rs.next()) {
                   JOptionPane.showMessageDialog(Login.this, "Enterence is successful");
                     
                     int userId = rs.getInt("id");
                     String fullName = rs.getString("username");
                     textFieldUsername.setText("");
                     passwordFieldUser.setText("");
                     UserSession.setUserId(userId); 
                     CafeSystem cafeSystem = new CafeSystem(userId);
                     cafeSystem.setVisible(true);
                     dispose();
                 } else {
                     JOptionPane.showMessageDialog(Login.this, 
                         "Wrong Username or Password!", 
                         "Enterance failed", 
                         JOptionPane.ERROR_MESSAGE);
                 }
                
           }
			  catch(SQLException ex) {
		            JOptionPane.showMessageDialog(Login.this, 
		                "DataBase Error: " + ex.getMessage(), 
		                "Error", 
		                JOptionPane.ERROR_MESSAGE);
		            ex.printStackTrace();
		        } finally {
		            Arrays.fill(passwordChars, '0');
		        }
			 }		
		}
		});
	}
}
