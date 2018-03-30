package review.dal;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import review.model.*;

public class FavoritesDao {
	protected ConnectionManager connectionManager = null;
	private static FavoritesDao instance = null;
	
	protected FavoritesDao() {
		connectionManager = new ConnectionManager();
	}
	public static FavoritesDao getInstance() {
		if(instance == null) {
			instance = new FavoritesDao();
		}
		return instance;
	}
	
	public Favorites create(Favorites favorite) throws SQLException {
		String insertFavorites =
			"INSERT INTO Favorites(UserName,productId) " +
			"VALUES(?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet resultKey = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertFavorites,
				Statement.RETURN_GENERATED_KEYS);
			insertStmt.setString(1, favorite.getUser().getUserName());
			insertStmt.setString(2, favorite.getProduct().getProductId());
			insertStmt.executeUpdate();
				
				// Retrieve the auto-generated key and set it, so it can be used by the caller.
			resultKey = insertStmt.getGeneratedKeys();
			int favoriteId = -1;
			if(resultKey.next()) {
				favoriteId = resultKey.getInt(1);
			} else {
				throw new SQLException("Unable to retrieve auto-generated key.");
			}
			favorite.setFavoriteId(favoriteId);
			return favorite;
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
	
	public Favorites getFavoriteById(int favoriteId) throws SQLException {
		String selectQuestion =
			"SELECT favoriteId,UserName,ProductId " +
			"FROM Favorites " +
			"WHERE FavoriteId=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectQuestion);
			selectStmt.setInt(1, favoriteId);
			results = selectStmt.executeQuery();
			UsersDao usersDao = UsersDao.getInstance();
			ProductsDao productsDao = ProductsDao.getInstance();
			if(results.next()) {
				String userName = results.getString("UserName");
				String productId = results.getString("ProductId");			
				Users user = usersDao.getUserByUserName(userName);
				Products product = productsDao.getProductById(productId);
				Favorites favorite = new Favorites(favoriteId, product, user);
				return favorite;
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
	
	public List<Favorites> getFavoritesByUserName(String userName) throws SQLException {
		List<Favorites> favorites = new ArrayList<>();
		String selectQuestions =
			"SELECT FavoriteId,UserName,ProductId " +
			"FROM Favorites " +
			"WHERE UserName=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectQuestions);
			selectStmt.setString(1, userName);
			results = selectStmt.executeQuery();
			UsersDao usersDao = UsersDao.getInstance();
			ProductsDao productsDao = ProductsDao.getInstance();
			while(results.next()) {
				int favoriteId = results.getInt("FavoriteId");
				String productId = results.getString("ProductId");			
				Users user = usersDao.getUserByUserName(userName);
				Products product = productsDao.getProductById(productId);
				Favorites favorite = new Favorites(favoriteId, product, user);
				favorites.add(favorite);
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
		return favorites;
	}
	

	
	public Favorites delete(Favorites favorite) throws SQLException {
		String deleteFavorites = "DELETE FROM Favorites WHERE favoriteId=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteFavorites);
			deleteStmt.setInt(1, favorite.getFavoriteId());
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

