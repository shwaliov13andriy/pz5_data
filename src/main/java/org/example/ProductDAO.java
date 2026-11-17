package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {


    public void addProduct(Product product) {
        String sql = "INSERT INTO Products (ID, Name, Price, Quantity, Category) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, product.getId());
            pstmt.setString(2, product.getName());
            pstmt.setDouble(3, product.getPrice());
            pstmt.setInt(4, product.getQuantity());
            pstmt.setString(5, product.getCategory());
            pstmt.executeUpdate();
            System.out.println("Товар успішно додано!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM Products";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                products.add(new Product(
                        rs.getInt("ID"),
                        rs.getString("Name"),
                        rs.getDouble("Price"),
                        rs.getInt("Quantity"),
                        rs.getString("Category")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    public void updateProductPrice(int id, double newPrice) {
        String sql = "UPDATE Products SET Price = ? WHERE ID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1, newPrice);
            pstmt.setInt(2, id);
            pstmt.executeUpdate();
            System.out.println("Ціну товару оновлено!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteProduct(int id) {
        String sql = "DELETE FROM Products WHERE ID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            System.out.println("Товар видалено!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void getDeliveriesWithProducts() {
        String sql = "SELECT d.Date, p.Name, d.Weight FROM Deliveries d JOIN Products p ON d.ProductID = p.ID";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            System.out.println("\n--- Звіт: Поставки та товари (JOIN) ---");
            while (rs.next()) {
                System.out.println("Дата: " + rs.getDate("Date") +
                        " | Товар: " + rs.getString("Name") +
                        " | Вага: " + rs.getDouble("Weight"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void getProductsFilteredByPrice(double minPrice) {
        String sql = "SELECT * FROM Products WHERE Price > ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1, minPrice);
            ResultSet rs = pstmt.executeQuery();
            System.out.println("\n--- Фільтрація: Товари дорожчі за " + minPrice + " ---");
            while (rs.next()) {
                System.out.println(rs.getString("Name") + " - " + rs.getDouble("Price") + " грн");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void getAggregateProductStats() {
        String sql = "SELECT Category, SUM(Quantity) AS TotalQty, AVG(Price) AS AvgPrice FROM Products GROUP BY Category";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            System.out.println("\n--- Агрегатні функції (GROUP BY, SUM, AVG) ---");
            while (rs.next()) {
                System.out.println("Категорія: " + rs.getString("Category") +
                        " | Загальна кількість: " + rs.getInt("TotalQty") +
                        " | Середня ціна: " + rs.getDouble("AvgPrice"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}