package pocket;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Panel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;

public class StaffManagement extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textFieldName;
	private JTextField textFieldEmail;
	private JTextField textFieldDeleteName;
	private JTextField textFieldWage;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
				
					StaffManagement frame = new StaffManagement();
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
	public StaffManagement() {
		setTitle("Staff Management");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 723, 417);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
    
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		Panel panel = new Panel();
		panel.setBounds(10, 10, 180, 314);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblAddStaff = new JLabel("Add Staff");
		lblAddStaff.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		lblAddStaff.setBounds(58, 21, 112, 26);
		panel.add(lblAddStaff);
		
		JLabel lblName = new JLabel("Name :");
		lblName.setBounds(19, 68, 45, 13);
		panel.add(lblName);
		
		textFieldName = new JTextField();
		textFieldName.setBounds(68, 65, 96, 19);
		panel.add(textFieldName);
		textFieldName.setColumns(10);
		
		JLabel lblPosition = new JLabel("Position :");
		lblPosition.setBounds(10, 112, 54, 13);
		panel.add(lblPosition);
		
		
		
		String [] positions= {"waiter","chef","cashier","barista","dishwasher","pastry chef","cleaner","delivery person"};
		JComboBox comboBoxPosition = new JComboBox(positions);
		comboBoxPosition.setBounds(68, 108, 96, 21);
		panel.add(comboBoxPosition);
		
		JLabel lblEmail = new JLabel("Email :");
		lblEmail.setBounds(19, 170, 54, 13);
		panel.add(lblEmail);
		
		textFieldEmail = new JTextField();
		textFieldEmail.setBounds(68, 167, 96, 19);
		panel.add(textFieldEmail);
		textFieldEmail.setColumns(10);
		
		textFieldWage = new JTextField();
		textFieldWage.setBounds(68, 215, 96, 19);
		panel.add(textFieldWage);
		textFieldWage.setColumns(10);
		
		  
		JButton btnAddStaff = new JButton("Add Staff");
        btnAddStaff.addActionListener(new ActionListener() {
	 public void actionPerformed(ActionEvent e) {
	  String name = textFieldName.getText();
      String position = comboBoxPosition.getSelectedItem().toString();
      String email = textFieldEmail.getText();
      String salaryText = textFieldWage.getText().trim();
   
	 if(name.isEmpty() || position.isEmpty()||email.isEmpty()||salaryText.isEmpty()) {
     JOptionPane.showMessageDialog(StaffManagement.this, "All are required!");}
	 

	 else {
		    double wage = Double.parseDouble(salaryText);             
      try {
		 DBHelper db = DBHelper.getInstance();
		 String sql = "INSERT INTO staff (name, position, email,wage) VALUES (?, ?, ?,?)";
		 int result = db.executeUpdate(sql, name, position, email,wage);
		                    
		  if(result > 0) {
		  JOptionPane.showMessageDialog(StaffManagement.this,"Staff added successfully!");
		  textFieldName.setText("");
		  textFieldEmail.setText("");
		 comboBoxPosition.setSelectedIndex(0);
		 textFieldWage.setText("");
 }
} 
       catch (SQLException ex) {
	JOptionPane.showMessageDialog(StaffManagement.this, 
	"Database error: " + ex.getMessage(),  "Error",  JOptionPane.ERROR_MESSAGE);
     ex.printStackTrace();
 }}
}});
		btnAddStaff.setBounds(42, 263, 85, 21);
		panel.add(btnAddStaff);
		
		JLabel lblWage = new JLabel("Wage :");
		lblWage.setBounds(19, 218, 45, 13);
		panel.add(lblWage);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(475, 10, 202, 314);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblShowStaff = new JLabel("Show Staff");
		lblShowStaff.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		lblShowStaff.setBounds(48, 22, 117, 24);
		panel_1.add(lblShowStaff);
		
		JComboBox comboBoxStaffPosition = new JComboBox(positions);
		comboBoxStaffPosition.setBounds(86, 63, 79, 21);
		panel_1.add(comboBoxStaffPosition);
		
		JLabel lblPositionStaff = new JLabel("Position :");
		lblPositionStaff.setBounds(20, 67, 56, 13);
		panel_1.add(lblPositionStaff);
		
		JTextArea textAreaStaffShow = new JTextArea();
		textAreaStaffShow.setBounds(0, 125, 202, 189);
		textAreaStaffShow.setEditable(false);
        textAreaStaffShow.setLineWrap(true);
        textAreaStaffShow.setWrapStyleWord(true);
		panel_1.add(textAreaStaffShow);
		
		
		JScrollPane scrollPane = new JScrollPane(textAreaStaffShow);
	    scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
	    scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(475, 138, 202, 185);
		contentPane.add(scrollPane);
		
		JButton btnShow = new JButton("Show");
		btnShow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String position =comboBoxStaffPosition.getSelectedItem().toString();
				  textAreaStaffShow.setText(" "); 
	                
	                try {
	                    DBHelper db = DBHelper.getInstance();
	                    String sql;
	                    ResultSet rs;
	                    
	                    if(position.equals("All")) {
	                        sql = "SELECT name, position, email,wage FROM staff ORDER BY position, name";
	                        rs = db.executeQuery(sql);
	                    } else {
	                        sql = "SELECT name, position, email,wage FROM staff WHERE position = ? ORDER BY name";
	                        rs = db.executeQuery(sql, position);
	                    }
	                    
	                    while(rs.next()) {
	                        String name = rs.getString("name");
	                        String pos = rs.getString("position");
	                        String email = rs.getString("email");
	                        double salary = rs.getDouble("wage"); 
	                        
	                        textAreaStaffShow.append("Name: " + name + "\n");
	                        textAreaStaffShow.append("Position: " + pos + "\n");
	                        textAreaStaffShow.append("Email: " + (email != null ? email : "N/A") + "\n");
	                        textAreaStaffShow.append("Salary: " + String.format("%,.2f TL", salary) + "\n"); 
	                        textAreaStaffShow.append("----------------\n");
	                    }
	                    
	                    if(textAreaStaffShow.getText().isEmpty()) {
	                        textAreaStaffShow.setText("No staff found for the selected position.");
	                    }
	                    
	                    rs.close();
	                } catch (SQLException ex) {
	                    JOptionPane.showMessageDialog(StaffManagement.this, 
	                        "Database error: " + ex.getMessage(), 
	                        "Error", 
	                        JOptionPane.ERROR_MESSAGE);
	                    ex.printStackTrace();
	                }
	            }
		});
		
		btnShow.setBounds(49, 94, 85, 21);
		panel_1.add(btnShow);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(216, 10, 233, 314);
		contentPane.add(panel_2);
		panel_2.setLayout(null);
		
		JLabel lblNameDelete = new JLabel("Name :");
		lblNameDelete.setBounds(19, 66, 63, 13);
		panel_2.add(lblNameDelete);
		
		textFieldDeleteName = new JTextField();
		textFieldDeleteName.setColumns(10);
		textFieldDeleteName.setBounds(83, 63, 96, 19);
		panel_2.add(textFieldDeleteName);
		
		JLabel lblDeleteStaff = new JLabel("Delete Staff");
		lblDeleteStaff.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		lblDeleteStaff.setBounds(67, 20, 112, 26);
		panel_2.add(lblDeleteStaff);
		
		JComboBox comboBoxPositionDelete = new JComboBox(positions);
		comboBoxPositionDelete.setBounds(83, 102, 96, 21);
		panel_2.add(comboBoxPositionDelete);
		
		JLabel lblPositionDelete = new JLabel("Position :");
		lblPositionDelete.setBounds(19, 106, 54, 13);
		panel_2.add(lblPositionDelete);
		
		JButton btnDeleteStaff_1 = new JButton("Delete Staff");
		btnDeleteStaff_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                String name = textFieldDeleteName.getText().trim();
                if(name.isEmpty()) 
                    JOptionPane.showMessageDialog(contentPane, 
                        "Please enter staff name to delete!", 
                        "Error", 
                        JOptionPane.ERROR_MESSAGE);
                else {
                	 int confirm = JOptionPane.showConfirmDialog(contentPane, 
                             "Are you sure you want to delete staff: " + name + "?", 
                             "Confirm Delete", 
                             JOptionPane.YES_NO_OPTION);
                        
                	   if(confirm == JOptionPane.YES_OPTION) {
                           try {
                               DBHelper db = DBHelper.getInstance();
                               String sql = "DELETE FROM staff WHERE name = ?";
                               int result = db.executeUpdate(sql, name);
                               
                               if(result > 0) {
                                   JOptionPane.showMessageDialog(contentPane, 
                                       "Staff deleted successfully!", 
                                       "Success", 
                                       JOptionPane.INFORMATION_MESSAGE);
                                   textFieldDeleteName.setText(""); }
                                   
                         else {
                                       JOptionPane.showMessageDialog(contentPane, 
                                           "Staff not found!", 
                                           "Error", 
                                           JOptionPane.ERROR_MESSAGE);
                               }
                  } catch (SQLException ex) {
                                   JOptionPane.showMessageDialog(contentPane, 
                                       "Database error: " + ex.getMessage(), 
                                       "Error", 
                                       JOptionPane.ERROR_MESSAGE);
                                   ex.printStackTrace();
                               }
                	
              } 
          }
			}
		});
		btnDeleteStaff_1.setBounds(67, 158, 112, 21);
		panel_2.add(btnDeleteStaff_1);
		
		
	
	}
}
