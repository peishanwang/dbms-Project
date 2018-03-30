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
/**
 * Servlet implementation class ProductQuestions
 */
@WebServlet("/findquestions")
public class ProductQuestions extends HttpServlet {
	QuestionsDao questionsDao;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
	 @Override
	    public void init() throws ServletException {
		 questionsDao = QuestionsDao.getInstance();
	    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        List<Questions> questions = new ArrayList<>();
        
        // Retrieve and validate name.
        // product name is retrieved from the URL query string.
        String productId = req.getParameter("productId");
        if (productId == null || productId.trim().isEmpty()) {
            messages.put("success", "Please enter a valid productId.");
        } else {
            // Retrieve Products, and store as a message.
            try {
            	questions = questionsDao.getQuestionsByProductId(productId);
            } catch (SQLException e) {
                e.printStackTrace();
                throw new IOException(e);
            }
            messages.put("success", "Displaying results for " + productId);
            // Save the previous search term, so it can be used as the default
            // in the input box when rendering FindUsers.jsp.
            messages.put("previousProductName", productId);
        }
        req.setAttribute("questions", questions);
        
        req.getRequestDispatcher("/ProductQuestions.jsp").forward(req, resp);
    }
    
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        List<Questions> questions = new ArrayList<>();
        
        // Retrieve and validate name.
        // product name is retrieved from the form POST submission. By default, it
        // is populated by the URL query string (in FindProducts.jsp).
        String productId = req.getParameter("productId");
        if (productId == null || productId.trim().isEmpty()) {
            messages.put("success", "Please enter a valid productId.");
        } else {
            // Retrieve Products, and store as a message.
            try {
                questions = questionsDao.getQuestionsByProductId(productId);
            } catch (SQLException e) {
                e.printStackTrace();
                throw new IOException(e);
            }
            messages.put("success", "Displaying results for " + productId);
        }
        req.setAttribute("products", productId);
        
        req.getRequestDispatcher("/ProductQuestions.jsp").forward(req, resp);
    }
}
