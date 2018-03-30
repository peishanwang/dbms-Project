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

@WebServlet("/reviewcreate")
public class ReviewCreate extends HttpServlet {

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
        //Just render the JSP.   
        req.getRequestDispatcher("/ReviewCreate.jsp").forward(req, resp);
    }
    
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        // Retrieve and validate name.
        String username = req.getParameter("username");
        String productId = req.getParameter("productid");
        if (username == null || username.trim().isEmpty() || productId == null || productId.trim().isEmpty()) {
            messages.put("success", "Invalid UserName or ProductId");
        } else {
            // Create the Favorite.
            UsersDao usersDao = UsersDao.getInstance();
            ProductsDao productsDao = ProductsDao.getInstance();
            try {
                Users user = usersDao.getUserByUserName(username);
                Products product = productsDao.getProductById(productId);
                // Exercise: parse the input for StatusLevel.
                Reviews review = null;
                if (user == null || product == null) {
                    messages.put("success", "User or Product does not exist");
                } else {
                    String content = req.getParameter("content");
                    String summary = req.getParameter("summary");
                    String rating = req.getParameter("rating");
                    review = new Reviews(user, product, content, summary, Double.parseDouble(rating));
                    review = reviewsDao.create(review);
                    messages.put("success", "Successfully created ReviewId " + review.getReviewId());
                }
            } catch (SQLException e) {
                e.printStackTrace();
                throw new IOException(e);
            }
        }
        
        req.getRequestDispatcher("/ReviewCreate.jsp").forward(req, resp);
    }
}
