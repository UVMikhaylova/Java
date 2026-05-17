package service;
import model.GameRecord;
import java.sql.*;
import java.util.*;

public class DatabaseService {
    private final String url = "jdbc:sqlite:games.db";

    public void initAndSave(List<GameRecord> records) throws SQLException {
        try (Connection conn = DriverManager.getConnection(url)) {
            createTable(conn);
            saveData(conn, records);
        }
    }

    private void createTable(Connection conn) throws SQLException {
        String sql = """
            CREATE TABLE IF NOT EXISTS games (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                name TEXT,
                platform TEXT, 
                year TEXT, 
                genre TEXT, 
                publisher TEXT,
                na_sales REAL, 
                eu_sales REAL, 
                jp_sales REAL, 
                other_sales REAL, 
                global_sales REAL
            );
            """;
        try (Statement stmt = conn.createStatement()) { stmt.execute(sql); }
    }

    private void saveData(Connection conn, List<GameRecord> records) throws SQLException {
        conn.setAutoCommit(false);
        String sql = "INSERT INTO games (name, platform, year, genre, publisher, na_sales, eu_sales, jp_sales, other_sales, global_sales) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            for (GameRecord g : records) {
                pstmt.setString(1, g.getName());
                pstmt.setString(2, g.getPlatform());
                pstmt.setString(3, g.getYear());
                pstmt.setString(4, g.getGenre());
                pstmt.setString(5, g.getPublisher());
                pstmt.setDouble(6, g.getNaSales());
                pstmt.setDouble(7, g.getEuSales());
                pstmt.setDouble(8, g.getJpSales());
                pstmt.setDouble(9, g.getOtherSales());
                pstmt.setDouble(10, g.getGlobalSales());
                pstmt.addBatch();
            }
            pstmt.executeBatch();
            conn.commit();
        } catch (SQLException e) { conn.rollback(); throw e; }
        finally { conn.setAutoCommit(true); }
    }

    public Map<String, Double> getAvgGlobalSalesByPlatform() throws SQLException {
        Map<String, Double> res = new LinkedHashMap<>();
        String sql = "SELECT platform, AVG(global_sales) as avg_sales FROM games GROUP BY platform ORDER BY avg_sales DESC";
        try (Connection c = DriverManager.getConnection(url);
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) res.put(rs.getString("platform"), rs.getDouble("avg_sales"));
        }
        return res;
    }

    public String getTopEuGame2000() throws SQLException {
        String sql = "SELECT name FROM games WHERE year = '2000' ORDER BY eu_sales DESC LIMIT 1";
        try (Connection c = DriverManager.getConnection(url);
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getString("name");
        }
        return "Нет данных";
    }

    public String getTopJpSports2000_2006() throws SQLException {
        String sql = "SELECT name FROM games WHERE genre = 'Sports' AND CAST(year AS INTEGER) BETWEEN 2000 AND 2006 ORDER BY jp_sales DESC LIMIT 1";
        try (Connection c = DriverManager.getConnection(url);
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getString("name");
        }
        return "Нет данных";
    }
}