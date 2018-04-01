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

@WebServlet("/reviewcomments")
public class ProductReviewComments extends HttpServlet {

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
        
        List<ReviewComments> reviewComments = new ArrayList<>();
        
        // Retrieve BlogComments depending on valid PostId or UserName.
        String reviewId = req.getParameter("reviewid");
        
        try {
            if (reviewId != null && !reviewId.trim().isEmpty()) {
                // If the postid param is provided then ignore the username param.
                Reviews review = new Reviews(Integer.parseInt(reviewId));
                reviewComments = reviewCommentsDao.getReviewCommentsForReview(review);
                messages.put("title", "Comments of Review");
                messages.put("title2", "Review Id: " + reviewId);
            } else {
                messages.put("title", "Invalid ReviewId.");
                messages.put("title2", "");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IOException(e);
        }
        
        req.setAttribute("reviewComments", reviewComments);
        req.getRequestDispatcher("/ProductReviewComments.jsp").forward(req, resp);
    }
}
