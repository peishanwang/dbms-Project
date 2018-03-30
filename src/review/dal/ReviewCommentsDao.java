package review.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import review.model.ReviewComments;
import review.model.Reviews;
import review.model.Users;

public class ReviewCommentsDao {
	protected ConnectionManager connectionManager;
	private static ReviewCommentsDao instance = null;
	protected ReviewCommentsDao() {
		connectionManager = new ConnectionManager();
	}
	public static ReviewCommentsDao getInstance() {
		if(instance == null) {
			instance = new ReviewCommentsDao();
		}
		return instance;
	}
	
	public ReviewComments create(ReviewComments reviewComment) throws SQLException {
		String insertReviewComment =
			"INSERT INTO ReviewComments(UserName,ReviewId,Helpful) " +
			"VALUES(?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet resultKey = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertReviewComment,
				Statement.RETURN_GENERATED_KEYS);
			insertStmt.setString(1, reviewComment.getUser().getUserName());
			insertStmt.setInt(2, reviewComment.getReview().getReviewId());
			insertStmt.setBoolean(3, reviewComment.isHelpful());
			insertStmt.executeUpdate();
			resultKey = insertStmt.getGeneratedKeys();
			int reviewCommentId = -1;
			if(resultKey.next()) {
				reviewCommentId = resultKey.getInt(1);
			} else {
				throw new SQLException("Unable to retrieve auto-generated key.");
			}
			reviewComment.setCommentId(reviewCommentId);
			return reviewComment;
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
	
	public ReviewComments delete(ReviewComments reviewComment) throws SQLException {

		String deleteComment = "DELETE FROM ReviewComments WHERE CommentId=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteComment);
			deleteStmt.setInt(1, reviewComment.getCommentId());
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

	public ReviewComments getCommentById(int commentId) throws SQLException {
		String selectComment =
			"SELECT CommentId,UserName,ReviewId,Helpful " +
			"FROM ReviewComments " +
			"WHERE CommentId=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectComment);
			selectStmt.setInt(1, commentId);
			results = selectStmt.executeQuery();
			UsersDao usersDao = UsersDao.getInstance();
			ReviewsDao reviewsDao = ReviewsDao.getInstance();
			if(results.next()) {
				String userName = results.getString("UserName");
				int reviewId = results.getInt("ReviewId");
				boolean helpful = results.getBoolean("Helpful");
				Users user = usersDao.getUserByUserName(userName);
				Reviews review = reviewsDao.getReviewById(reviewId);
				ReviewComments reviewComment = new ReviewComments(commentId, user, review, helpful);
				return reviewComment;
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

	public List<ReviewComments> getReviewCommentsForReview(Reviews review) throws SQLException {
		List<ReviewComments> reviewComments = new ArrayList<>();
		String selectComment =
			"SELECT CommentId,UserName,ReviewId,Helpful " +
			"FROM ReviewComments " +
			"WHERE ReviewId=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectComment);
			selectStmt.setInt(1, review.getReviewId());
			results = selectStmt.executeQuery();
			UsersDao usersDao = UsersDao.getInstance();
			while(results.next()) {
				int commentId = results.getInt("CommentId");
				String userName = results.getString("UserName");
				boolean helpful = results.getBoolean("Helpful");
				Users user = usersDao.getUserByUserName(userName);
				ReviewComments reviewComment = new ReviewComments(commentId, user, review, helpful);
				reviewComments.add(reviewComment);
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
		return reviewComments;
	}
}
