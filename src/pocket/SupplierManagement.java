package pocket;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;

public class SupplierManagement extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textFieldName;
	private JTextField textFieldContact;


	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SupplierManagement frame = new SupplierManagement();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	public SupplierManagement() {
		setTitle("Supplier Management");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 600, 496);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 36, 278, 409);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblSupplier = new JLabel("Add / Delete Supplier");
		lblSupplier.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblSupplier.setBounds(42, 10, 196, 24);
		panel.add(lblSupplier);
		
		JLabel lblSupplierName = new JLabel("Name :");
		lblSupplierName.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		lblSupplierName.setBounds(31, 87, 45, 13);
		panel.add(lblSupplierName);
		
		textFieldName = new JTextField();
		textFieldName.setBounds(130, 85, 96, 19);
		panel.add(textFieldName);
		textFieldName.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Contact Number :");
		lblNewLabel.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		lblNewLabel.setBounds(14, 144, 106, 13);
		panel.add(lblNewLabel);
		
		textFieldContact = new JTextField();
		textFieldContact.setBounds(130, 142, 96, 19);
		panel.add(textFieldContact);
		textFieldContact.setColumns(10);
		
		JButton btnAdd = new JButton("Add Supplier");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name=textFieldName.getText().trim();
				String contactName=textFieldContact.getText().trim();
				 if(name.isEmpty() || contactName.isEmpty()) {
	        JOptionPane.showMessageDialog(SupplierManagement.this, "Name and Contact fields cannot be empty!");
	       }
				 else {
				 try {
	                    DBHelper db = DBHelper.getInstance();
	                    String sql = "INSERT INTO suppliers (name, contact_name) VALUES (?, ?)";
	                    int result = db.executeUpdate(sql, name, contactName);
	                    
	                    if(result > 0) {
	                        JOptionPane.showMessageDialog(SupplierManagement.this,"Supplier added successfully!");
	                        textFieldName.setText("");
	                        textFieldContact.setText("");
	                    }
	                } catch (SQLException ex) {
	                    JOptionPane.showMessageDialog(SupplierManagement.this, 
	                        "Database error: " + ex.getMessage(), 
	                        "Error", 
	                        JOptionPane.ERROR_MESSAGE);
	                    ex.printStackTrace();
	                }
				 }
				 
			}
		});
		btnAdd.setBounds(0, 193, 130, 33);
		panel.add(btnAdd);
		
		JButton btnDeleteSuppliers = new JButton("Delete Supplier");
		btnDeleteSuppliers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 String name = textFieldName.getText().trim();
	                
	        if(name.isEmpty()) {
	                    JOptionPane.showMessageDialog(SupplierManagement.this, "Please enter supplier name or to delete!");
	         }
	    
	        else {
	         int confirm = JOptionPane.showConfirmDialog(SupplierManagement.this, 
	          "Are you sure you want to delete supplier: " + name + "?",  "Confirm Delete", JOptionPane.YES_NO_OPTION);
	                    
	        if(confirm == JOptionPane.YES_OPTION) {

	        	  try {
	                    DBHelper db = DBHelper.getInstance();
	                    String sql = "DELETE FROM suppliers WHERE name = ?";
                        int result = db.executeUpdate(sql, name);
	                    
	                    if(result > 0) {
	                        JOptionPane.showMessageDialog(SupplierManagement.this, 
	                            "Supplier deleted successfully!", 
	                            "Success", 
	                            JOptionPane.INFORMATION_MESSAGE);
	                        textFieldName.setText("");
	                        textFieldContact.setText("");
	                    }
	                } catch (SQLException ex) {
	                    JOptionPane.showMessageDialog(SupplierManagement.this, 
	                        "Database error: " + ex.getMessage(), 
	                        "Error", 
	                        JOptionPane.ERROR_MESSAGE);
	                    ex.printStackTrace();
	                }
	    } 
	 }
	}
});
		btnDeleteSuppliers.setBounds(140, 193, 138, 33);
		panel.add(btnDeleteSuppliers);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(298, 36, 278, 409);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblShow = new JLabel("Suppliers");
		lblShow.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblShow.setBounds(97, 10, 119, 24);
		panel_1.add(lblShow);
		
		JTextArea textArea = new JTextArea();
		textArea.setBounds(10, 89, 260, 310);
		panel_1.add(textArea);
		
		JButton btnShow = new JButton("Show Suppliers");
		btnShow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 try {
	                    DBHelper db = DBHelper.getInstance();
	                    String sql = "SELECT name, contact_name FROM suppliers ORDER BY name";
	                    ResultSet rs = db.executeQuery(sql);
	                    
	                    textArea.setText(""); 
	                    textArea.append(String.format("%-20s %-15s\n", "Supplier Name", "Contact"));
	                    textArea.append("--------------------------------\n");
	                    while(rs.next()) {
	                        String name = rs.getString("name");
	                        String contact = rs.getString("contact_name");
	                        textArea.append(String.format("%-20s %-15s\n", name, contact));
	                    }
	                    
	                    if(textArea.getText().isEmpty()) {
	                        textArea.setText("No suppliers found in database.");
	                    }
	                    
	                    rs.close();
	                } catch (SQLException ex) {
	                    JOptionPane.showMessageDialog(SupplierManagement.this, 
	                        "Database error: " + ex.getMessage(), 
	                        "Error", 
	                        JOptionPane.ERROR_MESSAGE);
	                    ex.printStackTrace();
	                }
			}
		});
		btnShow.setBounds(72, 47, 133, 21);
		panel_1.add(btnShow);
	}
}
