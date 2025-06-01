package pocket;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Yönetim extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Yönetim frame = new Yönetim();
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
	public Yönetim() {
		setTitle("Management System");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnStaffManagement = new JButton("Staff Management");
		btnStaffManagement.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				StaffManagement staffManagement= new StaffManagement();
				staffManagement.setVisible(true);
			}
		});
		btnStaffManagement.setBounds(229, 105, 160, 30);
		contentPane.add(btnStaffManagement);
		
		JButton btnManagerManagement = new JButton("Manager Management");
		btnManagerManagement.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ManagerManagement managerManagement= new ManagerManagement();
				managerManagement.setVisible(true);
			}
		});
		btnManagerManagement.setBounds(32, 105, 171, 30);
		contentPane.add(btnManagerManagement);
		
		JButton btnSales = new JButton("Sales Management");
		btnSales.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SalesManagement salesManagement= new SalesManagement();
				salesManagement.setVisible(true);
				
			}
		});
		btnSales.setBounds(229, 163, 160, 30);
		contentPane.add(btnSales);
		JButton btnSupplier = new JButton("Supplier Management");
		btnSupplier.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			 SupplierManagement supplierManagement= new SupplierManagement();
			 supplierManagement.setVisible(true);
			}
		});
		btnSupplier.setBounds(32, 163, 171, 30);
		contentPane.add(btnSupplier);
		
		JLabel lblNewLabel = new JLabel("Welcome to Management System");
		lblNewLabel.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblNewLabel.setBounds(78, 45, 280, 30);
		contentPane.add(lblNewLabel);
	}

}
