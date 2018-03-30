package review.dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import review.model.Follow;
import review.model.Users;

public class FollowDao {
    protected ConnectionManager connectionManager;
    
    private static FollowDao instance = null;
    protected FollowDao() {
        connectionManager = new ConnectionManager();
    }
    public static FollowDao getInstance() {
        if(instance == null) {
            instance = new FollowDao();
        }
        return instance;
    }
    
    public Follow create(Follow follow) throws SQLException {
        String insertFollow = "INSERT INTO Follow(Follower,Followee) VALUES(?,?);";
        Connection connection = null;
        PreparedStatement insertStmt = null;
        ResultSet resultKey = null;
        try {
            connection = connectionManager.getConnection();
            insertStmt = connection.prepareStatement(insertFollow, Statement.RETURN_GENERATED_KEYS);
            insertStmt.setString(1, follow.getFollower().getUserName());
            insertStmt.setString(2, follow.getFollowee().getUserName());
            insertStmt.executeUpdate();
            resultKey = insertStmt.getGeneratedKeys();
            int followId = -1;
            if(resultKey.next()) {
                followId = resultKey.getInt(1);
            } else {
                throw new SQLException("Unable to retrieve auto-generated key.");
            }
            follow.setFollowId(followId);
            return follow;
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
    
    public Follow getFollowById(int followId) throws SQLException {
        String selectReview =
            "SELECT FollowId,Follower,Followee " +
            "FROM Follow " +
            "WHERE FollowId=?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectReview);
            selectStmt.setInt(1, followId);
            results = selectStmt.executeQuery();
            UsersDao usersDao = UsersDao.getInstance();
            if(results.next()) {
                int resultFollowId = results.getInt("FollowId");
                String followerName = results.getString("Follower");
                String followeeName = results.getString("Followee");
                
                Users follower = usersDao.getUserByUserName(followerName);
                Users followee = usersDao.getUserByUserName(followeeName);
                Follow follow = new Follow(resultFollowId, follower, followee);
                return follow;
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
    
    public List<Follow> getFollowByFollowerName(String followerName) throws SQLException {
        List<Follow> follows = new ArrayList<>();
        String selectFollow =
            "SELECT FollowId,Follower,Followee " +
            "FROM Follow " +
            "WHERE Follower=?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectFollow);
            selectStmt.setString(1, followerName);
            results = selectStmt.executeQuery();
            UsersDao usersDao = UsersDao.getInstance();
            Users follower = usersDao.getUserByUserName(followerName);
            while(results.next()) {
                int resultFollowId = results.getInt("FollowId");
                String followeeName = results.getString("Followee");
                
                Users followee = usersDao.getUserByUserName(followeeName);
                Follow follow = new Follow(resultFollowId, follower, followee);
                follows.add(follow);
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
        return follows;
    }
    
    public List<Follow> getFollowByFolloweeName(String followeeName) throws SQLException {
        List<Follow> follows = new ArrayList<>();
        String selectFollow =
            "SELECT FollowId,Follower,Followee " +
            "FROM Follow " +
            "WHERE Followee=?;";
        Connection connection = null;
        PreparedStatement selectStmt = null;
        ResultSet results = null;
        try {
            connection = connectionManager.getConnection();
            selectStmt = connection.prepareStatement(selectFollow);
            selectStmt.setString(1, followeeName);
            results = selectStmt.executeQuery();
            UsersDao usersDao = UsersDao.getInstance();
            Users followee = usersDao.getUserByUserName(followeeName);
            while(results.next()) {
                int resultFollowId = results.getInt("FollowId");
                String followerName = results.getString("Follower");
                
                Users follower = usersDao.getUserByUserName(followerName);
                Follow follow = new Follow(resultFollowId, follower, followee);
                follows.add(follow);
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
        return follows;
    }
    
    public Follow delete(Follow follow) throws SQLException {
        String deleteFollow = "DELETE FROM Follow WHERE FollowId=?;";
        Connection connection = null;
        PreparedStatement deleteStmt = null;
        try {
            connection = connectionManager.getConnection();
            deleteStmt = connection.prepareStatement(deleteFollow);
            deleteStmt.setInt(1, follow.getFollowId());
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
