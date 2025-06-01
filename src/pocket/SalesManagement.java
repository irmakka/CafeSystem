package pocket;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class SalesManagement extends JFrame {

    private JPanel mainPanel;
    private JButton salesReportButton;

    public SalesManagement() {
        setTitle("Sales Management");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        mainPanel = new JPanel();
        mainPanel.setLayout(new FlowLayout());

        salesReportButton = new JButton("Sales Report");
        salesReportButton.setBackground(new Color(255, 102, 178));
        salesReportButton.setForeground(Color.WHITE);
        salesReportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showSalesReport();
            }
        });

        mainPanel.add(salesReportButton);

        getContentPane().add(mainPanel);
        setVisible(true);
    }

    private void showSalesReport() {
        String dateInput = JOptionPane.showInputDialog(this, "Enter a date (YYYY-MM-DD):");

        if (dateInput == null || dateInput.trim().isEmpty()) {
            return;
        }

        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            Connection conn = DBHelper.getInstance().getConnection();

            String query = "SELECT p.name, SUM(oi.quantity) AS total_quantity " +
                           "FROM orders o " +
                           "JOIN order_items oi ON o.id = oi.order_id " +
                           "JOIN products p ON oi.product_id = p.id " +
                           "WHERE DATE(o.date) = ? " +
                           "GROUP BY p.name";

            stmt = conn.prepareStatement(query);
            stmt.setString(1, dateInput);
            rs = stmt.executeQuery();

            StringBuilder report = new StringBuilder();
            report.append(dateInput).append(" Report with a date :\n\n");

            boolean hasResults = false;
            while (rs.next()) {
                String productName = rs.getString("name");
                int totalQuantity = rs.getInt("total_quantity");
                report.append("- ").append(productName).append(": ").append(totalQuantity).append(" quantity\n");
                hasResults = true;
            }

            if (!hasResults) {
                report.append("There is no sales on this date.");
            }

            JOptionPane.showMessageDialog(this, report.toString(), "Sales Report", JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Hata: " + ex.getMessage(), "Veritabanı Hatası", JOptionPane.ERROR_MESSAGE);
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception ignored) {}
            try { if (stmt != null) stmt.close(); } catch (Exception ignored) {}
         
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SalesManagement());
    }
}
