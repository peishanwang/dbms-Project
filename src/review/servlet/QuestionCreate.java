package review.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
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
/**
 * Servlet implementation class ProductQuestions
 */
@WebServlet("/questioncreate")
public class QuestionCreate extends HttpServlet {
	QuestionsDao questionsDao;
	UsersDao usersDao;
	ProductsDao productsDao;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
	 @Override
	    public void init() throws ServletException {
		 questionsDao = QuestionsDao.getInstance();
		 usersDao = UsersDao.getInstance();
		 productsDao = ProductsDao.getInstance();
	    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
    	  // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);
        
        req.getRequestDispatcher("/QuestionCreate.jsp").forward(req, resp);
    }
    
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        // Retrieve and validate name.
        // product name is retrieved from the form POST submission. By default, it
        // is populated by the URL query string (in FindProducts.jsp).
        String content = req.getParameter("content");
        String productId = req.getParameter("productid");
        Date created = new Date();
        String username = req.getParameter("username");
		
        if (content == null || content.trim().isEmpty()) {
            messages.put("success", "Please enter a valid content.");
        } else {
            // Retrieve Products, and store as a message.
            try {
            	Users user = usersDao.getUserByUserName(username);
    		    Products product = productsDao.getProductById(productId);
                Questions question = new Questions(content, created, product, user);
                questionsDao.create(question);
            } catch (SQLException e) {
                e.printStackTrace();
                throw new IOException(e);
            }
            messages.put("success", "Create successfully");
        }
      
//        req.setAttribute("products", productId);
        
        req.getRequestDispatcher("/QuestionCreate.jsp").forward(req, resp);
    }
}