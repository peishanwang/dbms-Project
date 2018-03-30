package review.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import review.dal.*;
import review.model.*;

public class AnswersDao {
	protected ConnectionManager connectionManager = null;
	private static AnswersDao instance = null;
	
	protected AnswersDao() {
		connectionManager = new ConnectionManager();
	}
	public static AnswersDao getInstance() {
		if(instance == null) {
			instance = new AnswersDao();
		}
		return instance;
	}
	
	public Answers create(Answers answer) throws SQLException {
		String insertRecommendation =
			"INSERT INTO Answers(Content,UserName,QuestionId) " +
			"VALUES(?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet resultKey = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertRecommendation,
				Statement.RETURN_GENERATED_KEYS);
			insertStmt.setString(1, answer.getContent());
			insertStmt.setString(2, answer.getUser().getUserName());
			insertStmt.setInt(3, answer.getQuestion().getQuestionId());
			insertStmt.executeUpdate();
				
				// Retrieve the auto-generated key and set it, so it can be used by the caller.
			resultKey = insertStmt.getGeneratedKeys();
			int answerId = -1;
			if(resultKey.next()) {
				answerId = resultKey.getInt(1);
			} else {
				throw new SQLException("Unable to retrieve auto-generated key.");
			}
			answer.setAnswerId(answerId);
			return answer;
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
	
	public Answers getAnswerById(int answerId) throws SQLException {
		String selectQuestion =
			"SELECT AnswerId,Content,Created,UserName,QuestionId " +
			"FROM Answers " +
			"WHERE answerId=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectQuestion);
			selectStmt.setInt(1, answerId);
			results = selectStmt.executeQuery();
			UsersDao usersDao = UsersDao.getInstance();
			QuestionsDao questionDao = QuestionsDao.getInstance();
			if(results.next()) {
				String content = results.getString("Content");
				Date created = results.getDate("Created");
				String userName = results.getString("UserName");
				int questionId = results.getInt("QuestionId");			
				Users user = usersDao.getUserByUserName(userName);
				Questions question = questionDao.getQuestionById(questionId);
				Answers answer = new Answers(answerId, content, created, question, user);
				return answer;
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
	
	
//	public List<Questions> getQuestionsByUserName(String userName) throws SQLException {
//		List<Questions> questions = new ArrayList<Questions>();
//		String selectQuestions =
//			"SELECT QuestionId,Content,Created,UserName,ProductId " +
//			"FROM Questions " +
//			"WHERE UserName=?;";
//		Connection connection = null;
//		PreparedStatement selectStmt = null;
//		ResultSet results = null;
//		try {
//			connection = connectionManager.getConnection();
//			selectStmt = connection.prepareStatement(selectQuestions);
//			selectStmt.setString(1, userName);
//			results = selectStmt.executeQuery();
//			UsersDao usersDao = UsersDao.getInstance();
//			ProductsDao productsDao = ProductsDao.getInstance();
//			while(results.next()) {
//				int questionId = results.getInt("QuestionId");
//				String content = results.getString("Content");
//				Date created = results.getDate("Created");
//				String productId = results.getString("ProductId");			
//				Users user = usersDao.getUserByUserName(userName);
//				Products product = productsDao.getProductById(productId);
//				Questions question = new Questions(questionId, content, created, product, user);
//				questions.add(question);
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//			throw e;
//		} finally {
//			if(connection != null) {
//				connection.close();
//			}
//			if(selectStmt != null) {
//				selectStmt.close();
//			}
//			if(results != null) {
//				results.close();
//			}
//		}
//		return questions;
//	}
//	
	public List<Answers> getAnswersByQuestionId(int questionId) throws SQLException {
	List<Answers> answers = new ArrayList<Answers>();
	String selectQuestions =
		"SELECT AnswerId, QuestionId,Content,Created,UserName " +
		"FROM Answers " +
		"WHERE QuestionId=?;";
	Connection connection = null;
	PreparedStatement selectStmt = null;
	ResultSet results = null;
	try {
		connection = connectionManager.getConnection();
		selectStmt = connection.prepareStatement(selectQuestions);
		selectStmt.setInt(1, questionId);
		results = selectStmt.executeQuery();
		UsersDao usersDao = UsersDao.getInstance();
		QuestionsDao questionDao = QuestionsDao.getInstance();
		while(results.next()) {
			int answerId = results.getInt("AnswerId");
			String content = results.getString("Content");
			Date created = results.getDate("Created");
            String userName = results.getString("UserName");
			Users user = usersDao.getUserByUserName(userName);
			Questions question = questionDao.getQuestionById(questionId);
			Answers answer = new Answers(answerId, content, created, question, user);
			answers.add(answer);
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
	return answers;
}
//	
	public Answers delete(Answers answer) throws SQLException {
		String deleteQuestions = "DELETE FROM Answers WHERE answerId=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteQuestions);
			deleteStmt.setInt(1, answer.getAnswerId());
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