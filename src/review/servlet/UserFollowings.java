package review.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import review.dal.*;
import review.model.*;

@WebServlet("/followings")
public class UserFollowings extends HttpServlet {
    
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
        
        // Retrieve and validate UserName.
        String username = req.getParameter("username");
        if (username == null || username.trim().isEmpty()) {
            messages.put("title", "Invalid username.");
        } else {
            messages.put("title", "Followings for " + username);
        }
        
        // Retrieve Follows, and store in the request.
        List<Follow> follows = new ArrayList<>();
        try {
            follows = followDao.getFollowByFollowerName(username);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IOException(e);
        }
        req.setAttribute("follows", follows);
        req.getRequestDispatcher("/UserFollowings.jsp").forward(req, resp);
    }
}
