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


@WebServlet("/productupdate")
public class ProductUpdate extends HttpServlet {
    
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
        
        String productId = req.getParameter("productid");
        if (productId == null || productId.trim().isEmpty()) {
            messages.put("success", "Please enter a valid productId.");
        } else {
            try {
            	Products product = productsDao.getProductById(productId);
                if(product == null) {
                    messages.put("success", "Product does not exist.");
                }
                req.setAttribute("product", product);
            } catch (SQLException e) {
                e.printStackTrace();
                throw new IOException(e);
            }
        }
        //Just render the JSP.   
        messages.put("title", "Update Product");
        req.getRequestDispatcher("/ProductUpdate.jsp").forward(req, resp);
	}
    
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        // Retrieve user and validate.
        String productId = req.getParameter("productid");
        if (productId == null || productId.trim().isEmpty()) {
            messages.put("success", "Please enter a valid ProductId.");
        } else {
            try {
            	Products product = productsDao.getProductById(productId);
                if(product == null) {
                    messages.put("success", "ProductId does not exist. No update to perform.");
                } else {
                	String newProductName = req.getParameter("productname");
                    String newDescription = req.getParameter("description");
                    Double newPrice = 0.0;
                	if (!req.getParameter("price").isEmpty()) {
                		newPrice = Double.valueOf(req.getParameter("price"));
                	}
                    if (newDescription == null || newDescription.trim().isEmpty()) {
                        messages.put("success", "Please enter a valid Description.");
                    } else if (newProductName == null || newProductName.trim().isEmpty()) {
                    	messages.put("success", "Please enter a valid Product Name.");
                    } else if (newPrice == null) {
                    	messages.put("success", "Please enter a valid Price.");
                    } else {
                        product = productsDao.updateDescription(product, newDescription);
                        product = productsDao.updateProductName(product, newProductName);
                        product = productsDao.updatePrice(product, newPrice);
                        messages.put("success", "Successfully updated " + productId);
                    }
                }
                req.setAttribute("product", product);
            } catch (SQLException e) {
                e.printStackTrace();
                throw new IOException(e);
            }
        }
        
        req.getRequestDispatcher("/ProductUpdate.jsp").forward(req, resp);
    }
}
