package review.servlet;

import review.dal.*;
import review.model.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.annotation.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/recommendationcreate")
public class RecommendationCreate extends HttpServlet {
	
	protected ProductsDao productsDao;
	protected RecommendationsDao recommendationsDao;
	protected UsersDao usersDao;
	
	@Override
	public void init() throws ServletException {
		productsDao = ProductsDao.getInstance();
		recommendationsDao = RecommendationsDao.getInstance();
		usersDao = UsersDao.getInstance();
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);
        //Just render the JSP.   
        req.getRequestDispatcher("/RecommendationCreate.jsp").forward(req, resp);
	}
	
	@Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        String username = req.getParameter("username");
        String productId = req.getParameter("productid");
        if (productId == null || productId.trim().isEmpty()) {
            messages.put("success", "Invalid ProductId");
        } else {
            try {
            	Users user = usersDao.getUserByUserName(username);
            	Products product = productsDao.getProductById(productId);
            	List<Recommendations> recommendations = recommendationsDao.getRecommendationsByUserName(username);
            	boolean isExist = false;
            	for (Recommendations r : recommendations) {
            		if (r.getProduct().getProductId().equals(productId)) {
            			isExist = true;
            			
            		}
            	}
            	if (isExist) {
            		messages.put("success", "Already recommend");
            	}
            	else {
            	Recommendations recommendation = new Recommendations(user, product);
	        	recommendationsDao.create(recommendation);
	        	messages.put("success", "Successfully created ");
            	}
	        } catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
	        }
        }
        
        req.getRequestDispatcher("/RecommendationCreate.jsp").forward(req, resp);
    }
}
