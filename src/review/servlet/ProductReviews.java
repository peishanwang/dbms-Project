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
        if (productId == null || productId.trim().isEmpty()) {
            messages.put("title", "Invalid product id.");
        } else {
            messages.put("title", "Reviews for " + productId);
        }
        
        // Retrieve Reviews, and store in the request.
        List<Reviews> reviews = new ArrayList<>();
        try {
            Products product = new Products(productId);
            reviews = reviewsDao.getReviewForProduct(product);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new IOException(e);
        }
        req.setAttribute("reviews", reviews);
        req.getRequestDispatcher("/ProductReviews.jsp").forward(req, resp);
    }
}
