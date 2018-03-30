package review.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import review.model.*;

public class QuestionsDao {
	protected ConnectionManager connectionManager = null;
	private static QuestionsDao instance = null;
	
	protected QuestionsDao() {
		connectionManager = new ConnectionManager();
	}
	public static QuestionsDao getInstance() {
		if(instance == null) {
			instance = new QuestionsDao();
		}
		return instance;
	}
	
	public Questions create(Questions question) throws SQLException {
		String insertRecommendation =
			"INSERT INTO Questions(Content,UserName,productId) " +
			"VALUES(?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet resultKey = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertRecommendation,
				Statement.RETURN_GENERATED_KEYS);
			insertStmt.setString(1, question.getContent());
			insertStmt.setString(2, question.getUser().getUserName());
			insertStmt.setString(3, question.getProduct().getProductId());
			insertStmt.executeUpdate();
				
				// Retrieve the auto-generated key and set it, so it can be used by the caller.
			resultKey = insertStmt.getGeneratedKeys();
			int questionId = -1;
			if(resultKey.next()) {
				questionId = resultKey.getInt(1);
			} else {
				throw new SQLException("Unable to retrieve auto-generated key.");
			}
			question.setQuestionId(questionId);
			return question;
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
	
	public Questions getQuestionById(int questionId) throws SQLException {
		String selectQuestion =
			"SELECT QuestionId,Content,Created,UserName,ProductId " +
			"FROM Questions " +
			"WHERE QuestionId=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectQuestion);
			selectStmt.setInt(1, questionId);
			results = selectStmt.executeQuery();
			UsersDao usersDao = UsersDao.getInstance();
			ProductsDao productsDao = ProductsDao.getInstance();
			if(results.next()) {
				String content = results.getString("Content");
				Date created = results.getDate("Created");
				String userName = results.getString("UserName");
				String productId = results.getString("ProductId");			
				Users user = usersDao.getUserByUserName(userName);
				Products product = productsDao.getProductById(productId);
				Questions question = new Questions(questionId, content, created, product, user);
				return question;
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
	
	
	public List<Questions> getQuestionsByUserName(String userName) throws SQLException {
		List<Questions> questions = new ArrayList<Questions>();
		String selectQuestions =
			"SELECT QuestionId,Content,Created,UserName,ProductId " +
			"FROM Questions " +
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
				int questionId = results.getInt("QuestionId");
				String content = results.getString("Content");
				Date created = results.getDate("Created");
				String productId = results.getString("ProductId");			
				Users user = usersDao.getUserByUserName(userName);
				Products product = productsDao.getProductById(productId);
				Questions question = new Questions(questionId, content, created, product, user);
				questions.add(question);
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
		return questions;
	}
	
	public List<Questions> getQuestionsByProductId(String productId) throws SQLException {
		List<Questions> questions = new ArrayList<Questions>();
		String selectQuestions =
			"SELECT QuestionId,Content,Created,UserName,ProductId " +
			"FROM Questions " +
			"WHERE ProductId=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectQuestions);
			selectStmt.setString(1, productId);
			results = selectStmt.executeQuery();
			UsersDao usersDao = UsersDao.getInstance();
			ProductsDao productsDao = ProductsDao.getInstance();
			while(results.next()) {
				int questionId = results.getInt("QuestionId");
				String content = results.getString("Content");
				Date created = results.getDate("Created");
                String userName = results.getString("UserName");
				Users user = usersDao.getUserByUserName(userName);
				Products product = productsDao.getProductById(productId);
				Questions question = new Questions(questionId, content, created, product, user);
				questions.add(question);
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
		return questions;
	}
	
	public Questions delete(Questions question) throws SQLException {
		String deleteQuestions = "DELETE FROM Questions WHERE questionId=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteQuestions);
			deleteStmt.setInt(1, question.getQuestionId());
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

