package pocket;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

import javax.swing.JOptionPane;

public class DBHelper {
    private static DBHelper instance;
    private Connection connection;
    private static String DB_URL;
    private static String DB_USER;
    private static String DB_PASSWORD;
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    
    private DBHelper() {
        loadProperties();
        initialize();
    }
    
    private void loadProperties() {
        Properties props = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("db.properties")) {
            if (input == null) {
                System.err.println("HATA: db.properties dosyası bulunamadı! Dosya classpath içine konmalı.");
                return;
            }
            props.load(input);

            DB_URL = props.getProperty("db.url");
            DB_USER = props.getProperty("db.user");
            DB_PASSWORD = props.getProperty("db.password");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static synchronized DBHelper getInstance() {
        if (instance == null) {
            instance = new DBHelper();
        }
        return instance;
    }
    
    private void initialize() {
        try {
            Class.forName(JDBC_DRIVER);
            if (DB_URL == null || DB_USER == null || DB_PASSWORD == null) {
                throw new SQLException("Veritabanı bağlantı bilgileri eksik! db.properties kontrol et.");
            }
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            connection.setAutoCommit(true);
            System.out.println("Veritabanı bağlantısı başarılı.");
        } catch (ClassNotFoundException e) {
            showError("JDBC Driver bulunamadı: " + e.getMessage());
            connection = null;
        } catch (SQLException e) {
            showError("Veritabanı bağlantı hatası: " + e.getMessage());
            connection = null;
        }
    }
    
    public Connection getConnection() throws SQLException {
        try {
            if (connection == null || connection.isClosed()) {
                System.out.println("Bağlantı kapalı veya null, initialize çağrılıyor...");
                initialize();
            }
        } catch (SQLException e) {
            throw new SQLException("Bağlantı kontrol hatası: " + e.getMessage());
        }
        return connection;
    }
    


    public ResultSet executeQuery(String query, Object... params) throws SQLException {
        PreparedStatement pstmt = null;
        try {
            pstmt = getConnection().prepareStatement(query);
            setParameters(pstmt, params);
            return pstmt.executeQuery();
        } catch (SQLException e) {
            closeStatement(pstmt);
            throw e;
        }
    }
    
    public int executeUpdate(String query, Object... params) throws SQLException {
        try (PreparedStatement pstmt = getConnection().prepareStatement(query)) {
            setParameters(pstmt, params);
            return pstmt.executeUpdate();
        }
    }
    
    private void setParameters(PreparedStatement pstmt, Object... params) throws SQLException {
        if (params != null) {
            for (int i = 0; i < params.length; i++) {
                pstmt.setObject(i + 1, params[i]);
            }
        }
    }
    
    public void beginTransaction() throws SQLException {
        getConnection().setAutoCommit(false);
    }
    
    public void commit() throws SQLException {
        Connection conn = getConnection();
        conn.commit();
        conn.setAutoCommit(true);
    }
    
    public void rollback() {
        try {
            Connection conn = getConnection();
            if (conn != null && !conn.getAutoCommit()) {
                conn.rollback();
                conn.setAutoCommit(true);
            }
        } catch (SQLException e) {
            showError("Rollback hatası: " + e.getMessage());
        }
    }
    
    public void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            showError("Bağlantı kapatma hatası: " + e.getMessage());
        }
    }
    
    private void closeStatement(Statement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                showError("Statement kapatma hatası: " + e.getMessage());
            }
        }
    }
    
    
    private void closeResultSet(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                showError("ResultSet kapatma hatası: " + e.getMessage());
            }
        }
    }
    public int insertAndGetId(String sql, Object... params) throws SQLException {
        try (PreparedStatement stmt = getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            setParameters(stmt, params);
            stmt.executeUpdate();

            try (ResultSet keys = stmt.getGeneratedKeys()) {
                if (keys.next()) {
                    return keys.getInt(1);
                } else {
                    throw new SQLException("Otomatik üretilen ID alınamadı.");
                }
            }
        }
    }

    
    public void showError(String message) {
        JOptionPane.showMessageDialog(null, 
            message, 
            "Veritabanı Hatası", 
            JOptionPane.ERROR_MESSAGE);
    }
}