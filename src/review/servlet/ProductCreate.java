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

@WebServlet("/productcreate")
public class ProductCreate extends HttpServlet {
	
	protected ProductsDao productsDao;
	protected BrandsDao brandsDao;
	
	@Override
	public void init() throws ServletException {
		productsDao = ProductsDao.getInstance();
		brandsDao = BrandsDao.getInstance();
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);
        //Just render the JSP.   
        req.getRequestDispatcher("/ProductCreate.jsp").forward(req, resp);
	}
	
	@Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        String productId = req.getParameter("productid");
        if (productId == null || productId.trim().isEmpty()) {
            messages.put("success", "Invalid ProductId");
        } else {
        	String productName = req.getParameter("productname");
        	String description = req.getParameter("description");
        	String brandName = req.getParameter("brandname");
        	Double price = Double.valueOf(req.getParameter("price"));
        	Brands brand = null;
        	try {
        		brand = brandsDao.getBrandByBrandName(brandName);
        		if (brand == null) {
            		messages.put("success", "This brand doesn't exist");
            		req.getRequestDispatcher("/ProductCreate.jsp").forward(req, resp);
            		return;
            	}
        	} catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
	        }
            try {
	        	Products product = new Products(productId, productName, description, brand, price);
	        	product = productsDao.create(product);
	        	messages.put("success", "Successfully created " + productName);
	        } catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
	        }
        }
        
        req.getRequestDispatcher("/ProductCreate.jsp").forward(req, resp);
    }
}
