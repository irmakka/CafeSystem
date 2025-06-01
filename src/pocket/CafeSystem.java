package pocket;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.awt.Image;
import javax.swing.ImageIcon;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class CafeSystem extends JFrame {
    private int userId;
    private ArrayList<Product> orders = new ArrayList<>();
    private double total = 0;
    private JTextArea orderArea;
    private JLabel totalLabel;
    private DBHelper dbHelper;
    private JTextField addressField;

    public CafeSystem(int userId) {
        this.userId = userId;
        dbHelper = DBHelper.getInstance();
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Cafe Order System");
        setSize(900, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(255, 230, 242));
        getContentPane().setLayout(new BorderLayout(10, 10));

        JLabel title = new JLabel("Cafe Order System", JLabel.CENTER);
        title.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 24));
        title.setForeground(Color.WHITE);
        title.setOpaque(true);
        title.setBackground(new Color(255, 102, 178));
        getContentPane().add(title, BorderLayout.NORTH);

        List<Product> products = getProductsFromDatabase();
        JPanel productPanel = createProductPanel(products);
        JScrollPane productScrollPane = new JScrollPane(productPanel);
        productScrollPane.setBorder(BorderFactory.createEmptyBorder());
        getContentPane().add(productScrollPane, BorderLayout.CENTER);

        JPanel orderPanel = createOrderPanel();
        getContentPane().add(orderPanel, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
    }

    private List<Product> getProductsFromDatabase() {
        List<Product> products = new ArrayList<>();
        ResultSet rs = null;
        try {
            String query = "SELECT p.id, p.name AS product_name, p.price, p.image_path, c.name AS category_name " +
                    "FROM products p JOIN categories c ON p.category_id = c.id ORDER BY c.name, p.name";
            rs = dbHelper.executeQuery(query);
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("product_name");
                double price = rs.getDouble("price");
                String imagePath = rs.getString("image_path");
                String category = rs.getString("category_name");
                products.add(new Product(id, name, price, imagePath, category));
            }
        } catch (SQLException e) {
            dbHelper.showError("A problem while loading :\n" + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return products;
    }

    private JPanel createOrderPanel() {
        JPanel orderPanel = new JPanel(new BorderLayout());
        orderPanel.setBackground(new Color(255, 240, 245));
        orderPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        orderArea = new JTextArea(8, 40);
        orderArea.setEditable(false);
        orderArea.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 24));
        orderArea.setBackground(Color.WHITE);
        JScrollPane orderScrollPane = new JScrollPane(orderArea);
        orderScrollPane.setBorder(BorderFactory.createTitledBorder("Order Details"));

        totalLabel = new JLabel("Total: 0.00₺", JLabel.CENTER);
        totalLabel.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 18));
        totalLabel.setForeground(new Color(255, 102, 178));

        addressField = new JTextField(30);
        addressField.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 14));
        addressField.setBorder(BorderFactory.createTitledBorder("Delivery Address"));

        JButton completeButton = new JButton("Finish Orders");
        completeButton.setBackground(new Color(255, 128, 255));
        completeButton.setForeground(new Color(255, 128, 192));
        completeButton.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 14));
        completeButton.setPreferredSize(new Dimension(200, 40));
        completeButton.addActionListener(e -> completeOrder());

        JButton clearButton = new JButton("Clear");
        clearButton.setBackground(Color.LIGHT_GRAY);
        clearButton.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 14));
        clearButton.setPreferredSize(new Dimension(120, 40));
        clearButton.addActionListener(e -> {
            orders.clear();
            total = 0;
            updateOrderDisplay();
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(new Color(255, 240, 245));
        buttonPanel.add(clearButton);
        buttonPanel.add(completeButton);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(new Color(255, 240, 245));
        bottomPanel.add(addressField, BorderLayout.NORTH);
        bottomPanel.add(totalLabel, BorderLayout.CENTER);
        bottomPanel.add(buttonPanel, BorderLayout.SOUTH);

        orderPanel.add(orderScrollPane, BorderLayout.CENTER);
        orderPanel.add(bottomPanel, BorderLayout.SOUTH);

        return orderPanel;
    }

    private JPanel createProductPanel(List<Product> products) {
        JPanel panel = new JPanel(new GridLayout(0, 4, 15, 15));
        panel.setBackground(new Color(255, 230, 242));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        for (Product product : products) {
            JPanel productCard = createProductCard(product);
            panel.add(productCard);
        }
        return panel;
    }

    private JPanel createProductCard(Product product) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        card.setBackground(Color.WHITE);
        card.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                addOrder(product);
            }
        });

        ImageIcon icon = loadProductImage(product.imagePath);
        JLabel imageLabel = new JLabel(icon, JLabel.CENTER);
        card.add(imageLabel, BorderLayout.CENTER);

        JLabel nameLabel = new JLabel(product.name, SwingConstants.CENTER);
        nameLabel.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 14));
        nameLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        card.add(nameLabel, BorderLayout.NORTH);

        JPanel infoPanel = new JPanel(new BorderLayout());
        infoPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));

        JLabel priceLabel = new JLabel(String.format("₺%.2f", product.price), SwingConstants.CENTER);
        priceLabel.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 16));
        priceLabel.setForeground(new Color(255, 102, 178));

        JLabel categoryLabel = new JLabel(product.category, SwingConstants.CENTER);
        categoryLabel.setFont(new java.awt.Font("Arial", java.awt.Font.ITALIC, 11));
        categoryLabel.setForeground(Color.GRAY);

        infoPanel.add(priceLabel, BorderLayout.CENTER);
        infoPanel.add(categoryLabel, BorderLayout.SOUTH);
        card.add(infoPanel, BorderLayout.SOUTH);

        return card;
    }

    private ImageIcon loadProductImage(String imagePath) {
        try {
            if (imagePath != null && !imagePath.isEmpty()) {
                if (!imagePath.startsWith("image/")) {
                    imagePath = "image/" + imagePath;
                }
                File imageFile = new File(imagePath);
                if (imageFile.exists()) {
                    ImageIcon icon = new ImageIcon(imagePath);
                    if (icon.getImageLoadStatus() == MediaTracker.COMPLETE) {
                        return scaleImageIcon(icon, 180, 180);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return createDefaultIcon();
    }

    private ImageIcon scaleImageIcon(ImageIcon icon, int width, int height) {
        Image scaledImage = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }

    private ImageIcon createDefaultIcon() {
        BufferedImage img = new BufferedImage(180, 180, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = img.createGraphics();
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, 180, 180);
        g2d.setColor(Color.LIGHT_GRAY);
        g2d.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 14));
        g2d.drawString("Resim Yok", 60, 90);
        g2d.dispose();
        return new ImageIcon(img);
    }

    private void addOrder(Product product) {
        orders.add(product);
        total += product.price;
        updateOrderDisplay();
    }

    private void updateOrderDisplay() {
        orderArea.setText("");
        Map<Product, Integer> productCountMap = new LinkedHashMap<>();
        for (Product product : orders) {
            productCountMap.put(product, productCountMap.getOrDefault(product, 0) + 1);
        }

        for (Map.Entry<Product, Integer> entry : productCountMap.entrySet()) {
            Product p = entry.getKey();
            int count = entry.getValue();
            orderArea.append(String.format("%-25s x%-2d %6.2f₺\n", p.name, count, (p.price * count)));
        }

        totalLabel.setText(String.format("Total: %.2f₺", total));
    }

    private void completeOrder() {
        if (orders.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No order , empty!", "Uyarı", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String address = addressField.getText().trim();
        if (address.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter delivery address!", "Uyarı", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            dbHelper.beginTransaction();

            String orderSql = "INSERT INTO orders (customer_id, total_price, address,date) VALUES (?, ?, ?, CURRENT_TIMESTAMP)";
            int orderId = dbHelper.insertAndGetId(orderSql, userId, total, address);

            String itemSql = "INSERT INTO order_items (order_id, product_id, quantity, total_price) VALUES (?, ?, ?, ?)";
            Map<Product, Integer> productCountMap = new LinkedHashMap<>();
            for (Product product : orders) {
                productCountMap.put(product, productCountMap.getOrDefault(product, 0) + 1);
            }

            for (Map.Entry<Product, Integer> entry : productCountMap.entrySet()) {
                Product p = entry.getKey();
                int quantity = entry.getValue();
                double totalPrice = quantity * p.price;
                dbHelper.executeUpdate(itemSql, orderId, p.id, quantity, totalPrice);
            }

            dbHelper.commit();
            createReceiptPDF(orderId, address, productCountMap);
            JOptionPane.showMessageDialog(this, "Your order is made !\n Reciept is saved on desktop.", "Thank you", JOptionPane.INFORMATION_MESSAGE);
            orders.clear();
            total = 0;
            addressField.setText("");
            updateOrderDisplay();

        } catch (SQLException e) {
            dbHelper.rollback();
            dbHelper.showError("Sipariş kaydedilirken hata oluştu:\n" + e.getMessage());
        } catch (Exception e) {
            dbHelper.showError("Fiş oluşturulurken hata oluştu:\n" + e.getMessage());
        }
    }

    private void createReceiptPDF(int orderId, String address, Map<Product, Integer> productCountMap) {
        Document document = new Document();
        File pdfFile;

        try {
            
            String userHome = System.getProperty("user.home");
            String desktopPath = userHome + File.separator + "Desktop";
            File desktopDir = new File(desktopPath);
            if (!desktopDir.exists()) {
                desktopDir = new File(userHome);
            }

           
            pdfFile = new File(desktopDir, "receipt" + orderId + ".pdf");
            PdfWriter.getInstance(document, new FileOutputStream(pdfFile));
            document.open();

            
            com.itextpdf.text.Font titleFont = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.HELVETICA, 18, com.itextpdf.text.Font.BOLD, BaseColor.BLUE);
            Paragraph title = new Paragraph("CAFE Order Receipt", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(20);
            document.add(title);

            
            com.itextpdf.text.Font infoFont = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.HELVETICA, 12);
            String orderDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
            document.add(new Paragraph("Order No: " + orderId, infoFont));
            document.add(new Paragraph("Date: " + orderDate, infoFont));
            document.add(new Paragraph("Address: " + address, infoFont));
            document.add(new Paragraph(" "));

            
            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100);
            table.setSpacingBefore(10);
            table.setSpacingAfter(10);

            com.itextpdf.text.Font headerFont = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.HELVETICA, 12, com.itextpdf.text.Font.BOLD, BaseColor.WHITE);
            String[] headers = {"Product Name", "Product Price", "Quantity", "Total"};
            for (String header : headers) {
                PdfPCell cell = new PdfPCell(new Phrase(header, headerFont));
                cell.setBackgroundColor(BaseColor.GRAY);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);
            }

            
            com.itextpdf.text.Font tableFont = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.HELVETICA, 10);
            for (Map.Entry<Product, Integer> entry : productCountMap.entrySet()) {
                Product p = entry.getKey();
                int qty = entry.getValue();
                double lineTotal = p.price * qty;

                table.addCell(new Phrase(p.name, tableFont));
                table.addCell(new Phrase(String.format("%.2f₺", p.price), tableFont));
                table.addCell(new Phrase(String.valueOf(qty), tableFont));
                table.addCell(new Phrase(String.format("%.2f₺", lineTotal), tableFont));
            }

            document.add(table);

          
            com.itextpdf.text.Font totalFont = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.HELVETICA, 12, com.itextpdf.text.Font.BOLD);
            Paragraph totalPara = new Paragraph("Total: " + String.format("%.2f₺", total), totalFont);
            totalPara.setAlignment(Element.ALIGN_RIGHT);
            document.add(totalPara);

            document.close();

           
            if (pdfFile.exists()) {
                Desktop.getDesktop().open(pdfFile);
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "A problem is face while generating pdf:\n" + e.getMessage(), "Hata", JOptionPane.ERROR_MESSAGE);
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new CafeSystem(1).setVisible(true);
        });
    }
}
