package review.dal;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import review.model.*;

public class RecommendationsDao {
	protected ConnectionManager connectionManager = null;
	private static RecommendationsDao instance = null;
	
	protected RecommendationsDao() {
		connectionManager = new ConnectionManager();
	}
	public static RecommendationsDao getInstance() {
		if(instance == null) {
			instance = new RecommendationsDao();
		}
		return instance;
	}
	
	public Recommendations create(Recommendations recommendation) throws SQLException {
		String insertRecommendation =
			"INSERT INTO Recommendations(UserName,productId) " +
			"VALUES(?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet resultKey = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertRecommendation,
				Statement.RETURN_GENERATED_KEYS);
			insertStmt.setString(1, recommendation.getUser().getUserName());
			insertStmt.setString(2, recommendation.getProduct().getProductId());
			insertStmt.executeUpdate();
				
				// Retrieve the auto-generated key and set it, so it can be used by the caller.
			resultKey = insertStmt.getGeneratedKeys();
			int reviewId = -1;
			if(resultKey.next()) {
				reviewId = resultKey.getInt(1);
			} else {
				throw new SQLException("Unable to retrieve auto-generated key.");
			}
			recommendation.setRecommendationId(reviewId);
			return recommendation;
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
	
	public Recommendations getRecommendationById(int recommendationId) throws SQLException {
		String selectRecommendation =
			"SELECT RecommendationId,UserName,productId " +
			"FROM Recommendations " +
			"WHERE RecommendationId=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectRecommendation);
			selectStmt.setInt(1, recommendationId);
			results = selectStmt.executeQuery();
			UsersDao usersDao = UsersDao.getInstance();
			ProductsDao productsDao = ProductsDao.getInstance();
			if(results.next()) {
				int resultRecommendationId = results.getInt("RecommendationId");
				String userName = results.getString("UserName");
				String productId = results.getString("productId");
				Users user = usersDao.getUserByUserName(userName);
				Products product = productsDao.getProductById(productId);
				Recommendations recommendation = new Recommendations(resultRecommendationId, user, product);
				return recommendation;
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
	
	public List<Recommendations> getRecommendationsByUserName(String userName) throws SQLException {
		List<Recommendations> recommendations = new ArrayList<Recommendations>();
		String selectRecommendation =
			"SELECT RecommendationId,UserName,productId " +
			"FROM Recommendations " +
			"WHERE UserName=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectRecommendation);
			selectStmt.setString(1, userName);
			results = selectStmt.executeQuery();
			UsersDao usersDao = UsersDao.getInstance();
			ProductsDao restaurantsDao = ProductsDao.getInstance();
			while(results.next()) {
				int resultRecommendationId = results.getInt("RecommendationId");
				String productId = results.getString("productId");
				
				Users user = usersDao.getUserByUserName(userName);
				Products restaurant = restaurantsDao.getProductById(productId);
				Recommendations recommendation = new Recommendations(resultRecommendationId, user, restaurant);
				recommendations.add(recommendation);
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
		return recommendations;
	}
	
	public List<Recommendations> getRecommendationsByProductId(String productId) throws SQLException {
		List<Recommendations> recommendations = new ArrayList<Recommendations>();
		String selectRecommendation =
			"SELECT RecommendationId,UserName,productId " +
			"FROM Recommendations " +
			"WHERE productId=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectRecommendation);
			selectStmt.setString(1, productId);
			results = selectStmt.executeQuery();
			UsersDao usersDao = UsersDao.getInstance();
			ProductsDao productsDao = ProductsDao.getInstance();
			while(results.next()) {
				int resultRecommendationId = results.getInt("RecommendationId");
				String userName = results.getString("UserName");			
				Users user = usersDao.getUserByUserName(userName);
				Products product = productsDao.getProductById(productId);
				Recommendations recommendation = new Recommendations(resultRecommendationId, user, product);
				recommendations.add(recommendation);
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
		return recommendations;
	}
	
	public Recommendations delete(Recommendations recommendation) throws SQLException {
		String deleteRecommendation = "DELETE FROM Recommendations WHERE RecommendationId=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteRecommendation);
			deleteStmt.setInt(1, recommendation.getRecommendationId());
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

