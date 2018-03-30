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

@WebServlet("/reviewcommentcreate")
public class ReviewCommentCreate extends HttpServlet {

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
        //Just render the JSP.   
        req.getRequestDispatcher("/ReviewCommentCreate.jsp").forward(req, resp);
    }
    
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        // Retrieve and validate name.
        String reviewId = req.getParameter("reviewid");
        if (reviewId == null || reviewId.trim().isEmpty()) {
            messages.put("success", "Invalid ReviewId");
        } else {
            // Create the review comment.
            String username = req.getParameter("username");
            UsersDao usersDao = UsersDao.getInstance();
            ReviewsDao reviewsDao = ReviewsDao.getInstance();
            try {
                Users user = usersDao.getUserByUserName(username);
                Reviews review = reviewsDao.getReviewById(Integer.parseInt(reviewId));
                // Exercise: parse the input for StatusLevel.
                String helpful = req.getParameter("helpful");
                ReviewComments comment = null;
                if (helpful.toLowerCase().equals("yes")) {
                    comment = new ReviewComments(user, review, true);
                } else if (helpful.toLowerCase().equals("no")) {
                    comment = new ReviewComments(user, review, false);
                } else {
                    messages.put("success", "Please enter yes or no for Helpful");
                }
                if (comment != null) {
                    comment = reviewCommentsDao.create(comment);
                    messages.put("success", "Successfully created ReviewCommentId " + comment.getCommentId());
                }
            } catch (SQLException e) {
                e.printStackTrace();
                throw new IOException(e);
            }
        }
        
        req.getRequestDispatcher("/ReviewCommentCreate.jsp").forward(req, resp);
    }
}
