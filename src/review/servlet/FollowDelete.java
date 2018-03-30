package review.servlet;

import review.dal.FollowDao;
import review.model.Follow;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.annotation.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/followdelete")
public class FollowDelete extends HttpServlet {
    
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
        // Provide a title and render the JSP.
        messages.put("title", "Delete Follow");        
        req.getRequestDispatcher("/FollowDelete.jsp").forward(req, resp);
    }
    
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        // Retrieve and validate name.
        String followId = req.getParameter("followid");
        if (followId == null || followId.trim().isEmpty()) {
            messages.put("title", "Invalid FollowId");
            messages.put("disableSubmit", "true");
        } else {
            // Delete the Follow.
            Follow follow = new Follow(Integer.parseInt(followId));
            try {
                follow = followDao.delete(follow);
                // Update the message.
                if (follow == null) {
                    messages.put("title", "Successfully deleted " + followId);
                    messages.put("disableSubmit", "true");
                } else {
                    messages.put("title", "Failed to delete " + followId);
                    messages.put("disableSubmit", "false");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                throw new IOException(e);
            }
        }
        
        req.getRequestDispatcher("/FollowDelete.jsp").forward(req, resp);
    }
}
