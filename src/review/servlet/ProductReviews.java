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

@WebServlet("/reviews")
public class ProductReviews extends HttpServlet {
    
    protected ReviewsDao reviewsDao;
    
    @Override
    public void init() throws ServletException {
        reviewsDao = ReviewsDao.getInstance();
    }
    
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);
        
        // Retrieve and validate UserName.
        String productId = req.getParameter("productid");
        String userName = req.getParameter("username");
        if (isValid(productId) && isValid(userName)) {
            messages.put("title", "Invalid parameters.");
        } else if (!isValid(productId) && !isValid(userName)) {
        	messages.put("title", "Lack of parameters");
        } else if (isValid(productId)) {
        	messages.put("title", "Reviews for " + productId);
        } else {
        	messages.put("title", "Reviews for " + userName);
        }
        
        // Retrieve Reviews, and store in the request.
        List<Reviews> reviews = new ArrayList<>();
        try {
        	if (isValid(productId)) {
        		Products product = new Products(productId);
        		reviews = reviewsDao.getReviewForProduct(product);
            	
            } else {
            	Users user = new Users(userName);
            	reviews = reviewsDao.getReviewForUser(user);
            		
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IOException(e);
        }
        req.setAttribute("reviews", reviews);
        req.getRequestDispatcher("/ProductReviews.jsp").forward(req, resp);
    }
    
    private boolean isValid(String string) {
    	return string != null && !string.trim().isEmpty();
    }
}
