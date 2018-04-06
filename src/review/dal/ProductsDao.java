package review.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import review.model.*;


public class ProductsDao {
	protected ConnectionManager connectionManager;

	private static ProductsDao instance = null;
	protected ProductsDao() {
		connectionManager = new ConnectionManager();
	}
	public static ProductsDao getInstance() {
		if(instance == null) {
			instance = new ProductsDao();
		}
		return instance;
	}

	public Products create(Products product) throws SQLException {
		String insertProduct =
			"INSERT INTO Products(ProductId,ProductName,Description,BrandName,Price) " +
			"VALUES(?,?,?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet resultKey = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertProduct);
			insertStmt.setString(1, product.getProductId());
			insertStmt.setString(2, product.getProductName());
			insertStmt.setString(3, product.getDescription());
			insertStmt.setString(4, product.getBrand().getBrandName());
			insertStmt.setDouble(5, product.getPrice());
			insertStmt.executeUpdate();
			return product;
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
			if(resultKey != null) {
				resultKey.close();
			}
		}
	}

	public Products updateDescription(Products product, String newDescription) throws SQLException {
		String updateProduct = "UPDATE Products SET Description=? WHERE ProductId=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try {
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updateProduct);
			updateStmt.setString(1, newDescription);
			updateStmt.setString(2, product.getProductId());
			updateStmt.executeUpdate();
			product.setDescription(newDescription);
			return product;
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
	
	public Products updateProductName(Products product, String newProductName) throws SQLException {
		String updateProduct = "UPDATE Products SET ProductName=? WHERE ProductId=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try {
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updateProduct);
			updateStmt.setString(1, newProductName);
			updateStmt.setString(2, product.getProductId());
			updateStmt.executeUpdate();
			product.setProductName(newProductName);
			return product;
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
	
	public Products updatePrice(Products product, Double newPrice) throws SQLException {
		String updateProduct = "UPDATE Products SET Price=? WHERE ProductId=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try {
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updateProduct);
			updateStmt.setDouble(1, newPrice);
			updateStmt.setString(2, product.getProductId());
			updateStmt.executeUpdate();
			product.setPrice(newPrice);
			return product;
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


	public Products delete(Products product) throws SQLException {
		String deleteProduct = "DELETE FROM Products WHERE ProductId=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteProduct);
			deleteStmt.setString(1, product.getProductId());
			deleteStmt.executeUpdate();

			// Return null so the caller can no longer operate on the Products instance.
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

	public Products getProductById(String productId) throws SQLException {
		String selectBlogPost =
			"SELECT ProductId,ProductName,Description,BrandName,Price " +
			"FROM Products " +
			"WHERE ProductId=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectBlogPost);
			selectStmt.setString(1, productId);
			results = selectStmt.executeQuery();
			BrandsDao brandsDao = BrandsDao.getInstance();
			if(results.next()) {
				String productName = results.getString("ProductName");
				String description = results.getString("Description");
				String brandName = results.getString("BrandName");
				Double price = results.getDouble("Price");
				
				Brands brand = brandsDao.getBrandByBrandName(brandName);
				Products product = new Products(productId, productName, description, brand, price);
				return product;
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
	
	public List<Products> getProductByProductName(String productName) throws SQLException {
		List<Products> products = new ArrayList<>();
		String selectBlogPost =
			"SELECT Products.ProductId,ProductName,Description,BrandName,Price,AVG(Rating) AS AVG_Rating "
			+ "FROM Products INNER JOIN Reviews "
			+ "WHERE Products.ProductId=Reviews.ProductId "
			+ "GROUP BY Products.ProductId "
			+ "HAVING Products.ProductName LIKE ? "
			+ "ORDER BY AVG_Rating DESC;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectBlogPost);
			selectStmt.setString(1, '%' + productName + '%');
			results = selectStmt.executeQuery();
			BrandsDao brandsDao = BrandsDao.getInstance();
			while(results.next()) {
				String productId = results.getString("Products.ProductId");
				String originProductName = results.getString("ProductName");
				String description = results.getString("Description");
				String brandName = results.getString("BrandName");
				Double price = results.getDouble("Price");
				Double averageRating = results.getDouble("AVG_Rating");
				
				Brands brand = brandsDao.getBrandByBrandName(brandName);
				Products product = new Products(productId, originProductName, description, brand, price, averageRating);
				products.add(product);
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
		return products;
	}
}
