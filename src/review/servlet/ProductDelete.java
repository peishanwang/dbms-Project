package review.servlet;

import review.dal.*;
import review.model.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.annotation.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/productdelete")
public class ProductDelete extends HttpServlet {
    
	protected ProductsDao productsDao;
	
	@Override
	public void init() throws ServletException {
		productsDao = ProductsDao.getInstance();
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);
        //Just render the JSP.   
        messages.put("title", "Delete Product");
        req.getRequestDispatcher("/ProductDelete.jsp").forward(req, resp);
	}
    
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        // Retrieve and validate name.
        String productId = req.getParameter("productid");
        if (productId == null || productId.trim().isEmpty()) {
            messages.put("title", "Invalid ProductId");
            messages.put("disableSubmit", "true");
        } else {
            // Delete the BlogUser.
        	Products product = new Products(productId);
            try {
                product = productsDao.delete(product);
                // Update the message.
                if (product == null) {
                    messages.put("title", "Successfully deleted " + productId);
                    messages.put("disableSubmit", "true");
                } else {
                    messages.put("title", "Failed to delete " + productId);
                    messages.put("disableSubmit", "false");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                throw new IOException(e);
            }
        }
        
        req.getRequestDispatcher("/ProductDelete.jsp").forward(req, resp);
    }
}
