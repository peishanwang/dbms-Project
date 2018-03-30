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

import review.model.*;
import review.dal.*;

@WebServlet("/user")
public class User extends HttpServlet {

    protected UsersDao usersDao;
    
    @Override
    public void init() throws ServletException {
        usersDao = UsersDao.getInstance();
    }
    
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);
        
        // Retrieve and validate product id.
        String userName = req.getParameter("username");
        
        if (userName == null || userName.trim().isEmpty()) {
            messages.put("title", "Invalid username.");
        } else {
            messages.put("title", "User for " + userName);
        }
        
        Users user;
        try {
        	user = usersDao.getUserByUserName(userName);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IOException(e);
        }
        req.setAttribute("user", user);
        req.getRequestDispatcher("/User.jsp").forward(req, resp);
    }
}
