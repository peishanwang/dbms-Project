package review.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import review.model.*;

public class BrandsDao {
    protected ConnectionManager connectionManager;
    
    private static BrandsDao instance = null;
    protected BrandsDao() {
        connectionManager = new ConnectionManager();
    }
    public static BrandsDao getInstance() {
        if(instance == null) {
            instance = new BrandsDao();
        }
        return instance;
    }
    
    public Brands create(Brands brand) throws SQLException {
        String insertBrand = "INSERT INTO Brands(BrandName,Description) VALUES(?,?);";
        Connection connection = null;
        PreparedStatement insertStmt = null;
        try {
            connection = connectionManager.getConnection();
            insertStmt = connection.prepareStatement(insertBrand);
            insertStmt.setString(1, brand.getBrandName());
            insertStmt.setString(2, brand.getDescription());
            insertStmt.executeUpdate();
            return brand;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if(connection != null) {
                connection.close();
            }
            if(insertStmt != null) {
                insertStmt.close();
            }
        }
    }
    
    public Brands getBrandByBrandName(String brandName) throws SQLException {
        String selectBrand = "SELECT BrandName,Description FROM Brands WHERE BrandName=?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectBrand);
            selectStmt.setString(1, brandName);
            results = selectStmt.executeQuery();
            if(results.next()) {
                String resultBrandName = results.getString("BrandName");
                String description = results.getString("Description");
                Brands brand = new Brands(resultBrandName, description);
                return brand;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if(connection != null) {
                connection.close();
            }
            if(selectStmt != null) {
                selectStmt.close();
            }
            if(results != null) {
                results.close();
            }
        }
        return null;
    }
    
    public Brands updateDescription(Brands brand, String newDescription) throws SQLException {
        String updateBrands = "UPDATE Brands SET Description=? WHERE BrandName=?;";
        Connection connection = null;
        PreparedStatement updateStmt = null;
        try {
            connection = connectionManager.getConnection();
            updateStmt = connection.prepareStatement(updateBrands);
            updateStmt.setString(1, newDescription);
            updateStmt.setString(2, brand.getBrandName());
            updateStmt.executeUpdate();
            brand.setDescription(newDescription);
            return brand;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if(connection != null) {
                connection.close();
            }
            if(updateStmt != null) {
                updateStmt.close();
            }
        }
    }
    
    public Brands delete(Brands brand) throws SQLException {
        String deleteBrand = "DELETE FROM Brands WHERE BrandName=?;";
        Connection connection = null;
        PreparedStatement deleteStmt = null;
        try {
            connection = connectionManager.getConnection();
            deleteStmt = connection.prepareStatement(deleteBrand);
            deleteStmt.setString(1, brand.getBrandName());
            deleteStmt.executeUpdate();

            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if(connection != null) {
                connection.close();
            }
            if(deleteStmt != null) {
                deleteStmt.close();
            }
        }
    }
}
