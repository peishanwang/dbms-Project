package review.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import review.dal.*;
import review.model.*;

@WebServlet("/followcreate")
public class FollowCreate extends HttpServlet {

    protected FollowDao followDao;
    
    @Override
    public void init() throws ServletException {
        followDao = FollowDao.getInstance();
    }
    
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);
        //Just render the JSP.   
        req.getRequestDispatcher("/FollowCreate.jsp").forward(req, resp);
    }
    
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        // Retrieve and validate name.
        String followerName = req.getParameter("follower");
        String followeeName = req.getParameter("followee");
        if (followerName == null || followerName.trim().isEmpty() || followeeName == null || followeeName.trim().isEmpty()) {
            messages.put("success", "Invalid Follower or Followee");
        } else {
            // Create the Follow.
            UsersDao usersDao = UsersDao.getInstance();
            try {
                Users follower = usersDao.getUserByUserName(followerName);
                Users followee = usersDao.getUserByUserName(followeeName);
                // Exercise: parse the input for StatusLevel.
                Follow follow = null;
                if (follower == null || followee == null) {
                    messages.put("success", "Follower or Followee does not exist");
                } else {
                    follow = new Follow(follower, followee);
                    follow = followDao.create(follow);
                    messages.put("success", "Successfully created FollowId " + follow.getFollowId());
                }
            } catch (SQLException e) {
                e.printStackTrace();
                throw new IOException(e);
            }
        }
        
        req.getRequestDispatcher("/FollowCreate.jsp").forward(req, resp);
    }
}
