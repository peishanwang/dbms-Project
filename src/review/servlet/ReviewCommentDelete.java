package review.servlet;

import review.dal.ReviewCommentsDao;
import review.model.ReviewComments;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.annotation.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/reviewcommentdelete")
public class ReviewCommentDelete extends HttpServlet {
    
    protected ReviewCommentsDao reviewCommentsDao;
    
    @Override
    public void init() throws ServletException {
        reviewCommentsDao = ReviewCommentsDao.getInstance();
    }
    
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);
        // Provide a title and render the JSP.
        messages.put("title", "Delete ReviewComment");        
        req.getRequestDispatcher("/ReviewCommentDelete.jsp").forward(req, resp);
    }
    
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        // Retrieve and validate name.
        String commentId = req.getParameter("commentid");
        if (commentId == null || commentId.trim().isEmpty()) {
            messages.put("title", "Invalid ReviewCommentId");
            messages.put("disableSubmit", "true");
        } else {
            // Delete the BlogUser.
            ReviewComments comment = new ReviewComments(Integer.parseInt(commentId));
            try {
                comment = reviewCommentsDao.delete(comment);
                // Update the message.
                if (comment == null) {
                    messages.put("title", "Successfully deleted " + commentId);
                    messages.put("disableSubmit", "true");
                } else {
                    messages.put("title", "Failed to delete " + commentId);
                    messages.put("disableSubmit", "false");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                throw new IOException(e);
            }
        }
        
        req.getRequestDispatcher("/ReviewCommentDelete.jsp").forward(req, resp);
    }
}
