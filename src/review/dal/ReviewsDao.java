package review.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import review.model.*;



public class ReviewsDao {
	protected ConnectionManager connectionManager;
	private static ReviewsDao instance = null;
	protected ReviewsDao() {
		connectionManager = new ConnectionManager();
	}
	public static ReviewsDao getInstance() {
		if(instance == null) {
			instance = new ReviewsDao();
		}
		return instance;
	}
	
	public Reviews create(Reviews review) throws SQLException {
		String insertReview =
			"INSERT INTO Reviews(UserName,ProductId,Content,Summary,Rating) " +
			"VALUES(?,?,?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet resultKey = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertReview,
				Statement.RETURN_GENERATED_KEYS);
			insertStmt.setString(1, review.getUser().getUserName());
			insertStmt.setString(2, review.getProduct().getProductId());
			insertStmt.setString(3, review.getContent());
			insertStmt.setString(4, review.getSummary());
			insertStmt.setDouble(5, review.getRating());
			insertStmt.executeUpdate();
			resultKey = insertStmt.getGeneratedKeys();
			int reviewId = -1;
			if(resultKey.next()) {
				reviewId = resultKey.getInt(1);
			} else {
				throw new SQLException("Unable to retrieve auto-generated key.");
			}
			review.setReviewId(reviewId);
			return review;
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
	
	public Reviews updateContent(Reviews review, String newContent) throws SQLException {
		String updateReivew = "UPDATE Reviews SET Content=?,Created=? WHERE ReviewId=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try {
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updateReivew);
			updateStmt.setString(1, newContent);
			// Sets the Created timestamp to the current time.
			Date newCreatedTimestamp = new Date();
			updateStmt.setTimestamp(2, new Timestamp(newCreatedTimestamp.getTime()));
			updateStmt.setInt(3, review.getReviewId());
			updateStmt.executeUpdate();

			// Update the blogPost param before returning to the caller.
			review.setContent(newContent);
			review.setCreated(newCreatedTimestamp);
			return review;
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

	public Reviews updateSummary(Reviews review, String newSummary) throws SQLException {
		String updateReivew = "UPDATE Reviews SET Summary=?,Created=? WHERE ReviewId=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try {
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updateReivew);
			updateStmt.setString(1, newSummary);
			// Sets the Created timestamp to the current time.
			Date newCreatedTimestamp = new Date();
			updateStmt.setTimestamp(2, new Timestamp(newCreatedTimestamp.getTime()));
			updateStmt.setInt(3, review.getReviewId());
			updateStmt.executeUpdate();

			// Update the blogPost param before returning to the caller.
			review.setSummary(newSummary);
			review.setCreated(newCreatedTimestamp);
			return review;
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
	
	/**
	 * Delete the Reviews instance.
	 * This runs a DELETE statement.
	 */
	public Reviews delete(Reviews review) throws SQLException {

		String deleteReview = "DELETE FROM Reviews WHERE ReviewId=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteReview);
			deleteStmt.setInt(1, review.getReviewId());
			deleteStmt.executeUpdate();

			// Return null so the caller can no longer operate on the BlogPosts instance.
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

	public Reviews getReviewById(int reviewId) throws SQLException {
		String selectReview =
			"SELECT ReviewId,UserName,ProductId,Created,Content,Summary,Rating " +
			"FROM Reviews " +
			"WHERE ReviewId=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectReview);
			selectStmt.setInt(1, reviewId);
			results = selectStmt.executeQuery();
			UsersDao usersDao = UsersDao.getInstance();
			ProductsDao productsDao = ProductsDao.getInstance();
			if(results.next()) {
				int resultReviewId = results.getInt("ReviewId");
				String userName = results.getString("UserName");
				String productId = results.getString("ProductId");
				Date created =  new Date(results.getTimestamp("Created").getTime());
				String content = results.getString("Content");
				String summary = results.getString("Summary");
				Double rating = results.getDouble("Rating");
				
				Users user = usersDao.getUserByUserName(userName);
				Products product = productsDao.getProductById(productId);
				Reviews review = new Reviews(resultReviewId, user, product, created, content, summary, rating);
				return review;
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

	public List<Reviews> getReviewForUser(Users user) throws SQLException {
		List<Reviews> reviews = new ArrayList<>();
		String selectReview =
			"SELECT ReviewId,UserName,ProductId,Created,Content,Summary,Rating " +
			"FROM Reviews " +
			"WHERE UserName=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectReview);
			selectStmt.setString(1, user.getUserName());
			results = selectStmt.executeQuery();
			ProductsDao productsDao = ProductsDao.getInstance();
			while(results.next()) {
				int resultReviewId = results.getInt("ReviewId");
				String productId = results.getString("ProductId");
				Date created =  new Date(results.getTimestamp("Created").getTime());
				String content = results.getString("Content");
				String summary = results.getString("Summary");
				Double rating = results.getDouble("Rating");
				Products product = productsDao.getProductById(productId);
				Reviews review = new Reviews(resultReviewId, user, product, created, content, summary, rating);
				reviews.add(review);
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
		return reviews;
	}
	
	public List<Reviews> getReviewForProduct(Products product) throws SQLException {
		List<Reviews> reviews = new ArrayList<>();
		String selectReview =
			"SELECT ReviewId,UserName,ProductId,Created,Content,Summary,Rating " +
			"FROM Reviews " +
			"WHERE ProductId=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectReview);
			selectStmt.setString(1, product.getProductId());
			results = selectStmt.executeQuery();
			UsersDao usersDao = UsersDao.getInstance();
			while(results.next()) {
				int resultReviewId = results.getInt("ReviewId");
				String userName = results.getString("UserName");
				Date created =  new Date(results.getTimestamp("Created").getTime());
				String content = results.getString("Content");
				String summary = results.getString("Summary");
				Double rating = results.getDouble("Rating");
				Users user = usersDao.getUserByUserName(userName);
				Reviews review = new Reviews(resultReviewId, user, product, created, content, summary, rating);
				reviews.add(review);
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
		return reviews;
	}
}
