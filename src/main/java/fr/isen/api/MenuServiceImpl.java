package fr.isen.api;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MenuServiceImpl implements MenuService {
    private static final String URL = "jdbc:mysql://localhost:3306/borne_asiat";
    private static final String USER = "root";
    private static final String PASSWORD = ""; // XAMPP = souvent vide

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    @Override
    public List<MenuItem> getAllMenuItems(boolean fr) {
        if (!fr) {
            String sqlEng = "SELECT id, nameEng AS name, descriptionEng AS description, price, category, image_url, calories,"
                    +
                    " is_available, is_spicy, is_vegetarian, protein_required" +
                    " FROM menu_items" +
                    " ORDER BY id";
            return queryList(sqlEng);
        } else {
            String sql = "SELECT id, name, description, price, category, image_url, calories," +
                    " is_available, is_spicy, is_vegetarian, protein_required" +
                    " FROM menu_items" +
                    " ORDER BY id";
            return queryList(sql);
        }
    }

    @Override
    public List<MenuItem> getMenuItemsByCategory(String category, boolean fr) {
        String sql;
        if (!fr) {
            sql = "SELECT id, nameEng AS name, descriptionEng AS description, price, category, image_url, calories," +
                    " is_available, is_spicy, is_vegetarian, protein_required" +
                    " FROM menu_items" +
                    " WHERE category = ?" +
                    " ORDER BY id";

        } else {
            sql = "SELECT id, name, description, price, category, image_url, calories," +
                    " is_available, is_spicy, is_vegetarian, protein_required" +
                    " FROM menu_items" +
                    " WHERE category = ?" +
                    " ORDER BY id";
        }

        List<MenuItem> items = new ArrayList<>();

        if (category == "plats")
            category = "Plats principaux";
        else if (category == "snacks")
            category = "Snacks";
        else if (category == "desserts")
            category = "Desserts";
        else if (category == "boissons")
            category = "Boissons";
        try (Connection conn = getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, category);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    items.add(mapRow(rs));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erreur SQL getMenuItemsByCategory()", e);
        }

        return items;
    }

    @Override
    public MenuItem getMenuItemById(int id, boolean fr) {
        String sql;
        if (fr) {
            sql = "SELECT id, name, description,category, price, category, image_url, calories," +
                    " is_available, is_spicy, is_vegetarian, protein_required" +
                    " FROM menu_items" +
                    " WHERE id = ?";
        } else {
            sql = "SELECT id, nameEng AS name, descriptionEng AS description,category, price, category, image_url, calories,"
                    +
                    " is_available, is_spicy, is_vegetarian, protein_required" +
                    " FROM menu_items" +
                    " WHERE id = ?";
        }
        try (Connection conn = getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next())
                    return null;
                return mapRow(rs);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erreur SQL getMenuItemById()", e);
        }
    }

    @Override
    public List<MenuItem> getAvailableMenuItems() {
        String sql = "SELECT id, name, description,category, price, image_url, calories," +
                " is_available, is_spicy, is_vegetarian, protein_required" +
                " FROM menu_items" +
                " WHERE is_available = 1" +
                " ORDER BY id";
        return queryList(sql);
    }

    private List<MenuItem> queryList(String sql) {
        List<MenuItem> items = new ArrayList<>();

        try (Connection conn = getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                items.add(mapRow(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erreur SQL queryList()", e);
        }

        return items;
    }

    private MenuItem mapRow(ResultSet rs) throws SQLException {
        return new MenuItem(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("description"),
                rs.getString("category"),
                rs.getDouble("price"),
                rs.getString("image_url"),
                rs.getInt("calories"),
                rs.getInt("is_available") == 1,
                rs.getInt("is_spicy") == 1,
                rs.getInt("is_vegetarian") == 1,
                rs.getInt("protein_required") == 1);
    }

    @Override
    public void updateMenuItemBasic(int id, String name, String description, double price, boolean available) {

        String sql = "UPDATE menu_items " +
                "SET name = ?, description = ?, price = ?, is_available = ? " +
                "WHERE id = ?";

        try (Connection conn = getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setString(2, description);
            ps.setDouble(3, price);
            ps.setBoolean(4, available);
            ps.setInt(5, id);
            int updated = ps.executeUpdate();
            if (updated == 0) {
                throw new RuntimeException("Aucun item trouvé avec id=" + id);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erreur SQL updateMenuItemBasic()", e);
        }
    }
}